package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class SSEDescription(status: Option[SSEStatus] = None, sSEType: Option[SSEType] = None, kMSMasterKeyArn: Option[KMSMasterKeyArn] = None, inaccessibleEncryptionDateTime: Option[Date] = None)
object SSEDescription extends ShapeTag.Companion[SSEDescription] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "SSEDescription")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>The description of the server-side encryption status on the specified table.</p>"),
  )

  implicit val schema: Schema[SSEDescription] = struct(
    SSEStatus.schema.optional[SSEDescription]("Status", _.status).addHints(smithy.api.Documentation("<p>Represents the current state of server-side encryption. The only supported values\n            are:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>ENABLED</code> - Server-side encryption is enabled.</p>\n            </li>\n            <li>\n                <p>\n                    <code>UPDATING</code> - Server-side encryption is being updated.</p>\n            </li>\n         </ul>")),
    SSEType.schema.optional[SSEDescription]("SSEType", _.sSEType).addHints(smithy.api.Documentation("<p>Server-side encryption type. The only supported value is:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>KMS</code> - Server-side encryption that uses Key Management Service. The\n                    key is stored in your account and is managed by KMS (KMS charges apply).</p>\n            </li>\n         </ul>")),
    KMSMasterKeyArn.schema.optional[SSEDescription]("KMSMasterKeyArn", _.kMSMasterKeyArn).addHints(smithy.api.Documentation("<p>The KMS key ARN used for the KMS\n            encryption.</p>")),
    Date.schema.optional[SSEDescription]("InaccessibleEncryptionDateTime", _.inaccessibleEncryptionDateTime).addHints(smithy.api.Documentation("<p>Indicates the time, in UNIX epoch date format, when DynamoDB detected that\n            the table\'s KMS key was inaccessible. This attribute will automatically\n            be cleared when DynamoDB detects that the table\'s KMS key is accessible\n            again. DynamoDB will initiate the table archival process when table\'s KMS key remains inaccessible for more than seven days from this date.</p>")),
  ){
    SSEDescription.apply
  }.withId(id).addHints(hints)
}