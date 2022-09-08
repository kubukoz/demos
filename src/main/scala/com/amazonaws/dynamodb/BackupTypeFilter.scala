package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class BackupTypeFilter(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: BackupTypeFilter = this
}
object BackupTypeFilter extends Enumeration[BackupTypeFilter] with ShapeTag.Companion[BackupTypeFilter] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BackupTypeFilter")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("USER"), name = Some(smithy.api.EnumConstantBodyName("USER")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("SYSTEM"), name = Some(smithy.api.EnumConstantBodyName("SYSTEM")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("AWS_BACKUP"), name = Some(smithy.api.EnumConstantBodyName("AWS_BACKUP")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ALL"), name = Some(smithy.api.EnumConstantBodyName("ALL")), documentation = None, tags = None, deprecated = None))),
  )

  case object USER extends BackupTypeFilter("USER", "USER", 0)
  case object SYSTEM extends BackupTypeFilter("SYSTEM", "SYSTEM", 1)
  case object AWS_BACKUP extends BackupTypeFilter("AWS_BACKUP", "AWS_BACKUP", 2)
  case object ALL extends BackupTypeFilter("ALL", "ALL", 3)

  val values: List[BackupTypeFilter] = List(
    USER,
    SYSTEM,
    AWS_BACKUP,
    ALL,
  )
  implicit val schema: Schema[BackupTypeFilter] = enumeration(values).withId(id).addHints(hints)
}