package app

import views.ProductView
import cats.effect.IO

import cats.implicits._
import demo._
import tyrian.TyrianApp
import tyrian._

import scala.scalajs.js.annotation.JSExportTopLevel
import Html._

enum Msg {
  case ProductIdChanged(newId: Int)
  case Errored(e: Throwable)
  case FetchedProduct(product: Product)
  case ListMsg(msg: ProductListFetchView.Msg)
}

case class Model(
  productId: Int,
  product: ProductState,
  list: ProductListFetchView.Model,
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
    productId = 0,
    product = ProductState.Empty,
    list = ProductListFetchView.Model.init,
  )

}

@JSExportTopLevel("App")
object App extends TyrianApp[Msg, Model] {

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
                ProductApi
                  .client
                  .getProduct(request)
                  .map(Msg.FetchedProduct(_))
                  .handleError(Msg.Errored(_))
              ),
          )
        case _ => Sub.None

      }

    productRequest |+| ProductListFetchView.subscriptions(model.list).map(translateListMsg(_))
  }

  override def update(model: Model): Msg => (Model, Cmd[IO, Msg]) = {
    case Msg.ProductIdChanged(newInput) =>
      (
        model.copy(
          productId = newInput,
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

    case Msg.ListMsg(msg) =>
      val (newListModel, listMsg) = ProductListFetchView.update(model.list)(msg)

      (
        model.copy(list = newListModel),
        listMsg.map(translateListMsg(_)),
      )

  }

  private def translateListMsg
    : ProductListFetchView.Msg | ProductListFetchView.PropagatedMsg => Msg = {
    case ProductListFetchView.PropagatedMsg.ProductSelected(product) =>
      Msg.ProductIdChanged(product.id)
    case msg: ProductListFetchView.Msg => Msg.ListMsg(msg)
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
          value := model.productId.show,
        ),
      ),
      ProductView.view(model.product),
      ProductListFetchView
        .view(model.list)
        .map(translateListMsg(_)),
    )

}

@main def main = ()
