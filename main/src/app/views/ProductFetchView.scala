package app.views

import cats.effect.IO
import cats.implicits._
import demo._
import tyrian._
import Html._
import app.ProductApi

enum ProductState {
  case Empty
  case Fetching(id: Int)
  case Found(response: Product)
  case Errored(e: Throwable)
}

object ProductFetchView {

  case class Model(productId: Int, product: ProductState)

  object Model {

    val init: Model = Model(
      productId = 0,
      product = ProductState.Empty,
    )

  }

  enum Msg {
    case ProductIdChanged(newId: Int)
    case Errored(e: Throwable)
    case FetchedProduct(product: Product)
  }

  def subscriptions(model: Model): Sub[IO, Msg] =
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

  def update(model: Model): Msg => (Model, Cmd[IO, Msg]) = {
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

  }

  def view(model: Model): Html[Msg] = div(
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
  )

}
