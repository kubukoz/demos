import cats.effect.IO

import cats.implicits._
import demo._
import org.http4s.dom.FetchClientBuilder
import org.http4s.implicits._
import smithy4s.http4s.SimpleRestJsonBuilder
import tyrian.TyrianApp
import tyrian._

import scala.scalajs.js.annotation.JSExportTopLevel

enum Msg {
  case InputChanged(newText: Int)
  case FetchedProduct(product: Either[Throwable, GetProductOutput])
}

case class Model(text: Int, request: Option[Int], product: ProductState)

enum ProductState {
  case Empty
  case Fetching
  case Found(response: GetProductOutput)
  case Errored(message: String)
}

object Model {

  val init: Model = Model(
    text = 0,
    request = None,
    product = ProductState.Empty,
  )

}

@JSExportTopLevel("App")
object App extends TyrianApp[Msg, Model] {

  private val client: ProductService[IO] =
    SimpleRestJsonBuilder(ProductService)
      .client(FetchClientBuilder[IO].create)
      .uri(uri"https://dummyjson.com")
      .use
      .toTry
      .get

  override def init(flags: Map[String, String]): (Model, Cmd[IO, Msg]) =
    (
      Model.init,
      Cmd.None,
    )

  def subscriptions(model: Model): Sub[IO, Msg] =
    model.request match {
      case None => Sub.None
      case Some(request) =>
        Sub.make(
          s"request-$request",
          fs2
            .Stream
            .eval(
              client
                .getProduct(request)
                .attempt
                .guaranteeCase(IO.println(_))
            )
            .map(Msg.FetchedProduct(_)),
        )
    }

  override def update(model: Model): Msg => (Model, Cmd[IO, Msg]) = {
    case Msg.InputChanged(newInput) =>
      (
        model.copy(
          text = newInput,
          product = ProductState.Fetching,
          request = Some(newInput),
        ),
        Cmd.Run(
          client.getProduct(newInput).attempt
        )(out => Msg.FetchedProduct(out)),
      )

    case Msg.FetchedProduct(response) =>
      (
        model.copy(product =
          response.fold(
            e => ProductState.Errored(e.getMessage),
            product => ProductState.Found(product),
          )
        ),
        Cmd.None,
      )

  }

  override def view(model: Model): Html[Msg] = {
    import Html._

    html(
      input(
        tpe := "number",
        onInput(in => Msg.InputChanged(in.toIntOption.getOrElse(0))),
        value := model.text.show,
      ),
      model.product.match {
        case ProductState.Empty    => p()
        case ProductState.Fetching => p("fetching...")

        case ProductState.Found(response) =>
          p(s"The product is: ${response.title}: ${response.description}")

        case ProductState.Errored(msg) =>
          p(
            text("error"),
            pre(code(msg)),
          )
      },
    )
  }

}

@main def main = ()
