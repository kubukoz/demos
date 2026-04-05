package demo

import demo.pdapiBindings.primitives.HTTPConnection
import smithy4s.Blob
import smithy4s.Endpoint
import smithy4s.client.UnaryClientCompiler
import smithy4s.client.UnaryLowLevelClient
import smithy4s.http.CaseInsensitive
import smithy4s.http.HttpDiscriminator
import smithy4s.http.HttpMethod
import smithy4s.http.HttpRequest
import smithy4s.http.HttpResponse
import smithy4s.http.HttpUnaryClientCodecs
import smithy4s.http.Metadata
import smithy4s.json.Json

import scalanative.unsafe.*
import scalanative.unsigned.*

trait ClientCall[Output] {
  def run(onComplete: Output => Unit): Unit

  def map[Output2](f: Output => Output2): ClientCall[Output2] =
    onComplete =>
      run { output =>
        onComplete(f(output))
      }

  def flatMap[Output2](f: Output => ClientCall[Output2]): ClientCall[Output2] =
    onComplete =>
      run {
        f(_).run(onComplete)
      }

}

object ClientCall {

  def pure[A](a: A): ClientCall[A] =
    new ClientCall[A] {
      def run(onComplete: A => Unit): Unit = onComplete(a)
    }

  def raiseError[A](e: Throwable): ClientCall[A] =
    new ClientCall[A] {
      def run(onComplete: A => Unit): Unit = throw e
    }

  given smithy4s.capability.MonadThrowLike[ClientCall] with {
    def pure[A](a: A): ClientCall[A] = ClientCall.pure(a)

    def handleErrorWith[A](fa: ClientCall[A])(f: Throwable => ClientCall[A]): ClientCall[A] =
      new ClientCall[A] {
        def run(onComplete: A => Unit): Unit =
          try fa.run(onComplete)
          catch { case e: Throwable => f(e).run(onComplete) }
      }

    def flatMap[A, B](fa: ClientCall[A])(f: A => ClientCall[B]): ClientCall[B] =
      fa.flatMap(f)

    def raiseError[A](e: Throwable): ClientCall[A] = ClientCall.raiseError(e)

    def zipMapAll[A](seq: IndexedSeq[ClientCall[Any]])(f: IndexedSeq[Any] => A): ClientCall[A] = {
      val sequenced: ClientCall[IndexedSeq[Any]] =
        seq.foldLeft(pure(IndexedSeq.empty[Any])) { (acc, cc) =>
          acc.flatMap(results => cc.flatMap(a => pure(results :+ a)))
        }

      sequenced.map(f)
    }

  }

}

import pdapiBindings._
import ClientCall.given

private def httpUriToPath(uri: smithy4s.http.HttpUri): String = {
  val path = "/" + uri.path.mkString("/")
  val qp = uri.queryParams
  if (qp.isEmpty)
    path
  else {
    val query = qp.flatMap { case (key, values) => values.map(v => s"$key=$v") }.mkString("&")
    s"$path?$query"
  }
}

// Global state for in-flight HTTP request (single-threaded, one request at a time)
private var _pendingHeaders: scala.collection.mutable.Map[CaseInsensitive, Seq[String]] =
  scala.compiletime.uninitialized

private var _pendingOnComplete: HttpResponse[Blob] => Unit = scala.compiletime.uninitialized

private val headerReceivedCB: CFuncPtr3[Ptr[HTTPConnection], CString, CString, Unit] =
  CFuncPtr3.fromScalaFunction { (_: Ptr[HTTPConnection], key: CString, value: CString) =>
    val k = CaseInsensitive(fromCString(key))
    val v = fromCString(value)
    _pendingHeaders(k) = _pendingHeaders.getOrElse(k, Seq.empty) :+ v
  }

