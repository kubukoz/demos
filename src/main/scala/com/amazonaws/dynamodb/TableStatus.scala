package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class TableStatus(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: TableStatus = this
}
object TableStatus extends Enumeration[TableStatus] with ShapeTag.Companion[TableStatus] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TableStatus")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("CREATING"), name = Some(smithy.api.EnumConstantBodyName("CREATING")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("UPDATING"), name = Some(smithy.api.EnumConstantBodyName("UPDATING")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("DELETING"), name = Some(smithy.api.EnumConstantBodyName("DELETING")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ACTIVE"), name = Some(smithy.api.EnumConstantBodyName("ACTIVE")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("INACCESSIBLE_ENCRYPTION_CREDENTIALS"), name = Some(smithy.api.EnumConstantBodyName("INACCESSIBLE_ENCRYPTION_CREDENTIALS")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ARCHIVING"), name = Some(smithy.api.EnumConstantBodyName("ARCHIVING")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ARCHIVED"), name = Some(smithy.api.EnumConstantBodyName("ARCHIVED")), documentation = None, tags = None, deprecated = None))),
  )

  case object CREATING extends TableStatus("CREATING", "CREATING", 0)
  case object UPDATING extends TableStatus("UPDATING", "UPDATING", 1)
  case object DELETING extends TableStatus("DELETING", "DELETING", 2)
  case object ACTIVE extends TableStatus("ACTIVE", "ACTIVE", 3)
  case object INACCESSIBLE_ENCRYPTION_CREDENTIALS extends TableStatus("INACCESSIBLE_ENCRYPTION_CREDENTIALS", "INACCESSIBLE_ENCRYPTION_CREDENTIALS", 4)
  case object ARCHIVING extends TableStatus("ARCHIVING", "ARCHIVING", 5)
  case object ARCHIVED extends TableStatus("ARCHIVED", "ARCHIVED", 6)

  val values: List[TableStatus] = List(
    CREATING,
    UPDATING,
    DELETING,
    ACTIVE,
    INACCESSIBLE_ENCRYPTION_CREDENTIALS,
    ARCHIVING,
    ARCHIVED,
  )
  implicit val schema: Schema[TableStatus] = enumeration(values).withId(id).addHints(hints)
}