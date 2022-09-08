package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class BatchStatementErrorCodeEnum(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: BatchStatementErrorCodeEnum = this
}
object BatchStatementErrorCodeEnum extends Enumeration[BatchStatementErrorCodeEnum] with ShapeTag.Companion[BatchStatementErrorCodeEnum] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BatchStatementErrorCodeEnum")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ConditionalCheckFailed"), name = Some(smithy.api.EnumConstantBodyName("ConditionalCheckFailed")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ItemCollectionSizeLimitExceeded"), name = Some(smithy.api.EnumConstantBodyName("ItemCollectionSizeLimitExceeded")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("RequestLimitExceeded"), name = Some(smithy.api.EnumConstantBodyName("RequestLimitExceeded")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ValidationError"), name = Some(smithy.api.EnumConstantBodyName("ValidationError")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ProvisionedThroughputExceeded"), name = Some(smithy.api.EnumConstantBodyName("ProvisionedThroughputExceeded")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("TransactionConflict"), name = Some(smithy.api.EnumConstantBodyName("TransactionConflict")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ThrottlingError"), name = Some(smithy.api.EnumConstantBodyName("ThrottlingError")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("InternalServerError"), name = Some(smithy.api.EnumConstantBodyName("InternalServerError")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ResourceNotFound"), name = Some(smithy.api.EnumConstantBodyName("ResourceNotFound")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("AccessDenied"), name = Some(smithy.api.EnumConstantBodyName("AccessDenied")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("DuplicateItem"), name = Some(smithy.api.EnumConstantBodyName("DuplicateItem")), documentation = None, tags = None, deprecated = None))),
  )

  case object ConditionalCheckFailed extends BatchStatementErrorCodeEnum("ConditionalCheckFailed", "ConditionalCheckFailed", 0)
  case object ItemCollectionSizeLimitExceeded extends BatchStatementErrorCodeEnum("ItemCollectionSizeLimitExceeded", "ItemCollectionSizeLimitExceeded", 1)
  case object RequestLimitExceeded extends BatchStatementErrorCodeEnum("RequestLimitExceeded", "RequestLimitExceeded", 2)
  case object ValidationError extends BatchStatementErrorCodeEnum("ValidationError", "ValidationError", 3)
  case object ProvisionedThroughputExceeded extends BatchStatementErrorCodeEnum("ProvisionedThroughputExceeded", "ProvisionedThroughputExceeded", 4)
  case object TransactionConflict extends BatchStatementErrorCodeEnum("TransactionConflict", "TransactionConflict", 5)
  case object ThrottlingError extends BatchStatementErrorCodeEnum("ThrottlingError", "ThrottlingError", 6)
  case object InternalServerError extends BatchStatementErrorCodeEnum("InternalServerError", "InternalServerError", 7)
  case object ResourceNotFound extends BatchStatementErrorCodeEnum("ResourceNotFound", "ResourceNotFound", 8)
  case object AccessDenied extends BatchStatementErrorCodeEnum("AccessDenied", "AccessDenied", 9)
  case object DuplicateItem extends BatchStatementErrorCodeEnum("DuplicateItem", "DuplicateItem", 10)

  val values: List[BatchStatementErrorCodeEnum] = List(
    ConditionalCheckFailed,
    ItemCollectionSizeLimitExceeded,
    RequestLimitExceeded,
    ValidationError,
    ProvisionedThroughputExceeded,
    TransactionConflict,
    ThrottlingError,
    InternalServerError,
    ResourceNotFound,
    AccessDenied,
    DuplicateItem,
  )
  implicit val schema: Schema[BatchStatementErrorCodeEnum] = enumeration(values).withId(id).addHints(hints)
}