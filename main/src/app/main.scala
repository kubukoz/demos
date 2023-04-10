package app

import cats.effect.IO
import demo._
import tyrian.TyrianApp
import tyrian._

import scala.scalajs.js.annotation.JSExportTopLevel

import views.ProductListFetchView
import views.ProductFetchView
import Html._
import scala.concurrent.duration._
import scala.scalajs.js.Date
import cats.implicits._

enum Msg {
  case ProductMsg(msg: ProductFetchView.Msg)
  case ListMsg(msg: ProductListFetchView.Msg)

  case Tick(date: Date)
}

case class Model(
  now: Option[Date],
  product: ProductFetchView.Model,
  list: ProductListFetchView.Model,
)

object Model {

  val init: Model = Model(
    now = None,
    product = ProductFetchView.Model.init,
    list = ProductListFetchView.Model.init,
  )

}

@JSExportTopLevel("App")
object App extends TyrianApp[Msg, Model] {

  override def init(flags: Map[String, String]): (Model, Cmd[IO, Msg]) =
    (
      Model.init,
      Cmd.Run(IO(new Date(Date.now())))(Msg.Tick(_)),
    )

  def subscriptions(model: Model): Sub[IO, Msg] =
    Sub.every[IO](1.second).map(date => Msg.Tick(date)) |+|
      ProductFetchView.subscriptions(model.product).map(translateProductMsg) |+|
      ProductListFetchView.subscriptions(model.list).map(translateListMsg)

  override def update(model: Model): Msg => (Model, Cmd[IO, Msg]) = {

    case Msg.Tick(date) => (model.copy(now = date.some), Cmd.None)

    case Msg.ListMsg(msg) =>
      val (newListModel, listMsg) = ProductListFetchView.update(model.list)(msg)

      (
        model.copy(list = newListModel),
        listMsg.map(translateListMsg(_)),
      )

    case Msg.ProductMsg(msg) =>
      val (newProductModel, productMsg) = ProductFetchView.update(model.product)(msg)

      (
        model.copy(product = newProductModel),
        productMsg.map(translateProductMsg(_)),
      )

  }

  private val translateListMsg
    : ProductListFetchView.Msg | ProductListFetchView.PropagatedMsg => Msg = {
    case ProductListFetchView.PropagatedMsg.ProductSelected(product) =>
      Msg.ProductMsg(ProductFetchView.Msg.ProductIdChanged(product.id))
    case msg: ProductListFetchView.Msg => Msg.ListMsg(msg)
  }

  private val translateProductMsg: ProductFetchView.Msg => Msg = { msg =>
    Msg.ProductMsg(msg)
  }

  override def view(model: Model): Html[Msg] =
    div(className := "container")(
      model
        .now
        .map { date =>
          p(s"Current time: ${date.toLocaleTimeString()}")
        }
        .toList
        .appendedAll(
          List(
            ProductFetchView.view(model.product).map(translateProductMsg(_)),
            ProductListFetchView.view(model.list).map(translateListMsg(_)),
          )
        )
    )

}

@main def main = ()
