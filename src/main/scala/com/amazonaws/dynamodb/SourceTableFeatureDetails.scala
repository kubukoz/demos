package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class SourceTableFeatureDetails(localSecondaryIndexes: Option[List[LocalSecondaryIndexInfo]] = None, globalSecondaryIndexes: Option[List[GlobalSecondaryIndexInfo]] = None, streamDescription: Option[StreamSpecification] = None, timeToLiveDescription: Option[TimeToLiveDescription] = None, sSEDescription: Option[SSEDescription] = None)
object SourceTableFeatureDetails extends ShapeTag.Companion[SourceTableFeatureDetails] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "SourceTableFeatureDetails")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Contains the details of the features enabled on the table when the backup was created.\n            For example, LSIs, GSIs, streams, TTL. </p>"),
  )

  implicit val schema: Schema[SourceTableFeatureDetails] = struct(
    LocalSecondaryIndexes.underlyingSchema.optional[SourceTableFeatureDetails]("LocalSecondaryIndexes", _.localSecondaryIndexes).addHints(smithy.api.Documentation("<p>Represents the LSI properties for the table when the backup was created. It includes\n            the IndexName, KeySchema and Projection for the LSIs on the table at the time of backup.\n        </p>")),
    GlobalSecondaryIndexes.underlyingSchema.optional[SourceTableFeatureDetails]("GlobalSecondaryIndexes", _.globalSecondaryIndexes).addHints(smithy.api.Documentation("<p>Represents the GSI properties for the table when the backup was created. It includes\n            the IndexName, KeySchema, Projection, and ProvisionedThroughput for the GSIs on the\n            table at the time of backup. </p>")),
    StreamSpecification.schema.optional[SourceTableFeatureDetails]("StreamDescription", _.streamDescription).addHints(smithy.api.Documentation("<p>Stream settings on the table when the backup was created.</p>")),
    TimeToLiveDescription.schema.optional[SourceTableFeatureDetails]("TimeToLiveDescription", _.timeToLiveDescription).addHints(smithy.api.Documentation("<p>Time to Live settings on the table when the backup was created.</p>")),
    SSEDescription.schema.optional[SourceTableFeatureDetails]("SSEDescription", _.sSEDescription).addHints(smithy.api.Documentation("<p>The description of the server-side encryption status on the table when the backup was\n            created.</p>")),
  ){
    SourceTableFeatureDetails.apply
  }.withId(id).addHints(hints)
}