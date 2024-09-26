//> using dep com.disneystreaming.smithy4s::smithy4s-core:0.18.24
//> using option -Xkind-projector
import smithy4s.Document
import smithy4s.Schema
import demosmithy.*
import smithy4s.Document.DObject
import smithy4s.Document.DString
import smithy4s.capability.Covariant
import smithy4s.codecs.PayloadError

type CatalogEntity = Channel | Show | AudioEpisode | VideoEpisode | ChannelLineup

final case class ThinCatalogEntity[SupportedCatalogEntity <: CatalogEntity](
  id: String,
  _type: EntityType,
) {
  def retag[T <: CatalogEntity]: ThinCatalogEntity[T] = copy()
}

object ThinCatalogEntity {

  given Schema[ThinCatalogEntity[CatalogEntity]] =
    Schema.struct[ThinCatalogEntity[CatalogEntity]](
      Schema.string.required("id", _.id),
      EntityType.schema.required("type", _._type),
    )(ThinCatalogEntity.apply[CatalogEntity])

}

extension (entity: ThinCatalogEntity[? /* <: CatalogEntity */ ])

  def as[T <: CatalogEntity: PhantomTypeTest]: Either[Throwable, ThinCatalogEntity[T]] =
    if summon[PhantomTypeTest[T]].matches(entity._type) then Right(entity.retag[T])
    else
      Left(
        Exception(
          s"Not a supported entity type: expected one of ${summon[PhantomTypeTest[T]].supportedTypes.mkString(", ")}, got ${entity._type}"
        )
      )

enum PhantomTypeTest[T] {
  // convenience type for accessing the type parameter outside
  type Of = T

  def matches(tpeCandidate: EntityType): Boolean = supportedTypes.contains(tpeCandidate)

  val supportedTypes: Set[EntityType] =
    this match {
      case Single(_, types) => types
      case Many(lhs, rhs)   => lhs.supportedTypes ++ rhs.supportedTypes
    }

  case Single(schema: Schema[T], types: Set[EntityType])
  case Many[A, B](lhs: PhantomTypeTest[A], rhs: PhantomTypeTest[B]) extends PhantomTypeTest[A | B]
}

object PhantomTypeTest {

  def instance[T <: CatalogEntity: Schema](types: EntityType*): PhantomTypeTest[T] = PhantomTypeTest
    .Single[T](summon, types.toSet)

  given derivePTTUnion[A](
    using isu: IsUnion[A]
  )(
    using lhsInstance: PhantomTypeTest[isu.LHS],
    rhsInstance: PhantomTypeTest[isu.RHS],
  ): PhantomTypeTest[A] = {
    val base = PhantomTypeTest.Many(lhsInstance, rhsInstance)
    isu.isParent.substituteCo(base)
  }

}

def decodePlease[T <: CatalogEntity: PhantomTypeTest](json: Document)
  : Either[Throwable, ThinCatalogEntity[T]] = json
  .decode[ThinCatalogEntity[CatalogEntity]]
  .flatMap(_.as[T])

given Covariant[Either[PayloadError, *]] with {
  def map[A, B](fa: Either[PayloadError, A])(f: A => B): Either[PayloadError, B] = fa.map(f)
}

