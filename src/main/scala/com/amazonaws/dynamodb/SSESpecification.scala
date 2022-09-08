package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class SSESpecification(enabled: Option[SSEEnabled] = None, sSEType: Option[SSEType] = None, kMSMasterKeyId: Option[KMSMasterKeyId] = None)
object SSESpecification extends ShapeTag.Companion[SSESpecification] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "SSESpecification")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the settings used to enable server-side encryption.</p>"),
  )

  implicit val schema: Schema[SSESpecification] = struct(
    SSEEnabled.schema.optional[SSESpecification]("Enabled", _.enabled).addHints(smithy.api.Box(), smithy.api.Documentation("<p>Indicates whether server-side encryption is done using an Amazon Web Services managed\n            key or an Amazon Web Services owned key. If enabled (true), server-side encryption type\n            is set to <code>KMS</code> and an Amazon Web Services managed key is used (KMS charges apply). If disabled (false) or not specified, server-side\n            encryption is set to Amazon Web Services owned key.</p>")),
    SSEType.schema.optional[SSESpecification]("SSEType", _.sSEType).addHints(smithy.api.Documentation("<p>Server-side encryption type. The only supported value is:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>KMS</code> - Server-side encryption that uses Key Management Service. The\n                    key is stored in your account and is managed by KMS (KMS charges apply).</p>\n            </li>\n         </ul>")),
    KMSMasterKeyId.schema.optional[SSESpecification]("KMSMasterKeyId", _.kMSMasterKeyId).addHints(smithy.api.Documentation("<p>The KMS key that should be used for the KMS encryption.\n            To specify a key, use its key ID, Amazon Resource Name (ARN), alias name, or alias ARN.\n            Note that you should only provide this parameter if the key is different from the\n            default DynamoDB key <code>alias/aws/dynamodb</code>.</p>")),
  ){
    SSESpecification.apply
  }.withId(id).addHints(hints)
}