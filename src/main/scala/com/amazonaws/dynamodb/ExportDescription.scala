package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ExportDescription(exportArn: Option[ExportArn] = None, exportStatus: Option[ExportStatus] = None, startTime: Option[ExportStartTime] = None, endTime: Option[ExportEndTime] = None, exportManifest: Option[ExportManifest] = None, tableArn: Option[TableArn] = None, tableId: Option[TableId] = None, exportTime: Option[ExportTime] = None, clientToken: Option[ClientToken] = None, s3Bucket: Option[S3Bucket] = None, s3BucketOwner: Option[S3BucketOwner] = None, s3Prefix: Option[S3Prefix] = None, s3SseAlgorithm: Option[S3SseAlgorithm] = None, s3SseKmsKeyId: Option[S3SseKmsKeyId] = None, failureCode: Option[FailureCode] = None, failureMessage: Option[FailureMessage] = None, exportFormat: Option[ExportFormat] = None, billedSizeBytes: Option[BilledSizeBytes] = None, itemCount: Option[ItemCount] = None)
object ExportDescription extends ShapeTag.Companion[ExportDescription] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExportDescription")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the properties of the exported table.</p>"),
  )

  implicit val schema: Schema[ExportDescription] = struct(
    ExportArn.schema.optional[ExportDescription]("ExportArn", _.exportArn).addHints(smithy.api.Documentation("<p>The Amazon Resource Name (ARN) of the table export.</p>")),
    ExportStatus.schema.optional[ExportDescription]("ExportStatus", _.exportStatus).addHints(smithy.api.Documentation("<p>Export can be in one of the following states: IN_PROGRESS, COMPLETED, or\n            FAILED.</p>")),
    ExportStartTime.schema.optional[ExportDescription]("StartTime", _.startTime).addHints(smithy.api.Documentation("<p>The time at which the export task began.</p>")),
    ExportEndTime.schema.optional[ExportDescription]("EndTime", _.endTime).addHints(smithy.api.Documentation("<p>The time at which the export task completed.</p>")),
    ExportManifest.schema.optional[ExportDescription]("ExportManifest", _.exportManifest).addHints(smithy.api.Documentation("<p>The name of the manifest file for the export task.</p>")),
    TableArn.schema.optional[ExportDescription]("TableArn", _.tableArn).addHints(smithy.api.Documentation("<p>The Amazon Resource Name (ARN) of the table that was exported.</p>")),
    TableId.schema.optional[ExportDescription]("TableId", _.tableId).addHints(smithy.api.Documentation("<p>Unique ID of the table that was exported.</p>")),
    ExportTime.schema.optional[ExportDescription]("ExportTime", _.exportTime).addHints(smithy.api.Documentation("<p>Point in time from which table data was exported.</p>")),
    ClientToken.schema.optional[ExportDescription]("ClientToken", _.clientToken).addHints(smithy.api.Documentation("<p>The client token that was provided for the export task. A client token makes calls to\n                <code>ExportTableToPointInTimeInput</code> idempotent, meaning that multiple\n            identical calls have the same effect as one single call.</p>")),
    S3Bucket.schema.optional[ExportDescription]("S3Bucket", _.s3Bucket).addHints(smithy.api.Documentation("<p>The name of the Amazon S3 bucket containing the export.</p>")),
    S3BucketOwner.schema.optional[ExportDescription]("S3BucketOwner", _.s3BucketOwner).addHints(smithy.api.Documentation("<p>The ID of the Amazon Web Services account that owns the bucket containing the\n            export.</p>")),
    S3Prefix.schema.optional[ExportDescription]("S3Prefix", _.s3Prefix).addHints(smithy.api.Documentation("<p>The Amazon S3 bucket prefix used as the file name and path of the exported\n            snapshot.</p>")),
    S3SseAlgorithm.schema.optional[ExportDescription]("S3SseAlgorithm", _.s3SseAlgorithm).addHints(smithy.api.Documentation("<p>Type of encryption used on the bucket where export data is stored. Valid values for\n                <code>S3SseAlgorithm</code> are:</p>\n        <ul>\n            <li>\n                <p>\n                  <code>AES256</code> - server-side encryption with Amazon S3 managed\n                    keys</p>\n            </li>\n            <li>\n                <p>\n                  <code>KMS</code> - server-side encryption with KMS managed\n                    keys</p>\n            </li>\n         </ul>")),
    S3SseKmsKeyId.schema.optional[ExportDescription]("S3SseKmsKeyId", _.s3SseKmsKeyId).addHints(smithy.api.Documentation("<p>The ID of the KMS managed key used to encrypt the S3 bucket where\n            export data is stored (if applicable).</p>")),
    FailureCode.schema.optional[ExportDescription]("FailureCode", _.failureCode).addHints(smithy.api.Documentation("<p>Status code for the result of the failed export.</p>")),
    FailureMessage.schema.optional[ExportDescription]("FailureMessage", _.failureMessage).addHints(smithy.api.Documentation("<p>Export failure reason description.</p>")),
    ExportFormat.schema.optional[ExportDescription]("ExportFormat", _.exportFormat).addHints(smithy.api.Documentation("<p>The format of the exported data. Valid values for <code>ExportFormat</code> are\n                <code>DYNAMODB_JSON</code> or <code>ION</code>.</p>")),
    BilledSizeBytes.schema.optional[ExportDescription]("BilledSizeBytes", _.billedSizeBytes).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The billable size of the table export.</p>")),
    ItemCount.schema.optional[ExportDescription]("ItemCount", _.itemCount).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The number of items exported.</p>")),
  ){
    ExportDescription.apply
  }.withId(id).addHints(hints)
}