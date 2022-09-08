package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ExportTableToPointInTimeInput(tableArn: TableArn, s3Bucket: S3Bucket, exportTime: Option[ExportTime] = None, clientToken: Option[ClientToken] = None, s3BucketOwner: Option[S3BucketOwner] = None, s3Prefix: Option[S3Prefix] = None, s3SseAlgorithm: Option[S3SseAlgorithm] = None, s3SseKmsKeyId: Option[S3SseKmsKeyId] = None, exportFormat: Option[ExportFormat] = None)
object ExportTableToPointInTimeInput extends ShapeTag.Companion[ExportTableToPointInTimeInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExportTableToPointInTimeInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[ExportTableToPointInTimeInput] = struct(
    TableArn.schema.required[ExportTableToPointInTimeInput]("TableArn", _.tableArn).addHints(smithy.api.Documentation("<p>The Amazon Resource Name (ARN) associated with the table to export.</p>"), smithy.api.Required()),
    S3Bucket.schema.required[ExportTableToPointInTimeInput]("S3Bucket", _.s3Bucket).addHints(smithy.api.Documentation("<p>The name of the Amazon S3 bucket to export the snapshot to.</p>"), smithy.api.Required()),
    ExportTime.schema.optional[ExportTableToPointInTimeInput]("ExportTime", _.exportTime).addHints(smithy.api.Documentation("<p>Time in the past from which to export table data, counted in seconds from the start of\n            the Unix epoch. The table export will be a snapshot of the table\'s state at this point\n            in time.</p>")),
    ClientToken.schema.optional[ExportTableToPointInTimeInput]("ClientToken", _.clientToken).addHints(smithy.api.Documentation("<p>Providing a <code>ClientToken</code> makes the call to\n                <code>ExportTableToPointInTimeInput</code> idempotent, meaning that multiple\n            identical calls have the same effect as one single call.</p>\n        <p>A client token is valid for 8 hours after the first request that uses it is completed.\n            After 8 hours, any request with the same client token is treated as a new request. Do\n            not resubmit the same request with the same client token for more than 8 hours, or the\n            result might not be idempotent.</p>\n        <p>If you submit a request with the same client token but a change in other parameters\n            within the 8-hour idempotency window, DynamoDB returns an\n                <code>IdempotentParameterMismatch</code> exception.</p>"), smithy.api.IdempotencyToken()),
    S3BucketOwner.schema.optional[ExportTableToPointInTimeInput]("S3BucketOwner", _.s3BucketOwner).addHints(smithy.api.Documentation("<p>The ID of the Amazon Web Services account that owns the bucket the export will be\n            stored in.</p>")),
    S3Prefix.schema.optional[ExportTableToPointInTimeInput]("S3Prefix", _.s3Prefix).addHints(smithy.api.Documentation("<p>The Amazon S3 bucket prefix to use as the file name and path of the exported\n            snapshot.</p>")),
    S3SseAlgorithm.schema.optional[ExportTableToPointInTimeInput]("S3SseAlgorithm", _.s3SseAlgorithm).addHints(smithy.api.Documentation("<p>Type of encryption used on the bucket where export data will be stored. Valid values\n            for <code>S3SseAlgorithm</code> are:</p>\n        <ul>\n            <li>\n                <p>\n                  <code>AES256</code> - server-side encryption with Amazon S3 managed\n                    keys</p>\n            </li>\n            <li>\n                <p>\n                  <code>KMS</code> - server-side encryption with KMS managed\n                    keys</p>\n            </li>\n         </ul>")),
    S3SseKmsKeyId.schema.optional[ExportTableToPointInTimeInput]("S3SseKmsKeyId", _.s3SseKmsKeyId).addHints(smithy.api.Documentation("<p>The ID of the KMS managed key used to encrypt the S3 bucket where\n            export data will be stored (if applicable).</p>")),
    ExportFormat.schema.optional[ExportTableToPointInTimeInput]("ExportFormat", _.exportFormat).addHints(smithy.api.Documentation("<p>The format for the exported data. Valid values for <code>ExportFormat</code> are\n                <code>DYNAMODB_JSON</code> or <code>ION</code>.</p>")),
  ){
    ExportTableToPointInTimeInput.apply
  }.withId(id).addHints(hints)
}