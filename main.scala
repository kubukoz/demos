import scala.util.Random

@main def demo = {
  val numbers = List.fill(100)(Random.nextInt().abs)
  numbers
    .map { n =>
      val r = new Random(1024)
      r.nextInt(n)
      r.nextInt(100)
    }
    .distinct
    .foreach(println)
}
