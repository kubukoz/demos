package app.views

import demo._

import tyrian._

import Html._
import app.ProductState

object ProductView {

  def view(product: ProductState): Html[Nothing] = div {
    product.match {
      case ProductState.Empty       => p()
      case ProductState.Fetching(_) => p("fetching...")

      case ProductState.Found(response) =>
        div(
          p(
            text("The chosen product is: "),
            b(response.title),
            text(s": ${response.description}"),
          ),
          p(
            response.images.map { url =>
              span(
                img(
                  src := url,
                  width := "100",
                )
              )
            }
          ),
        )

      case ProductState.Errored(e) =>
        p(
          text("error"),
          pre(code(e.getMessage)),
        )
    }
  }

}
