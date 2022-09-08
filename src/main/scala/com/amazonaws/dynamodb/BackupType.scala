package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class BackupType(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: BackupType = this
}
object BackupType extends Enumeration[BackupType] with ShapeTag.Companion[BackupType] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BackupType")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("USER"), name = Some(smithy.api.EnumConstantBodyName("USER")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("SYSTEM"), name = Some(smithy.api.EnumConstantBodyName("SYSTEM")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("AWS_BACKUP"), name = Some(smithy.api.EnumConstantBodyName("AWS_BACKUP")), documentation = None, tags = None, deprecated = None))),
  )

  case object USER extends BackupType("USER", "USER", 0)
  case object SYSTEM extends BackupType("SYSTEM", "SYSTEM", 1)
  case object AWS_BACKUP extends BackupType("AWS_BACKUP", "AWS_BACKUP", 2)

  val values: List[BackupType] = List(
    USER,
    SYSTEM,
    AWS_BACKUP,
  )
  implicit val schema: Schema[BackupType] = enumeration(values).withId(id).addHints(hints)
}