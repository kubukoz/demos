package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class Projection(projectionType: Option[ProjectionType] = None, nonKeyAttributes: Option[List[NonKeyAttributeName]] = None)
object Projection extends ShapeTag.Companion[Projection] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "Projection")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents attributes that are copied (projected) from the table into an index. These\n            are in addition to the primary key attributes and index key attributes, which are\n            automatically projected.</p>"),
  )

  implicit val schema: Schema[Projection] = struct(
    ProjectionType.schema.optional[Projection]("ProjectionType", _.projectionType).addHints(smithy.api.Documentation("<p>The set of attributes that are projected into the index:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>KEYS_ONLY</code> - Only the index and primary keys are projected into the\n                    index.</p>\n            </li>\n            <li>\n                <p>\n                    <code>INCLUDE</code> - In addition to the attributes described in\n                        <code>KEYS_ONLY</code>, the secondary index will include other non-key\n                    attributes that you specify.</p>\n            </li>\n            <li>\n                <p>\n                    <code>ALL</code> - All of the table attributes are projected into the\n                    index.</p>\n            </li>\n         </ul>")),
    NonKeyAttributeNameList.underlyingSchema.optional[Projection]("NonKeyAttributes", _.nonKeyAttributes).addHints(smithy.api.Documentation("<p>Represents the non-key attribute names which will be projected into the index.</p>\n        <p>For local secondary indexes, the total count of <code>NonKeyAttributes</code> summed\n            across all of the local secondary indexes, must not exceed 100. If you project the same\n            attribute into two different indexes, this counts as two distinct attributes when\n            determining the total.</p>")),
  ){
    Projection.apply
  }.withId(id).addHints(hints)
}