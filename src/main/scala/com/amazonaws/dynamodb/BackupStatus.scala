package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class BackupStatus(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: BackupStatus = this
}
object BackupStatus extends Enumeration[BackupStatus] with ShapeTag.Companion[BackupStatus] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BackupStatus")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("CREATING"), name = Some(smithy.api.EnumConstantBodyName("CREATING")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("DELETED"), name = Some(smithy.api.EnumConstantBodyName("DELETED")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("AVAILABLE"), name = Some(smithy.api.EnumConstantBodyName("AVAILABLE")), documentation = None, tags = None, deprecated = None))),
  )

  case object CREATING extends BackupStatus("CREATING", "CREATING", 0)
  case object DELETED extends BackupStatus("DELETED", "DELETED", 1)
  case object AVAILABLE extends BackupStatus("AVAILABLE", "AVAILABLE", 2)

  val values: List[BackupStatus] = List(
    CREATING,
    DELETED,
    AVAILABLE,
  )
  implicit val schema: Schema[BackupStatus] = enumeration(values).withId(id).addHints(hints)
}