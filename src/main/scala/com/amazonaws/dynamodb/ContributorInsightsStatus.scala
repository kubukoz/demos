package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class ContributorInsightsStatus(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: ContributorInsightsStatus = this
}
object ContributorInsightsStatus extends Enumeration[ContributorInsightsStatus] with ShapeTag.Companion[ContributorInsightsStatus] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ContributorInsightsStatus")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ENABLING"), name = Some(smithy.api.EnumConstantBodyName("ENABLING")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ENABLED"), name = Some(smithy.api.EnumConstantBodyName("ENABLED")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("DISABLING"), name = Some(smithy.api.EnumConstantBodyName("DISABLING")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("DISABLED"), name = Some(smithy.api.EnumConstantBodyName("DISABLED")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("FAILED"), name = Some(smithy.api.EnumConstantBodyName("FAILED")), documentation = None, tags = None, deprecated = None))),
  )

  case object ENABLING extends ContributorInsightsStatus("ENABLING", "ENABLING", 0)
  case object ENABLED extends ContributorInsightsStatus("ENABLED", "ENABLED", 1)
  case object DISABLING extends ContributorInsightsStatus("DISABLING", "DISABLING", 2)
  case object DISABLED extends ContributorInsightsStatus("DISABLED", "DISABLED", 3)
  case object FAILED extends ContributorInsightsStatus("FAILED", "FAILED", 4)

  val values: List[ContributorInsightsStatus] = List(
    ENABLING,
    ENABLED,
    DISABLING,
    DISABLED,
    FAILED,
  )
  implicit val schema: Schema[ContributorInsightsStatus] = enumeration(values).withId(id).addHints(hints)
}