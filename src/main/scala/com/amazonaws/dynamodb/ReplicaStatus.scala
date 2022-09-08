package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class ReplicaStatus(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: ReplicaStatus = this
}
object ReplicaStatus extends Enumeration[ReplicaStatus] with ShapeTag.Companion[ReplicaStatus] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaStatus")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("CREATING"), name = Some(smithy.api.EnumConstantBodyName("CREATING")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("CREATION_FAILED"), name = Some(smithy.api.EnumConstantBodyName("CREATION_FAILED")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("UPDATING"), name = Some(smithy.api.EnumConstantBodyName("UPDATING")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("DELETING"), name = Some(smithy.api.EnumConstantBodyName("DELETING")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ACTIVE"), name = Some(smithy.api.EnumConstantBodyName("ACTIVE")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("REGION_DISABLED"), name = Some(smithy.api.EnumConstantBodyName("REGION_DISABLED")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("INACCESSIBLE_ENCRYPTION_CREDENTIALS"), name = Some(smithy.api.EnumConstantBodyName("INACCESSIBLE_ENCRYPTION_CREDENTIALS")), documentation = None, tags = None, deprecated = None))),
  )

  case object CREATING extends ReplicaStatus("CREATING", "CREATING", 0)
  case object CREATION_FAILED extends ReplicaStatus("CREATION_FAILED", "CREATION_FAILED", 1)
  case object UPDATING extends ReplicaStatus("UPDATING", "UPDATING", 2)
  case object DELETING extends ReplicaStatus("DELETING", "DELETING", 3)
  case object ACTIVE extends ReplicaStatus("ACTIVE", "ACTIVE", 4)
  case object REGION_DISABLED extends ReplicaStatus("REGION_DISABLED", "REGION_DISABLED", 5)
  case object INACCESSIBLE_ENCRYPTION_CREDENTIALS extends ReplicaStatus("INACCESSIBLE_ENCRYPTION_CREDENTIALS", "INACCESSIBLE_ENCRYPTION_CREDENTIALS", 6)

  val values: List[ReplicaStatus] = List(
    CREATING,
    CREATION_FAILED,
    UPDATING,
    DELETING,
    ACTIVE,
    REGION_DISABLED,
    INACCESSIBLE_ENCRYPTION_CREDENTIALS,
  )
  implicit val schema: Schema[ReplicaStatus] = enumeration(values).withId(id).addHints(hints)
}