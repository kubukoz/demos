package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class ContributorInsightsAction(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: ContributorInsightsAction = this
}
object ContributorInsightsAction extends Enumeration[ContributorInsightsAction] with ShapeTag.Companion[ContributorInsightsAction] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ContributorInsightsAction")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ENABLE"), name = Some(smithy.api.EnumConstantBodyName("ENABLE")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("DISABLE"), name = Some(smithy.api.EnumConstantBodyName("DISABLE")), documentation = None, tags = None, deprecated = None))),
  )

  case object ENABLE extends ContributorInsightsAction("ENABLE", "ENABLE", 0)
  case object DISABLE extends ContributorInsightsAction("DISABLE", "DISABLE", 1)

  val values: List[ContributorInsightsAction] = List(
    ENABLE,
    DISABLE,
  )
  implicit val schema: Schema[ContributorInsightsAction] = enumeration(values).withId(id).addHints(hints)
}