// for non-thin events
def decodePleaseFull[T <: CatalogEntity: PhantomTypeTest]: Document => Either[Throwable, T] = {

  def compile[A](schema: PhantomTypeTest[A]): EntityType => Option[Document.Decoder[A]] =
    schema match {
      case PhantomTypeTest.Single(schema, _) =>
        // this is always Some, because the entity types are checked at the top level anyway (for easier error reporting)
        Function.const(Some(Document.Decoder.fromSchema(schema)))

      case m @ PhantomTypeTest.Many(lhs, rhs) =>
        val lhsCompiled = compile(lhs)
        val rhsCompiled = compile(rhs)

        // this might be possible to simplify
        val proof = summon[m.Of =:= (lhs.Of | rhs.Of)]

        tpe =>
          if lhs.matches(tpe) then lhsCompiled(tpe).map(_.map(proof(_)))
          else if rhs.matches(tpe) then rhsCompiled(tpe).map(_.map(proof))
          else None
    }

  val decoderCompiled = compile(summon[PhantomTypeTest[T]])

  json =>
    json
      .match {
        case DObject(value) =>
          value("type") match {
            case DString(value) => EntityType.fromString(value).toRight(??? /* todo have fun */ )
            case _              => ??? // other cases left as an exercise for the reader
          }

        // other cases left as an exercise for the reader
        case _ => ???
      }
      .flatMap { entityType =>
        // note: this `if` is a possibly premature optimization. This could just happen in `compile`.
        if summon[PhantomTypeTest[T]].matches(entityType)
        then
          decoderCompiled(entityType)
            .toRight(
              Exception(
                s"Could not find a decoder for $entityType - this shouldn't happen ;)"
              )
            )
            .flatMap(_.decode(json))
        else
          Left(
            Exception(
              s"Not a supported entity type: expected one of ${summon[PhantomTypeTest[T]].supportedTypes.mkString(", ")}, got $entityType"
            )
          )
      }
}

given PhantomTypeTest[Show] = PhantomTypeTest.instance(
  EntityType.SHOW,
  EntityType.SHOW_PODCAST,
)

given PhantomTypeTest[Channel] = PhantomTypeTest.instance(
  EntityType.CHANNEL_LINEAR,
  EntityType.CHANNEL_XTRA,
)

given PhantomTypeTest[AudioEpisode] = PhantomTypeTest.instance(
  EntityType.EPISODE_AUDIO
)

given PhantomTypeTest[VideoEpisode] = PhantomTypeTest.instance(
  EntityType.EPISODE_VIDEO
)

given PhantomTypeTest[ChannelLineup] = PhantomTypeTest.instance(
  EntityType.LINEUP
)

import Document.syntax.*

def assertMatches[Tpe <: CatalogEntity: PhantomTypeTest](actualType: EntityType, isOk: Boolean) =
  val decoded = decodePlease(Document.obj("id" -> "test", "type" -> actualType))
  assert(decoded.isRight == isOk, s"Expected ${if isOk then "right" else "left"}, got $decoded")

def assertMatchesFull[Tpe <: CatalogEntity: PhantomTypeTest](value: Document, isOk: Boolean) =
  val decoder = decodePleaseFull[Tpe]

  val decoded = decoder(value)
  assert(decoded.isRight == isOk, s"Expected ${if isOk then "right" else "left"}, got $decoded")

import Document.syntax.*

@main def demo =
  // thins
  assertMatches[Show](EntityType.SHOW, true)
  assertMatches[Show](EntityType.EPISODE_VIDEO, false)
  assertMatches[Show | AudioEpisode](EntityType.SHOW, true)
  assertMatches[Channel](EntityType.SHOW, false)
  assertMatches[Channel](EntityType.CHANNEL_LINEAR, true)
  assertMatches[Channel](EntityType.CHANNEL_XTRA, true)
  assertMatches[CatalogEntity](EntityType.CHANNEL_XTRA, true)

  // fulls

  // all good
  assertMatchesFull[Show](
    Document.obj("id" -> "test", "type" -> EntityType.SHOW.value, "showField" -> "test"),
    true,
  )

  // type is known but the schema doesn't match it
  assertMatchesFull[AudioEpisode](
    Document.obj("id" -> "test", "type" -> EntityType.SHOW.value, "audioField" -> "test"),
    false,
  )

  // type is known but the schema doesn't match it (union)
  assertMatchesFull[Show | AudioEpisode](
    Document.obj("id" -> "test", "type" -> EntityType.SHOW.value, "audioField" -> "test"),
    false,
  )

  // type known
  assertMatchesFull[Show | AudioEpisode](
    Document.obj("id" -> "test", "type" -> EntityType.EPISODE_AUDIO.value, "audioField" -> "test"),
    true,
  )

  // all types
  assertMatchesFull[CatalogEntity](
    Document.obj("id" -> "test", "type" -> EntityType.EPISODE_AUDIO.value, "audioField" -> "test"),
    true,
  )
