package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class BackupDescription(backupDetails: Option[BackupDetails] = None, sourceTableDetails: Option[SourceTableDetails] = None, sourceTableFeatureDetails: Option[SourceTableFeatureDetails] = None)
object BackupDescription extends ShapeTag.Companion[BackupDescription] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BackupDescription")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Contains the description of the backup created for the table.</p>"),
  )

  implicit val schema: Schema[BackupDescription] = struct(
    BackupDetails.schema.optional[BackupDescription]("BackupDetails", _.backupDetails).addHints(smithy.api.Documentation("<p>Contains the details of the backup created for the table. </p>")),
    SourceTableDetails.schema.optional[BackupDescription]("SourceTableDetails", _.sourceTableDetails).addHints(smithy.api.Documentation("<p>Contains the details of the table when the backup was created. </p>")),
    SourceTableFeatureDetails.schema.optional[BackupDescription]("SourceTableFeatureDetails", _.sourceTableFeatureDetails).addHints(smithy.api.Documentation("<p>Contains the details of the features enabled on the table when the backup was created.\n            For example, LSIs, GSIs, streams, TTL.</p>")),
  ){
    BackupDescription.apply
  }.withId(id).addHints(hints)
}