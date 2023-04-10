package app.views

import cats.effect.IO
import cats.implicits._
import demo._
import tyrian._
import Html._
import app.ProductApi

enum ProductListState {
  case Fetching(skip: Int)
  case Done
  case Errored(e: Throwable)
}

object ProductListFetchView {

  case class Model(products: List[Product], list: ProductListState)

  object Model {

    val init: Model = Model(
      list = ProductListState.Fetching(skip = 0),
      products = Nil,
    )

  }

  enum Msg {
    case FetchedProductList(response: GetAllProductsOutput)
    case ErroredProductList(e: Throwable)
  }

  enum PropagatedMsg {
    case ProductSelected(product: Product)
  }

  def subscriptions(model: Model): Sub[IO, Msg | PropagatedMsg] =
    model.list match {
      case ProductListState.Fetching(skip) =>
        Sub
          .make(
            s"product-list-request-$skip",
            fs2
              .Stream
              .eval(
                ProductApi
                  .client
                  .getAllProducts(skip = skip.some)
                  .map(Msg.FetchedProductList(_))
                  .handleError(Msg.ErroredProductList(_))
              ),
          )

      case _ => Sub.None
    }

  def update(model: Model): Msg => (Model, Cmd[IO, Msg | PropagatedMsg]) = {
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

  def view(model: Model): Html[Msg | PropagatedMsg] = div(
    div(model.list match {
      case ProductListState.Fetching(_) => p("fetching products...")
      case ProductListState.Done        => p()
      case ProductListState.Errored(e) =>
        p(
          text("error"),
          pre(code(e.getMessage)),
        )
    }),
    ProductListView.view(model.products).map {
      case ProductListView.PropagatedMsg.ProductSelected(product) =>
        PropagatedMsg.ProductSelected(product)
    },
  )

}
