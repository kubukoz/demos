package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class SSEStatus(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: SSEStatus = this
}
object SSEStatus extends Enumeration[SSEStatus] with ShapeTag.Companion[SSEStatus] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "SSEStatus")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ENABLING"), name = Some(smithy.api.EnumConstantBodyName("ENABLING")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ENABLED"), name = Some(smithy.api.EnumConstantBodyName("ENABLED")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("DISABLING"), name = Some(smithy.api.EnumConstantBodyName("DISABLING")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("DISABLED"), name = Some(smithy.api.EnumConstantBodyName("DISABLED")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("UPDATING"), name = Some(smithy.api.EnumConstantBodyName("UPDATING")), documentation = None, tags = None, deprecated = None))),
  )

  case object ENABLING extends SSEStatus("ENABLING", "ENABLING", 0)
  case object ENABLED extends SSEStatus("ENABLED", "ENABLED", 1)
  case object DISABLING extends SSEStatus("DISABLING", "DISABLING", 2)
  case object DISABLED extends SSEStatus("DISABLED", "DISABLED", 3)
  case object UPDATING extends SSEStatus("UPDATING", "UPDATING", 4)

  val values: List[SSEStatus] = List(
    ENABLING,
    ENABLED,
    DISABLING,
    DISABLED,
    UPDATING,
  )
  implicit val schema: Schema[SSEStatus] = enumeration(values).withId(id).addHints(hints)
}