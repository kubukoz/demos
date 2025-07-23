// note: do NOT import sjsonnew.BasicJsonFormat._, as it'll clash with this one!
import sbt.util.CacheImplicits._
import sjsonnew.JsonFormat

val name = settingKey[String]("Name of the person")
val age = settingKey[Int]("Age of the person")
val myTask = taskKey[MyCaseClass]("Some expensive task that produces a case class")

val root = project
  .in(file("."))
  .settings(
    name := "hello",
    age := 42,
    myTask := {
      def impl(name: String, age: Int) = {
        // simulate slowness
        Thread.sleep(1000)
        MyCaseClass(name, age)
      }

      Function
        .untupled(
          Cache.cached(streams.value.cacheStoreFactory.make("myTask"))((impl _).tupled)
        )
        .apply(
          name.value,
          age.value,
        )
    },
  )
