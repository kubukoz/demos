import cats.effect.IO
import cats.effect.IOApp

object Demo extends IOApp.Simple {

  def run: IO[Unit] = IO.ref(List[Int]()).flatMap { seen =>
    Stream
      .iterate(1)(_ + 1)
      .take(5)
      .append(Stream(6, 7, 8, 9, 10).evalMap(it => seen.update(_ :+ it).as(it)))
      .evalMap(n => IO(println(s"1: Processing $n")).as(n * 10))
      .take(6)
      .drop(1)
      // .evalMap(n => IO(println(s"2: Processing $n")).as(n * 10))
      .compile
      .toList
      .debug()
      *> seen.get.flatMap { seen =>
        IO.println(s"seen items from second stream: $seen")
      }
  }
  // Stream(1, 2, 3, 4, 5).compile.toList.flatMap(IO.println)
  // (Stream(1, 2, 3, 4, 5) ++ Stream
  //   .eval(IO.unit)
  //   .drain ++
  //   Stream(1) ++
  //   Stream(5, 6, 7, 8)).chunks.unchunks.compile.toList.flatMap(IO.println)

  // Stream
  //   .eval(IO.unit)
  //   .pull
  //   .uncons
  //   .flatMap(_ => Pull.done)
  //   .stream
  //   .compile
  //   .drain
  // Stream.iterate(0)(_ + 1).take(5).evalMap(IO.println(_)).compile.drain

}
