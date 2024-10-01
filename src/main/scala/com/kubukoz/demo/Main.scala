package com.kubukoz.demo

import scala.io.Source
import scala.util.Using
import scala.util.Try

object Main extends App {

  val loaderRes =
    Using(Source.fromURL(getClass().getClassLoader().getResource("hello.txt")))(
      _.getLines().mkString("\n")
    )

  val classRes =
    Using(Source.fromURL(getClass().getResource("/hello.txt")))(
      _.getLines().mkString("\n")
    )

  println("loader: " + loaderRes)
  println("class: " + classRes)
}
