package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.recursive
import smithy4s.ShapeTag
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.union

sealed trait AttributeValue extends scala.Product with scala.Serializable {
  @inline final def widen: AttributeValue = this
}
object AttributeValue extends ShapeTag.Companion[AttributeValue] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "AttributeValue")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the data for an attribute.</p>\n        <p>Each attribute value is described as a name-value pair. The name is the data type, and\n            the value is the data itself.</p>\n        <p>For more information, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/HowItWorks.NamingRulesDataTypes.html#HowItWorks.DataTypes\">Data Types</a> in the <i>Amazon DynamoDB Developer\n            Guide</i>.</p>"),
  )

  case class SCase(s: StringAttributeValue) extends AttributeValue
  case class NCase(n: NumberAttributeValue) extends AttributeValue
  case class BCase(b: BinaryAttributeValue) extends AttributeValue
  case class SsCase(ss: List[StringAttributeValue]) extends AttributeValue
  case class NsCase(ns: List[NumberAttributeValue]) extends AttributeValue
  case class BsCase(bs: List[BinaryAttributeValue]) extends AttributeValue
  case class MCase(m: Map[AttributeName,com.amazonaws.dynamodb.AttributeValue]) extends AttributeValue
  case class LCase(l: List[com.amazonaws.dynamodb.AttributeValue]) extends AttributeValue
  case class NullCase(_null: NullAttributeValue) extends AttributeValue
  case class BoolCase(bool: BooleanAttributeValue) extends AttributeValue

  object SCase {
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>An attribute of type String. For example:</p>\n        <p>\n            <code>\"S\": \"Hello\"</code>\n         </p>"),
    )
    val schema: Schema[SCase] = bijection(StringAttributeValue.schema.addHints(hints), SCase(_), _.s)
    val alt = schema.oneOf[AttributeValue]("S")
  }
  object NCase {
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>An attribute of type Number. For example:</p>\n        <p>\n            <code>\"N\": \"123.45\"</code>\n         </p>\n        <p>Numbers are sent across the network to DynamoDB as strings, to maximize compatibility\n            across languages and libraries. However, DynamoDB treats them as number type attributes\n            for mathematical operations.</p>"),
    )
    val schema: Schema[NCase] = bijection(NumberAttributeValue.schema.addHints(hints), NCase(_), _.n)
    val alt = schema.oneOf[AttributeValue]("N")
  }
  object BCase {
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>An attribute of type Binary. For example:</p>\n        <p>\n            <code>\"B\": \"dGhpcyB0ZXh0IGlzIGJhc2U2NC1lbmNvZGVk\"</code>\n         </p>"),
    )
    val schema: Schema[BCase] = bijection(BinaryAttributeValue.schema.addHints(hints), BCase(_), _.b)
    val alt = schema.oneOf[AttributeValue]("B")
  }
  object SsCase {
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>An attribute of type String Set. For example:</p>\n        <p>\n            <code>\"SS\": [\"Giraffe\", \"Hippo\" ,\"Zebra\"]</code>\n         </p>"),
    )
    val schema: Schema[SsCase] = bijection(StringSetAttributeValue.underlyingSchema.addHints(hints), SsCase(_), _.ss)
    val alt = schema.oneOf[AttributeValue]("SS")
  }
  object NsCase {
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>An attribute of type Number Set. For example:</p>\n        <p>\n            <code>\"NS\": [\"42.2\", \"-19\", \"7.5\", \"3.14\"]</code>\n         </p>\n        <p>Numbers are sent across the network to DynamoDB as strings, to maximize compatibility\n            across languages and libraries. However, DynamoDB treats them as number type attributes\n            for mathematical operations.</p>"),
    )
    val schema: Schema[NsCase] = bijection(NumberSetAttributeValue.underlyingSchema.addHints(hints), NsCase(_), _.ns)
    val alt = schema.oneOf[AttributeValue]("NS")
  }
  object BsCase {
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>An attribute of type Binary Set. For example:</p>\n        <p>\n            <code>\"BS\": [\"U3Vubnk=\", \"UmFpbnk=\", \"U25vd3k=\"]</code>\n         </p>"),
    )
    val schema: Schema[BsCase] = bijection(BinarySetAttributeValue.underlyingSchema.addHints(hints), BsCase(_), _.bs)
    val alt = schema.oneOf[AttributeValue]("BS")
  }
  object MCase {
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>An attribute of type Map. For example:</p>\n        <p>\n            <code>\"M\": {\"Name\": {\"S\": \"Joe\"}, \"Age\": {\"N\": \"35\"}}</code>\n         </p>"),
    )
    val schema: Schema[MCase] = bijection(MapAttributeValue.underlyingSchema.addHints(hints), MCase(_), _.m)
    val alt = schema.oneOf[AttributeValue]("M")
  }
  object LCase {
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>An attribute of type List. For example:</p>\n        <p>\n            <code>\"L\": [ {\"S\": \"Cookies\"} , {\"S\": \"Coffee\"}, {\"N\": \"3.14159\"}]</code>\n         </p>"),
    )
    val schema: Schema[LCase] = bijection(ListAttributeValue.underlyingSchema.addHints(hints), LCase(_), _.l)
    val alt = schema.oneOf[AttributeValue]("L")
  }
  object NullCase {
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>An attribute of type Null. For example:</p>\n        <p>\n            <code>\"NULL\": true</code>\n         </p>"),
    )
    val schema: Schema[NullCase] = bijection(NullAttributeValue.schema.addHints(hints), NullCase(_), _._null)
    val alt = schema.oneOf[AttributeValue]("NULL")
  }
  object BoolCase {
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>An attribute of type Boolean. For example:</p>\n        <p>\n            <code>\"BOOL\": true</code>\n         </p>"),
    )
    val schema: Schema[BoolCase] = bijection(BooleanAttributeValue.schema.addHints(hints), BoolCase(_), _.bool)
    val alt = schema.oneOf[AttributeValue]("BOOL")
  }

  implicit val schema: Schema[AttributeValue] = recursive(union(
    SCase.alt,
    NCase.alt,
    BCase.alt,
    SsCase.alt,
    NsCase.alt,
    BsCase.alt,
    MCase.alt,
    LCase.alt,
    NullCase.alt,
    BoolCase.alt,
  ){
    case c : SCase => SCase.alt(c)
    case c : NCase => NCase.alt(c)
    case c : BCase => BCase.alt(c)
    case c : SsCase => SsCase.alt(c)
    case c : NsCase => NsCase.alt(c)
    case c : BsCase => BsCase.alt(c)
    case c : MCase => MCase.alt(c)
    case c : LCase => LCase.alt(c)
    case c : NullCase => NullCase.alt(c)
    case c : BoolCase => BoolCase.alt(c)
  }.withId(id).addHints(hints))
}