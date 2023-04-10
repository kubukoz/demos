package app

import views.ProductView
import cats.effect.IO

import cats.implicits._
import demo._
import org.http4s.dom.FetchClientBuilder
import org.http4s.implicits._
import smithy4s.http4s.SimpleRestJsonBuilder
import tyrian.TyrianApp
import tyrian._

import scala.scalajs.js.annotation.JSExportTopLevel
import Html._

import views.ProductListView

enum Msg {
  case ProductIdChanged(newId: Int)
  case Errored(e: Throwable)
  case FetchedProduct(product: Product)
  case FetchedProductList(response: GetAllProductsOutput)
  case ErroredProductList(e: Throwable)
}

case class Model(
  text: Int,
  product: ProductState,
  list: ProductListState,
  products: List[Product],
)

enum ProductState {
  case Empty
  case Fetching(id: Int)
  case Found(response: Product)
  case Errored(e: Throwable)
}

enum ProductListState {
  case Fetching(skip: Int)
  case Done
  case Errored(e: Throwable)
}

object Model {

  val init: Model = Model(
    text = 0,
    product = ProductState.Empty,
    list = ProductListState.Fetching(skip = 0),
    products = Nil,
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

  def subscriptions(model: Model): Sub[IO, Msg] = {
    val productRequest =
      model.product match {
        case ProductState.Fetching(request) =>
          Sub.make(
            s"product-request-$request",
            fs2
              .Stream
              .eval(
                client
                  .getProduct(request)
                  .map(Msg.FetchedProduct(_))
                  .handleError(Msg.Errored(_))
              ),
          )
        case _ => Sub.None

      }

    val listRequest =
      model.list match {
        case ProductListState.Fetching(skip) =>
          Sub.make(
            s"product-list-request-$skip",
            fs2
              .Stream
              .eval(
                client
                  .getAllProducts(skip = skip.some)
                  .map(Msg.FetchedProductList(_))
                  .handleError(Msg.ErroredProductList(_))
              ),
          )
        case _ => Sub.None
      }

    productRequest |+| listRequest
  }

  override def update(model: Model): Msg => (Model, Cmd[IO, Msg]) = {
    case Msg.ProductIdChanged(newInput) =>
      (
        model.copy(
          text = newInput,
          product = ProductState.Fetching(newInput),
        ),
        Cmd.None,
      )

    case Msg.Errored(e) =>
      (
        model.copy(product = ProductState.Errored(e)),
        Cmd.None,
      )

    case Msg.FetchedProduct(response) =>
      (
        model.copy(product = ProductState.Found(response)),
        Cmd.None,
      )

    case Msg.FetchedProductList(response) if response.limit > 0 =>
      (
        model.copy(
          products = model.products ++ response.products,
          list = ProductListState.Fetching(response.skip + response.limit),
        ),
        Cmd.None,
      )

    case Msg.FetchedProductList(_) =>
      (
        model.copy(list = ProductListState.Done),
        Cmd.None,
      )

    case Msg.ErroredProductList(e) =>
      (
        model.copy(list = ProductListState.Errored(e)),
        Cmd.None,
      )
  }

  override def view(model: Model): Html[Msg] =
    div(className := "container")(
      div(
        className := "container"
      )(
        span("Select product ID: "),
        input(
          tpe := "number",
          onInput(in => Msg.ProductIdChanged(in.toIntOption.getOrElse(0))),
          value := model.text.show,
        ),
      ),
      ProductView.view(model.product),
      div(model.list match {
        case ProductListState.Fetching(_) => p("fetching products...")
        case ProductListState.Done        => p()
        case ProductListState.Errored(e) =>
          p(
            text("error"),
            pre(code(e.getMessage)),
          )
      }),
      ProductListView.view(model.products)(productSelected = p => Msg.ProductIdChanged(p.id)),
    )

}

@main def main = ()
