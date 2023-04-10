package app.views

import demo._

import tyrian._

import Html._

object ProductListView {

  enum PropagatedMsg {
    case ProductSelected(product: Product)
  }

  def view(products: List[Product]): Html[PropagatedMsg] =
    table(className := "table table-striped")(
      thead(
        tr(
          th("Photo"),
          th("Title"),
          th("Price"),
          th("Description"),
        )
      ),
      tbody(
        products.map { product =>
          tr(
            td(
              img(
                src := product.thumbnail.getOrElse(""),
                width := "100",
              )
            ),
            td(
              a(
                href := "",
                onClick(
                  PropagatedMsg.ProductSelected(product)
                ),
              )(b(product.title))
            ),
            td(product.price.toString),
            td(product.description),
          )
        }
      ),
    )

}
