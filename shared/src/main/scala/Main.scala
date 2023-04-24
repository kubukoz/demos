import scala.scalanative.unsafe.exported

@exported("dupa")
def scalaApi(): Unit =
  for i <- 1 to 10
  do println(s"hello world! I'm in Scala! $i")