private val requestCompleteCB: CFuncPtr1[Ptr[HTTPConnection], Unit] =
  CFuncPtr1.fromScalaFunction { (conn: Ptr[HTTPConnection]) =>
    val err = pd_http_getError(conn)
    if (!err.isOk) {
      pd_http_release(conn)
      _pendingOnComplete = null
      _pendingHeaders = null
      info(s"HTTP connection error: ${err.code}")
    } else {
      val status = pd_http_getResponseStatus(conn)
      val buf = stackalloc[Byte](4096)
      var responseBody = Blob.empty
      var avail = pd_http_getBytesAvailable(conn)
      while (avail > 0.toULong) {
        val n = pd_http_read(conn, buf, 4096.toUInt)
        if (n > 0) {
          val arr = new Array[Byte](n)
          var i = 0
          while (i < n) {
            arr(i) = !(buf + i)
            i += 1
          }
          responseBody = Blob(responseBody.toArray ++ arr)
        }
        avail = pd_http_getBytesAvailable(conn)
      }
      pd_http_release(conn)
      val headers = _pendingHeaders.toMap
      val cb = _pendingOnComplete
      _pendingHeaders = null
      _pendingOnComplete = null
      cb(HttpResponse(status, headers, responseBody))
    }
  }

private def executeRequest(
  host: String,
  port: Int,
  request: HttpRequest[Blob],
  handleResponse: HttpResponse[Blob] => Unit,
): Unit =
  Zone {
    _pendingHeaders = scala.collection.mutable.Map.empty
    _pendingOnComplete = handleResponse

    val conn = pd_http_newConnection(toCString(host), port, 0)

    val method = request.method.showUppercase
    val path = httpUriToPath(request.uri)

    val headerStr =
      request
        .headers
        .flatMap { case (key, values) => values.map(v => s"${key}: $v\r\n") }
        .mkString

    val bodyBytes = request.body.toArray

    pd_http_setHeaderReceivedCallback(conn, headerReceivedCB)
    pd_http_setRequestCompleteCallback(conn, requestCompleteCB)

    val headerCStr = toCString(headerStr)
    val bodyCStr =
      if (bodyBytes.isEmpty)
        null
      else {
        val ptr = stackalloc[Byte](bodyBytes.length)
        var i = 0
        while (i < bodyBytes.length) {
          !(ptr + i) = bodyBytes(i)
          i += 1
        }
        ptr
      }

    pd_http_query(
      conn,
      toCString(method),
      toCString(path),
      headerCStr,
      headerStr.length.toCSize,
      bodyCStr,
      bodyBytes.length.toCSize,
    )
  }

def makeClient[Alg[_[_, _, _, _, _]]](service: smithy4s.Service[Alg], host: String, port: Int)
  : service.Impl[ClientCall] = {

  val hintMask = alloy.SimpleRestJson.protocol.hintMask
  val jsonCodecs = Json
    .payloadCodecs
    .withJsoniterCodecCompiler(
      Json.jsoniter.withHintMask(hintMask)
    )

  val baseUri = smithy4s
    .http
    .HttpUri(
      smithy4s.http.HttpUriScheme.Http,
      host,
      Some(port),
      IndexedSeq.empty,
      Map.empty,
      None,
    )

  val baseRequest = HttpRequest(
    HttpMethod.POST,
    baseUri,
    Map.empty,
    Blob.empty,
  )

  val clientCodecs = HttpUnaryClientCodecs
    .builder
    .withBodyEncoders(jsonCodecs.encoders)
    .withSuccessBodyDecoders(jsonCodecs.decoders)
    .withErrorBodyDecoders(jsonCodecs.decoders)
    .withErrorDiscriminator(resp =>
      ClientCall.pure(HttpDiscriminator.fromResponse(List(smithy4s.http.errorTypeHeader), resp))
    )
    .withMetadataDecoders(Metadata.Decoder)
    .withMetadataEncoders(Metadata.Encoder)
    .withBaseRequest(_ => ClientCall.pure(baseRequest))
    .withRequestMediaType("application/json")
    .build()

  val lowLevelClient =
    new UnaryLowLevelClient[ClientCall, HttpRequest[Blob], HttpResponse[Blob]] {
      def run[Output](
        request: HttpRequest[Blob]
      )(
        responseCB: HttpResponse[Blob] => ClientCall[Output]
      ): ClientCall[Output] = { onComplete =>
        executeRequest(host, port, request, responseCB(_).run(onComplete))
      }
    }

  val compiler = UnaryClientCompiler(
    service = service,
    client = lowLevelClient,
    toSmithy4sClient = identity,
    makeClientCodecs = clientCodecs,
    middleware = Endpoint.Middleware.noop,
    isSuccessful = (resp: HttpResponse[Blob]) => resp.statusCode >= 200 && resp.statusCode < 300,
  )

  service.impl(compiler)
}
