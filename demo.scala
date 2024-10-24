//> using toolkit typelevel:default
//> using option -Wunused:all
//> using option -Ykind-projector
//> using dep com.kubukoz::debug-utils::1.1.3
import cats.Contravariant
import cats.syntax.all.*

trait Feature[A] { self =>
  def name: String

  def compute(a: A): Float

  def contramap[B](f: B => A): Feature[B] =
    new {
      def name = self.name
      def compute(b: B) = self.compute(f(b))
    }

}

object Feature {

  given Contravariant[Feature] with {
    def contramap[A, B](fa: Feature[A])(f: B => A): Feature[B] = fa.contramap(f)
  }

  object dsl {
    inline def feat[A](inline f: A => Float): Feature[A] = ${ FeatMacro.featImpl('f) }

    inline def discoverFeatures[A]: FeatureSet[A] = ${ FeatMacro.discoverFeaturesImpl[A] }
  }

}

trait Feature2[A, B] { self =>
  def name: String
  def compute(a: A, b: B): Float

  def asFeature: Feature[(A, B)] =
    new {
      def name = self.name
      def compute(ab: (A, B)) = self.compute(ab._1, ab._2)
    }

}

import Feature.dsl.*

object TextFeature {
  val wordCount: Feature[String] = feat(_.split(" ").length.toFloat)

  val stopWordCount: Feature[String] = feat(
    _.split(" ").count(Set("the", "a", "an").contains).toFloat
  )

  val all: FeatureSet[String] = discoverFeatures
}

case class Candidate(name: String, text: String)

object CandidateFeature {
  val nameLength: Feature[Candidate] = feat(_.name.length.toFloat)
  val textLength: Feature[Candidate] = feat(_.text.length.toFloat)

  val all: FeatureSet[Candidate] = discoverFeatures
}

case class UnifiedCandidateWithQuery(
  c: Candidate,
  q: String,
)

trait FeatureSet[A] { self =>
  def features: List[Feature[A]]

  def contramap[B](f: B => A): FeatureSet[B] =
    new {
      val features = self.features.map(_.contramap(f))
    }

}

object FeatureSet {

  def of[A](fs: Feature[A]*): FeatureSet[A] =
    new {
      val features = fs.toList
    }

  def combineAll[A](sets: FeatureSet[A]*): FeatureSet[A] =
    new {
      val features = sets.flatMap(_.features).toList
    }

  given Contravariant[FeatureSet] with {
    def contramap[A, B](fa: FeatureSet[A])(f: B => A): FeatureSet[B] = fa.contramap(f)
  }

}

val myFeatures: FeatureSet[UnifiedCandidateWithQuery] = FeatureSet.combineAll(
  CandidateFeature.all.contramap(_.c),
  TextFeature.all.contramap(_.q),
)

val result = myFeatures.features.map { feat =>
  feat.name -> feat.compute(
    UnifiedCandidateWithQuery(Candidate("name", "text"), "query")
  )
}

val weights = Map(
  "NameLength" -> 1.0,
  "TextLength" -> 0.5,
  "WordCount" -> 0.1,
  "StopWordCount" -> 0.2,
)

@main def goooo =
  result.foreach(println)
  println((weights, result.toMap).mapN(_ * _).values.sum)
