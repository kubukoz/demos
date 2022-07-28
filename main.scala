//> using scala "2.13.8"
//> using lib "com.disneystreaming.smithy4s::smithy4s-http4s:0.14.2"
import smithy4s.schema.Schema
import smithy4s.http.PayloadError
import smithy4s.http4s.SimpleRestJsonBuilder

object codecs {

  def makeDecoder[A: Schema]: String => Either[PayloadError, A] = {
    val codecApi = SimpleRestJsonBuilder.codecs

    val codec = codecApi.compileCodec(implicitly[Schema[A]])

    s => codecApi.decodeFromByteArray(s.getBytes())
  }

  def makeEncoder[A: Schema]: A => String = {
    val codecApi = SimpleRestJsonBuilder.codecs

    val codec = codecApi.compileCodec(implicitly[Schema[A]])

    codecApi.writeToArray(codec, _)
  }
}
