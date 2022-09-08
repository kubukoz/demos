package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class ReturnConsumedCapacity(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: ReturnConsumedCapacity = this
}
object ReturnConsumedCapacity extends Enumeration[ReturnConsumedCapacity] with ShapeTag.Companion[ReturnConsumedCapacity] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReturnConsumedCapacity")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Determines the level of detail about either provisioned or on-demand throughput\n            consumption that is returned in the response:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>INDEXES</code> - The response includes the aggregate\n                        <code>ConsumedCapacity</code> for the operation, together with\n                        <code>ConsumedCapacity</code> for each table and secondary index that was\n                    accessed.</p>\n                <p>Note that some operations, such as <code>GetItem</code> and\n                        <code>BatchGetItem</code>, do not access any indexes at all. In these cases,\n                    specifying <code>INDEXES</code> will only return <code>ConsumedCapacity</code>\n                    information for table(s).</p>\n            </li>\n            <li>\n                <p>\n                    <code>TOTAL</code> - The response includes only the aggregate\n                        <code>ConsumedCapacity</code> for the operation.</p>\n            </li>\n            <li>\n                <p>\n                    <code>NONE</code> - No <code>ConsumedCapacity</code> details are included in the\n                    response.</p>\n            </li>\n         </ul>"),
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("INDEXES"), name = Some(smithy.api.EnumConstantBodyName("INDEXES")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("TOTAL"), name = Some(smithy.api.EnumConstantBodyName("TOTAL")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("NONE"), name = Some(smithy.api.EnumConstantBodyName("NONE")), documentation = None, tags = None, deprecated = None))),
  )

  case object INDEXES extends ReturnConsumedCapacity("INDEXES", "INDEXES", 0)
  case object TOTAL extends ReturnConsumedCapacity("TOTAL", "TOTAL", 1)
  case object NONE extends ReturnConsumedCapacity("NONE", "NONE", 2)

  val values: List[ReturnConsumedCapacity] = List(
    INDEXES,
    TOTAL,
    NONE,
  )
  implicit val schema: Schema[ReturnConsumedCapacity] = enumeration(values).withId(id).addHints(hints)
}