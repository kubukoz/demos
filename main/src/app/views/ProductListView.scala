package app.views

import demo._

import tyrian._

import Html._

object ProductListView {

  def view[Msg](products: List[Product])(productSelected: Product => Msg): Html[Msg] =
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
                  productSelected(product)
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
