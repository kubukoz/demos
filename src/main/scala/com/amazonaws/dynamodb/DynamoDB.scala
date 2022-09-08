package com.amazonaws.dynamodb

import smithy4s.Errorable
import smithy4s.Schema
import smithy4s.schema.Schema.unit
import smithy4s.Hints
import smithy4s.StreamingSchema
import smithy4s.Transformation
import smithy4s.Monadic
import smithy4s.ShapeId
import smithy4s.Endpoint
import smithy4s.Service
import smithy4s.ShapeTag
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.union
import smithy4s.schema.Schema.UnionSchema

trait DynamoDBGen[F[_, _, _, _, _]] {
  self =>

  def batchExecuteStatement(statements: List[BatchStatementRequest], returnConsumedCapacity: Option[ReturnConsumedCapacity] = None) : F[BatchExecuteStatementInput, DynamoDBGen.BatchExecuteStatementError, BatchExecuteStatementOutput, Nothing, Nothing]
  def batchGetItem(requestItems: Map[TableName,KeysAndAttributes], returnConsumedCapacity: Option[ReturnConsumedCapacity] = None) : F[BatchGetItemInput, DynamoDBGen.BatchGetItemError, BatchGetItemOutput, Nothing, Nothing]
  def batchWriteItem(requestItems: Map[TableName,List[WriteRequest]], returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, returnItemCollectionMetrics: Option[ReturnItemCollectionMetrics] = None) : F[BatchWriteItemInput, DynamoDBGen.BatchWriteItemError, BatchWriteItemOutput, Nothing, Nothing]
  def createBackup(tableName: TableName, backupName: BackupName) : F[CreateBackupInput, DynamoDBGen.CreateBackupError, CreateBackupOutput, Nothing, Nothing]
  def createGlobalTable(globalTableName: TableName, replicationGroup: List[Replica]) : F[CreateGlobalTableInput, DynamoDBGen.CreateGlobalTableError, CreateGlobalTableOutput, Nothing, Nothing]
  def createTable(attributeDefinitions: List[AttributeDefinition], tableName: TableName, keySchema: List[KeySchemaElement], localSecondaryIndexes: Option[List[LocalSecondaryIndex]] = None, globalSecondaryIndexes: Option[List[GlobalSecondaryIndex]] = None, billingMode: Option[BillingMode] = None, provisionedThroughput: Option[ProvisionedThroughput] = None, streamSpecification: Option[StreamSpecification] = None, sSESpecification: Option[SSESpecification] = None, tags: Option[List[Tag]] = None, tableClass: Option[TableClass] = None) : F[CreateTableInput, DynamoDBGen.CreateTableError, CreateTableOutput, Nothing, Nothing]
  def deleteBackup(backupArn: BackupArn) : F[DeleteBackupInput, DynamoDBGen.DeleteBackupError, DeleteBackupOutput, Nothing, Nothing]
  def deleteItem(tableName: TableName, key: Map[AttributeName,AttributeValue], expected: Option[Map[AttributeName,ExpectedAttributeValue]] = None, conditionalOperator: Option[ConditionalOperator] = None, returnValues: Option[ReturnValue] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, returnItemCollectionMetrics: Option[ReturnItemCollectionMetrics] = None, conditionExpression: Option[ConditionExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None, expressionAttributeValues: Option[Map[ExpressionAttributeValueVariable,AttributeValue]] = None) : F[DeleteItemInput, DynamoDBGen.DeleteItemError, DeleteItemOutput, Nothing, Nothing]
  def deleteTable(tableName: TableName) : F[DeleteTableInput, DynamoDBGen.DeleteTableError, DeleteTableOutput, Nothing, Nothing]
  def describeBackup(backupArn: BackupArn) : F[DescribeBackupInput, DynamoDBGen.DescribeBackupError, DescribeBackupOutput, Nothing, Nothing]
  def describeContinuousBackups(tableName: TableName) : F[DescribeContinuousBackupsInput, DynamoDBGen.DescribeContinuousBackupsError, DescribeContinuousBackupsOutput, Nothing, Nothing]
  def describeContributorInsights(tableName: TableName, indexName: Option[IndexName] = None) : F[DescribeContributorInsightsInput, DynamoDBGen.DescribeContributorInsightsError, DescribeContributorInsightsOutput, Nothing, Nothing]
  def describeEndpoints() : F[DescribeEndpointsRequest, Nothing, DescribeEndpointsResponse, Nothing, Nothing]
  def describeExport(exportArn: ExportArn) : F[DescribeExportInput, DynamoDBGen.DescribeExportError, DescribeExportOutput, Nothing, Nothing]
  def describeGlobalTable(globalTableName: TableName) : F[DescribeGlobalTableInput, DynamoDBGen.DescribeGlobalTableError, DescribeGlobalTableOutput, Nothing, Nothing]
  def describeGlobalTableSettings(globalTableName: TableName) : F[DescribeGlobalTableSettingsInput, DynamoDBGen.DescribeGlobalTableSettingsError, DescribeGlobalTableSettingsOutput, Nothing, Nothing]
  def describeKinesisStreamingDestination(tableName: TableName) : F[DescribeKinesisStreamingDestinationInput, DynamoDBGen.DescribeKinesisStreamingDestinationError, DescribeKinesisStreamingDestinationOutput, Nothing, Nothing]
  def describeLimits() : F[DescribeLimitsInput, DynamoDBGen.DescribeLimitsError, DescribeLimitsOutput, Nothing, Nothing]
  def describeTable(tableName: TableName) : F[DescribeTableInput, DynamoDBGen.DescribeTableError, DescribeTableOutput, Nothing, Nothing]
  def describeTableReplicaAutoScaling(tableName: TableName) : F[DescribeTableReplicaAutoScalingInput, DynamoDBGen.DescribeTableReplicaAutoScalingError, DescribeTableReplicaAutoScalingOutput, Nothing, Nothing]
  def describeTimeToLive(tableName: TableName) : F[DescribeTimeToLiveInput, DynamoDBGen.DescribeTimeToLiveError, DescribeTimeToLiveOutput, Nothing, Nothing]
  def disableKinesisStreamingDestination(tableName: TableName, streamArn: StreamArn) : F[KinesisStreamingDestinationInput, DynamoDBGen.DisableKinesisStreamingDestinationError, KinesisStreamingDestinationOutput, Nothing, Nothing]
  def enableKinesisStreamingDestination(tableName: TableName, streamArn: StreamArn) : F[KinesisStreamingDestinationInput, DynamoDBGen.EnableKinesisStreamingDestinationError, KinesisStreamingDestinationOutput, Nothing, Nothing]
  def executeStatement(statement: PartiQLStatement, parameters: Option[List[AttributeValue]] = None, consistentRead: Option[ConsistentRead] = None, nextToken: Option[PartiQLNextToken] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, limit: Option[PositiveIntegerObject] = None) : F[ExecuteStatementInput, DynamoDBGen.ExecuteStatementError, ExecuteStatementOutput, Nothing, Nothing]
  def executeTransaction(transactStatements: List[ParameterizedStatement], clientRequestToken: Option[ClientRequestToken] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None) : F[ExecuteTransactionInput, DynamoDBGen.ExecuteTransactionError, ExecuteTransactionOutput, Nothing, Nothing]
  def exportTableToPointInTime(tableArn: TableArn, s3Bucket: S3Bucket, exportTime: Option[ExportTime] = None, clientToken: Option[ClientToken] = None, s3BucketOwner: Option[S3BucketOwner] = None, s3Prefix: Option[S3Prefix] = None, s3SseAlgorithm: Option[S3SseAlgorithm] = None, s3SseKmsKeyId: Option[S3SseKmsKeyId] = None, exportFormat: Option[ExportFormat] = None) : F[ExportTableToPointInTimeInput, DynamoDBGen.ExportTableToPointInTimeError, ExportTableToPointInTimeOutput, Nothing, Nothing]
  def getItem(tableName: TableName, key: Map[AttributeName,AttributeValue], attributesToGet: Option[List[AttributeName]] = None, consistentRead: Option[ConsistentRead] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, projectionExpression: Option[ProjectionExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None) : F[GetItemInput, DynamoDBGen.GetItemError, GetItemOutput, Nothing, Nothing]
  def listBackups(tableName: Option[TableName] = None, limit: Option[BackupsInputLimit] = None, timeRangeLowerBound: Option[TimeRangeLowerBound] = None, timeRangeUpperBound: Option[TimeRangeUpperBound] = None, exclusiveStartBackupArn: Option[BackupArn] = None, backupType: Option[BackupTypeFilter] = None) : F[ListBackupsInput, DynamoDBGen.ListBackupsError, ListBackupsOutput, Nothing, Nothing]
  def listContributorInsights(maxResults: ListContributorInsightsLimit = com.amazonaws.dynamodb.ListContributorInsightsLimit(0), tableName: Option[TableName] = None, nextToken: Option[NextTokenString] = None) : F[ListContributorInsightsInput, DynamoDBGen.ListContributorInsightsError, ListContributorInsightsOutput, Nothing, Nothing]
  def listExports(tableArn: Option[TableArn] = None, maxResults: Option[ListExportsMaxLimit] = None, nextToken: Option[ExportNextToken] = None) : F[ListExportsInput, DynamoDBGen.ListExportsError, ListExportsOutput, Nothing, Nothing]
  def listGlobalTables(exclusiveStartGlobalTableName: Option[TableName] = None, limit: Option[PositiveIntegerObject] = None, regionName: Option[RegionName] = None) : F[ListGlobalTablesInput, DynamoDBGen.ListGlobalTablesError, ListGlobalTablesOutput, Nothing, Nothing]
  def listTables(exclusiveStartTableName: Option[TableName] = None, limit: Option[ListTablesInputLimit] = None) : F[ListTablesInput, DynamoDBGen.ListTablesError, ListTablesOutput, Nothing, Nothing]
  def listTagsOfResource(resourceArn: ResourceArnString, nextToken: Option[NextTokenString] = None) : F[ListTagsOfResourceInput, DynamoDBGen.ListTagsOfResourceError, ListTagsOfResourceOutput, Nothing, Nothing]
  def putItem(tableName: TableName, item: Map[AttributeName,AttributeValue], expected: Option[Map[AttributeName,ExpectedAttributeValue]] = None, returnValues: Option[ReturnValue] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, returnItemCollectionMetrics: Option[ReturnItemCollectionMetrics] = None, conditionalOperator: Option[ConditionalOperator] = None, conditionExpression: Option[ConditionExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None, expressionAttributeValues: Option[Map[ExpressionAttributeValueVariable,AttributeValue]] = None) : F[PutItemInput, DynamoDBGen.PutItemError, PutItemOutput, Nothing, Nothing]
  def query(tableName: TableName, indexName: Option[IndexName] = None, select: Option[Select] = None, attributesToGet: Option[List[AttributeName]] = None, limit: Option[PositiveIntegerObject] = None, consistentRead: Option[ConsistentRead] = None, keyConditions: Option[Map[AttributeName,Condition]] = None, queryFilter: Option[Map[AttributeName,Condition]] = None, conditionalOperator: Option[ConditionalOperator] = None, scanIndexForward: Option[BooleanObject] = None, exclusiveStartKey: Option[Map[AttributeName,AttributeValue]] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, projectionExpression: Option[ProjectionExpression] = None, filterExpression: Option[ConditionExpression] = None, keyConditionExpression: Option[KeyExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None, expressionAttributeValues: Option[Map[ExpressionAttributeValueVariable,AttributeValue]] = None) : F[QueryInput, DynamoDBGen.QueryError, QueryOutput, Nothing, Nothing]
  def restoreTableFromBackup(targetTableName: TableName, backupArn: BackupArn, billingModeOverride: Option[BillingMode] = None, globalSecondaryIndexOverride: Option[List[GlobalSecondaryIndex]] = None, localSecondaryIndexOverride: Option[List[LocalSecondaryIndex]] = None, provisionedThroughputOverride: Option[ProvisionedThroughput] = None, sSESpecificationOverride: Option[SSESpecification] = None) : F[RestoreTableFromBackupInput, DynamoDBGen.RestoreTableFromBackupError, RestoreTableFromBackupOutput, Nothing, Nothing]
  def restoreTableToPointInTime(targetTableName: TableName, sourceTableArn: Option[TableArn] = None, sourceTableName: Option[TableName] = None, useLatestRestorableTime: Option[BooleanObject] = None, restoreDateTime: Option[Date] = None, billingModeOverride: Option[BillingMode] = None, globalSecondaryIndexOverride: Option[List[GlobalSecondaryIndex]] = None, localSecondaryIndexOverride: Option[List[LocalSecondaryIndex]] = None, provisionedThroughputOverride: Option[ProvisionedThroughput] = None, sSESpecificationOverride: Option[SSESpecification] = None) : F[RestoreTableToPointInTimeInput, DynamoDBGen.RestoreTableToPointInTimeError, RestoreTableToPointInTimeOutput, Nothing, Nothing]
  def scan(tableName: TableName, indexName: Option[IndexName] = None, attributesToGet: Option[List[AttributeName]] = None, limit: Option[PositiveIntegerObject] = None, select: Option[Select] = None, scanFilter: Option[Map[AttributeName,Condition]] = None, conditionalOperator: Option[ConditionalOperator] = None, exclusiveStartKey: Option[Map[AttributeName,AttributeValue]] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, totalSegments: Option[ScanTotalSegments] = None, segment: Option[ScanSegment] = None, projectionExpression: Option[ProjectionExpression] = None, filterExpression: Option[ConditionExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None, expressionAttributeValues: Option[Map[ExpressionAttributeValueVariable,AttributeValue]] = None, consistentRead: Option[ConsistentRead] = None) : F[ScanInput, DynamoDBGen.ScanError, ScanOutput, Nothing, Nothing]
  def tagResource(resourceArn: ResourceArnString, tags: List[Tag]) : F[TagResourceInput, DynamoDBGen.TagResourceError, Unit, Nothing, Nothing]
  def transactGetItems(transactItems: List[TransactGetItem], returnConsumedCapacity: Option[ReturnConsumedCapacity] = None) : F[TransactGetItemsInput, DynamoDBGen.TransactGetItemsError, TransactGetItemsOutput, Nothing, Nothing]
  def transactWriteItems(transactItems: List[TransactWriteItem], returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, returnItemCollectionMetrics: Option[ReturnItemCollectionMetrics] = None, clientRequestToken: Option[ClientRequestToken] = None) : F[TransactWriteItemsInput, DynamoDBGen.TransactWriteItemsError, TransactWriteItemsOutput, Nothing, Nothing]
  def untagResource(resourceArn: ResourceArnString, tagKeys: List[TagKeyString]) : F[UntagResourceInput, DynamoDBGen.UntagResourceError, Unit, Nothing, Nothing]
  def updateContinuousBackups(tableName: TableName, pointInTimeRecoverySpecification: PointInTimeRecoverySpecification) : F[UpdateContinuousBackupsInput, DynamoDBGen.UpdateContinuousBackupsError, UpdateContinuousBackupsOutput, Nothing, Nothing]
  def updateContributorInsights(tableName: TableName, contributorInsightsAction: ContributorInsightsAction, indexName: Option[IndexName] = None) : F[UpdateContributorInsightsInput, DynamoDBGen.UpdateContributorInsightsError, UpdateContributorInsightsOutput, Nothing, Nothing]
  def updateGlobalTable(globalTableName: TableName, replicaUpdates: List[ReplicaUpdate]) : F[UpdateGlobalTableInput, DynamoDBGen.UpdateGlobalTableError, UpdateGlobalTableOutput, Nothing, Nothing]
  def updateGlobalTableSettings(globalTableName: TableName, globalTableBillingMode: Option[BillingMode] = None, globalTableProvisionedWriteCapacityUnits: Option[PositiveLongObject] = None, globalTableProvisionedWriteCapacityAutoScalingSettingsUpdate: Option[AutoScalingSettingsUpdate] = None, globalTableGlobalSecondaryIndexSettingsUpdate: Option[List[GlobalTableGlobalSecondaryIndexSettingsUpdate]] = None, replicaSettingsUpdate: Option[List[ReplicaSettingsUpdate]] = None) : F[UpdateGlobalTableSettingsInput, DynamoDBGen.UpdateGlobalTableSettingsError, UpdateGlobalTableSettingsOutput, Nothing, Nothing]
  def updateItem(tableName: TableName, key: Map[AttributeName,AttributeValue], attributeUpdates: Option[Map[AttributeName,AttributeValueUpdate]] = None, expected: Option[Map[AttributeName,ExpectedAttributeValue]] = None, conditionalOperator: Option[ConditionalOperator] = None, returnValues: Option[ReturnValue] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, returnItemCollectionMetrics: Option[ReturnItemCollectionMetrics] = None, updateExpression: Option[UpdateExpression] = None, conditionExpression: Option[ConditionExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None, expressionAttributeValues: Option[Map[ExpressionAttributeValueVariable,AttributeValue]] = None) : F[UpdateItemInput, DynamoDBGen.UpdateItemError, UpdateItemOutput, Nothing, Nothing]
  def updateTable(tableName: TableName, attributeDefinitions: Option[List[AttributeDefinition]] = None, billingMode: Option[BillingMode] = None, provisionedThroughput: Option[ProvisionedThroughput] = None, globalSecondaryIndexUpdates: Option[List[GlobalSecondaryIndexUpdate]] = None, streamSpecification: Option[StreamSpecification] = None, sSESpecification: Option[SSESpecification] = None, replicaUpdates: Option[List[ReplicationGroupUpdate]] = None, tableClass: Option[TableClass] = None) : F[UpdateTableInput, DynamoDBGen.UpdateTableError, UpdateTableOutput, Nothing, Nothing]
  def updateTableReplicaAutoScaling(tableName: TableName, globalSecondaryIndexUpdates: Option[List[GlobalSecondaryIndexAutoScalingUpdate]] = None, provisionedWriteCapacityAutoScalingUpdate: Option[AutoScalingSettingsUpdate] = None, replicaUpdates: Option[List[ReplicaAutoScalingUpdate]] = None) : F[UpdateTableReplicaAutoScalingInput, DynamoDBGen.UpdateTableReplicaAutoScalingError, UpdateTableReplicaAutoScalingOutput, Nothing, Nothing]
  def updateTimeToLive(tableName: TableName, timeToLiveSpecification: TimeToLiveSpecification) : F[UpdateTimeToLiveInput, DynamoDBGen.UpdateTimeToLiveError, UpdateTimeToLiveOutput, Nothing, Nothing]

  def transform[G[_, _, _, _, _]](transformation : Transformation[F, G]) : DynamoDBGen[G] = new Transformed(transformation)
  class Transformed[G[_, _, _, _, _]](transformation : Transformation[F, G]) extends DynamoDBGen[G] {
    def batchExecuteStatement(statements: List[BatchStatementRequest], returnConsumedCapacity: Option[ReturnConsumedCapacity] = None) = transformation[BatchExecuteStatementInput, DynamoDBGen.BatchExecuteStatementError, BatchExecuteStatementOutput, Nothing, Nothing](self.batchExecuteStatement(statements, returnConsumedCapacity))
    def batchGetItem(requestItems: Map[TableName,KeysAndAttributes], returnConsumedCapacity: Option[ReturnConsumedCapacity] = None) = transformation[BatchGetItemInput, DynamoDBGen.BatchGetItemError, BatchGetItemOutput, Nothing, Nothing](self.batchGetItem(requestItems, returnConsumedCapacity))
    def batchWriteItem(requestItems: Map[TableName,List[WriteRequest]], returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, returnItemCollectionMetrics: Option[ReturnItemCollectionMetrics] = None) = transformation[BatchWriteItemInput, DynamoDBGen.BatchWriteItemError, BatchWriteItemOutput, Nothing, Nothing](self.batchWriteItem(requestItems, returnConsumedCapacity, returnItemCollectionMetrics))
    def createBackup(tableName: TableName, backupName: BackupName) = transformation[CreateBackupInput, DynamoDBGen.CreateBackupError, CreateBackupOutput, Nothing, Nothing](self.createBackup(tableName, backupName))
    def createGlobalTable(globalTableName: TableName, replicationGroup: List[Replica]) = transformation[CreateGlobalTableInput, DynamoDBGen.CreateGlobalTableError, CreateGlobalTableOutput, Nothing, Nothing](self.createGlobalTable(globalTableName, replicationGroup))
    def createTable(attributeDefinitions: List[AttributeDefinition], tableName: TableName, keySchema: List[KeySchemaElement], localSecondaryIndexes: Option[List[LocalSecondaryIndex]] = None, globalSecondaryIndexes: Option[List[GlobalSecondaryIndex]] = None, billingMode: Option[BillingMode] = None, provisionedThroughput: Option[ProvisionedThroughput] = None, streamSpecification: Option[StreamSpecification] = None, sSESpecification: Option[SSESpecification] = None, tags: Option[List[Tag]] = None, tableClass: Option[TableClass] = None) = transformation[CreateTableInput, DynamoDBGen.CreateTableError, CreateTableOutput, Nothing, Nothing](self.createTable(attributeDefinitions, tableName, keySchema, localSecondaryIndexes, globalSecondaryIndexes, billingMode, provisionedThroughput, streamSpecification, sSESpecification, tags, tableClass))
    def deleteBackup(backupArn: BackupArn) = transformation[DeleteBackupInput, DynamoDBGen.DeleteBackupError, DeleteBackupOutput, Nothing, Nothing](self.deleteBackup(backupArn))
    def deleteItem(tableName: TableName, key: Map[AttributeName,AttributeValue], expected: Option[Map[AttributeName,ExpectedAttributeValue]] = None, conditionalOperator: Option[ConditionalOperator] = None, returnValues: Option[ReturnValue] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, returnItemCollectionMetrics: Option[ReturnItemCollectionMetrics] = None, conditionExpression: Option[ConditionExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None, expressionAttributeValues: Option[Map[ExpressionAttributeValueVariable,AttributeValue]] = None) = transformation[DeleteItemInput, DynamoDBGen.DeleteItemError, DeleteItemOutput, Nothing, Nothing](self.deleteItem(tableName, key, expected, conditionalOperator, returnValues, returnConsumedCapacity, returnItemCollectionMetrics, conditionExpression, expressionAttributeNames, expressionAttributeValues))
    def deleteTable(tableName: TableName) = transformation[DeleteTableInput, DynamoDBGen.DeleteTableError, DeleteTableOutput, Nothing, Nothing](self.deleteTable(tableName))
    def describeBackup(backupArn: BackupArn) = transformation[DescribeBackupInput, DynamoDBGen.DescribeBackupError, DescribeBackupOutput, Nothing, Nothing](self.describeBackup(backupArn))
    def describeContinuousBackups(tableName: TableName) = transformation[DescribeContinuousBackupsInput, DynamoDBGen.DescribeContinuousBackupsError, DescribeContinuousBackupsOutput, Nothing, Nothing](self.describeContinuousBackups(tableName))
    def describeContributorInsights(tableName: TableName, indexName: Option[IndexName] = None) = transformation[DescribeContributorInsightsInput, DynamoDBGen.DescribeContributorInsightsError, DescribeContributorInsightsOutput, Nothing, Nothing](self.describeContributorInsights(tableName, indexName))
    def describeEndpoints() = transformation[DescribeEndpointsRequest, Nothing, DescribeEndpointsResponse, Nothing, Nothing](self.describeEndpoints())
    def describeExport(exportArn: ExportArn) = transformation[DescribeExportInput, DynamoDBGen.DescribeExportError, DescribeExportOutput, Nothing, Nothing](self.describeExport(exportArn))
    def describeGlobalTable(globalTableName: TableName) = transformation[DescribeGlobalTableInput, DynamoDBGen.DescribeGlobalTableError, DescribeGlobalTableOutput, Nothing, Nothing](self.describeGlobalTable(globalTableName))
    def describeGlobalTableSettings(globalTableName: TableName) = transformation[DescribeGlobalTableSettingsInput, DynamoDBGen.DescribeGlobalTableSettingsError, DescribeGlobalTableSettingsOutput, Nothing, Nothing](self.describeGlobalTableSettings(globalTableName))
    def describeKinesisStreamingDestination(tableName: TableName) = transformation[DescribeKinesisStreamingDestinationInput, DynamoDBGen.DescribeKinesisStreamingDestinationError, DescribeKinesisStreamingDestinationOutput, Nothing, Nothing](self.describeKinesisStreamingDestination(tableName))
    def describeLimits() = transformation[DescribeLimitsInput, DynamoDBGen.DescribeLimitsError, DescribeLimitsOutput, Nothing, Nothing](self.describeLimits())
    def describeTable(tableName: TableName) = transformation[DescribeTableInput, DynamoDBGen.DescribeTableError, DescribeTableOutput, Nothing, Nothing](self.describeTable(tableName))
    def describeTableReplicaAutoScaling(tableName: TableName) = transformation[DescribeTableReplicaAutoScalingInput, DynamoDBGen.DescribeTableReplicaAutoScalingError, DescribeTableReplicaAutoScalingOutput, Nothing, Nothing](self.describeTableReplicaAutoScaling(tableName))
    def describeTimeToLive(tableName: TableName) = transformation[DescribeTimeToLiveInput, DynamoDBGen.DescribeTimeToLiveError, DescribeTimeToLiveOutput, Nothing, Nothing](self.describeTimeToLive(tableName))
    def disableKinesisStreamingDestination(tableName: TableName, streamArn: StreamArn) = transformation[KinesisStreamingDestinationInput, DynamoDBGen.DisableKinesisStreamingDestinationError, KinesisStreamingDestinationOutput, Nothing, Nothing](self.disableKinesisStreamingDestination(tableName, streamArn))
    def enableKinesisStreamingDestination(tableName: TableName, streamArn: StreamArn) = transformation[KinesisStreamingDestinationInput, DynamoDBGen.EnableKinesisStreamingDestinationError, KinesisStreamingDestinationOutput, Nothing, Nothing](self.enableKinesisStreamingDestination(tableName, streamArn))
    def executeStatement(statement: PartiQLStatement, parameters: Option[List[AttributeValue]] = None, consistentRead: Option[ConsistentRead] = None, nextToken: Option[PartiQLNextToken] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, limit: Option[PositiveIntegerObject] = None) = transformation[ExecuteStatementInput, DynamoDBGen.ExecuteStatementError, ExecuteStatementOutput, Nothing, Nothing](self.executeStatement(statement, parameters, consistentRead, nextToken, returnConsumedCapacity, limit))
    def executeTransaction(transactStatements: List[ParameterizedStatement], clientRequestToken: Option[ClientRequestToken] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None) = transformation[ExecuteTransactionInput, DynamoDBGen.ExecuteTransactionError, ExecuteTransactionOutput, Nothing, Nothing](self.executeTransaction(transactStatements, clientRequestToken, returnConsumedCapacity))
    def exportTableToPointInTime(tableArn: TableArn, s3Bucket: S3Bucket, exportTime: Option[ExportTime] = None, clientToken: Option[ClientToken] = None, s3BucketOwner: Option[S3BucketOwner] = None, s3Prefix: Option[S3Prefix] = None, s3SseAlgorithm: Option[S3SseAlgorithm] = None, s3SseKmsKeyId: Option[S3SseKmsKeyId] = None, exportFormat: Option[ExportFormat] = None) = transformation[ExportTableToPointInTimeInput, DynamoDBGen.ExportTableToPointInTimeError, ExportTableToPointInTimeOutput, Nothing, Nothing](self.exportTableToPointInTime(tableArn, s3Bucket, exportTime, clientToken, s3BucketOwner, s3Prefix, s3SseAlgorithm, s3SseKmsKeyId, exportFormat))
    def getItem(tableName: TableName, key: Map[AttributeName,AttributeValue], attributesToGet: Option[List[AttributeName]] = None, consistentRead: Option[ConsistentRead] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, projectionExpression: Option[ProjectionExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None) = transformation[GetItemInput, DynamoDBGen.GetItemError, GetItemOutput, Nothing, Nothing](self.getItem(tableName, key, attributesToGet, consistentRead, returnConsumedCapacity, projectionExpression, expressionAttributeNames))
    def listBackups(tableName: Option[TableName] = None, limit: Option[BackupsInputLimit] = None, timeRangeLowerBound: Option[TimeRangeLowerBound] = None, timeRangeUpperBound: Option[TimeRangeUpperBound] = None, exclusiveStartBackupArn: Option[BackupArn] = None, backupType: Option[BackupTypeFilter] = None) = transformation[ListBackupsInput, DynamoDBGen.ListBackupsError, ListBackupsOutput, Nothing, Nothing](self.listBackups(tableName, limit, timeRangeLowerBound, timeRangeUpperBound, exclusiveStartBackupArn, backupType))
    def listContributorInsights(maxResults: ListContributorInsightsLimit = com.amazonaws.dynamodb.ListContributorInsightsLimit(0), tableName: Option[TableName] = None, nextToken: Option[NextTokenString] = None) = transformation[ListContributorInsightsInput, DynamoDBGen.ListContributorInsightsError, ListContributorInsightsOutput, Nothing, Nothing](self.listContributorInsights(maxResults, tableName, nextToken))
    def listExports(tableArn: Option[TableArn] = None, maxResults: Option[ListExportsMaxLimit] = None, nextToken: Option[ExportNextToken] = None) = transformation[ListExportsInput, DynamoDBGen.ListExportsError, ListExportsOutput, Nothing, Nothing](self.listExports(tableArn, maxResults, nextToken))
    def listGlobalTables(exclusiveStartGlobalTableName: Option[TableName] = None, limit: Option[PositiveIntegerObject] = None, regionName: Option[RegionName] = None) = transformation[ListGlobalTablesInput, DynamoDBGen.ListGlobalTablesError, ListGlobalTablesOutput, Nothing, Nothing](self.listGlobalTables(exclusiveStartGlobalTableName, limit, regionName))
    def listTables(exclusiveStartTableName: Option[TableName] = None, limit: Option[ListTablesInputLimit] = None) = transformation[ListTablesInput, DynamoDBGen.ListTablesError, ListTablesOutput, Nothing, Nothing](self.listTables(exclusiveStartTableName, limit))
    def listTagsOfResource(resourceArn: ResourceArnString, nextToken: Option[NextTokenString] = None) = transformation[ListTagsOfResourceInput, DynamoDBGen.ListTagsOfResourceError, ListTagsOfResourceOutput, Nothing, Nothing](self.listTagsOfResource(resourceArn, nextToken))
    def putItem(tableName: TableName, item: Map[AttributeName,AttributeValue], expected: Option[Map[AttributeName,ExpectedAttributeValue]] = None, returnValues: Option[ReturnValue] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, returnItemCollectionMetrics: Option[ReturnItemCollectionMetrics] = None, conditionalOperator: Option[ConditionalOperator] = None, conditionExpression: Option[ConditionExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None, expressionAttributeValues: Option[Map[ExpressionAttributeValueVariable,AttributeValue]] = None) = transformation[PutItemInput, DynamoDBGen.PutItemError, PutItemOutput, Nothing, Nothing](self.putItem(tableName, item, expected, returnValues, returnConsumedCapacity, returnItemCollectionMetrics, conditionalOperator, conditionExpression, expressionAttributeNames, expressionAttributeValues))
    def query(tableName: TableName, indexName: Option[IndexName] = None, select: Option[Select] = None, attributesToGet: Option[List[AttributeName]] = None, limit: Option[PositiveIntegerObject] = None, consistentRead: Option[ConsistentRead] = None, keyConditions: Option[Map[AttributeName,Condition]] = None, queryFilter: Option[Map[AttributeName,Condition]] = None, conditionalOperator: Option[ConditionalOperator] = None, scanIndexForward: Option[BooleanObject] = None, exclusiveStartKey: Option[Map[AttributeName,AttributeValue]] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, projectionExpression: Option[ProjectionExpression] = None, filterExpression: Option[ConditionExpression] = None, keyConditionExpression: Option[KeyExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None, expressionAttributeValues: Option[Map[ExpressionAttributeValueVariable,AttributeValue]] = None) = transformation[QueryInput, DynamoDBGen.QueryError, QueryOutput, Nothing, Nothing](self.query(tableName, indexName, select, attributesToGet, limit, consistentRead, keyConditions, queryFilter, conditionalOperator, scanIndexForward, exclusiveStartKey, returnConsumedCapacity, projectionExpression, filterExpression, keyConditionExpression, expressionAttributeNames, expressionAttributeValues))
    def restoreTableFromBackup(targetTableName: TableName, backupArn: BackupArn, billingModeOverride: Option[BillingMode] = None, globalSecondaryIndexOverride: Option[List[GlobalSecondaryIndex]] = None, localSecondaryIndexOverride: Option[List[LocalSecondaryIndex]] = None, provisionedThroughputOverride: Option[ProvisionedThroughput] = None, sSESpecificationOverride: Option[SSESpecification] = None) = transformation[RestoreTableFromBackupInput, DynamoDBGen.RestoreTableFromBackupError, RestoreTableFromBackupOutput, Nothing, Nothing](self.restoreTableFromBackup(targetTableName, backupArn, billingModeOverride, globalSecondaryIndexOverride, localSecondaryIndexOverride, provisionedThroughputOverride, sSESpecificationOverride))
    def restoreTableToPointInTime(targetTableName: TableName, sourceTableArn: Option[TableArn] = None, sourceTableName: Option[TableName] = None, useLatestRestorableTime: Option[BooleanObject] = None, restoreDateTime: Option[Date] = None, billingModeOverride: Option[BillingMode] = None, globalSecondaryIndexOverride: Option[List[GlobalSecondaryIndex]] = None, localSecondaryIndexOverride: Option[List[LocalSecondaryIndex]] = None, provisionedThroughputOverride: Option[ProvisionedThroughput] = None, sSESpecificationOverride: Option[SSESpecification] = None) = transformation[RestoreTableToPointInTimeInput, DynamoDBGen.RestoreTableToPointInTimeError, RestoreTableToPointInTimeOutput, Nothing, Nothing](self.restoreTableToPointInTime(targetTableName, sourceTableArn, sourceTableName, useLatestRestorableTime, restoreDateTime, billingModeOverride, globalSecondaryIndexOverride, localSecondaryIndexOverride, provisionedThroughputOverride, sSESpecificationOverride))
    def scan(tableName: TableName, indexName: Option[IndexName] = None, attributesToGet: Option[List[AttributeName]] = None, limit: Option[PositiveIntegerObject] = None, select: Option[Select] = None, scanFilter: Option[Map[AttributeName,Condition]] = None, conditionalOperator: Option[ConditionalOperator] = None, exclusiveStartKey: Option[Map[AttributeName,AttributeValue]] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, totalSegments: Option[ScanTotalSegments] = None, segment: Option[ScanSegment] = None, projectionExpression: Option[ProjectionExpression] = None, filterExpression: Option[ConditionExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None, expressionAttributeValues: Option[Map[ExpressionAttributeValueVariable,AttributeValue]] = None, consistentRead: Option[ConsistentRead] = None) = transformation[ScanInput, DynamoDBGen.ScanError, ScanOutput, Nothing, Nothing](self.scan(tableName, indexName, attributesToGet, limit, select, scanFilter, conditionalOperator, exclusiveStartKey, returnConsumedCapacity, totalSegments, segment, projectionExpression, filterExpression, expressionAttributeNames, expressionAttributeValues, consistentRead))
    def tagResource(resourceArn: ResourceArnString, tags: List[Tag]) = transformation[TagResourceInput, DynamoDBGen.TagResourceError, Unit, Nothing, Nothing](self.tagResource(resourceArn, tags))
    def transactGetItems(transactItems: List[TransactGetItem], returnConsumedCapacity: Option[ReturnConsumedCapacity] = None) = transformation[TransactGetItemsInput, DynamoDBGen.TransactGetItemsError, TransactGetItemsOutput, Nothing, Nothing](self.transactGetItems(transactItems, returnConsumedCapacity))
    def transactWriteItems(transactItems: List[TransactWriteItem], returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, returnItemCollectionMetrics: Option[ReturnItemCollectionMetrics] = None, clientRequestToken: Option[ClientRequestToken] = None) = transformation[TransactWriteItemsInput, DynamoDBGen.TransactWriteItemsError, TransactWriteItemsOutput, Nothing, Nothing](self.transactWriteItems(transactItems, returnConsumedCapacity, returnItemCollectionMetrics, clientRequestToken))
    def untagResource(resourceArn: ResourceArnString, tagKeys: List[TagKeyString]) = transformation[UntagResourceInput, DynamoDBGen.UntagResourceError, Unit, Nothing, Nothing](self.untagResource(resourceArn, tagKeys))
    def updateContinuousBackups(tableName: TableName, pointInTimeRecoverySpecification: PointInTimeRecoverySpecification) = transformation[UpdateContinuousBackupsInput, DynamoDBGen.UpdateContinuousBackupsError, UpdateContinuousBackupsOutput, Nothing, Nothing](self.updateContinuousBackups(tableName, pointInTimeRecoverySpecification))
    def updateContributorInsights(tableName: TableName, contributorInsightsAction: ContributorInsightsAction, indexName: Option[IndexName] = None) = transformation[UpdateContributorInsightsInput, DynamoDBGen.UpdateContributorInsightsError, UpdateContributorInsightsOutput, Nothing, Nothing](self.updateContributorInsights(tableName, contributorInsightsAction, indexName))
    def updateGlobalTable(globalTableName: TableName, replicaUpdates: List[ReplicaUpdate]) = transformation[UpdateGlobalTableInput, DynamoDBGen.UpdateGlobalTableError, UpdateGlobalTableOutput, Nothing, Nothing](self.updateGlobalTable(globalTableName, replicaUpdates))
    def updateGlobalTableSettings(globalTableName: TableName, globalTableBillingMode: Option[BillingMode] = None, globalTableProvisionedWriteCapacityUnits: Option[PositiveLongObject] = None, globalTableProvisionedWriteCapacityAutoScalingSettingsUpdate: Option[AutoScalingSettingsUpdate] = None, globalTableGlobalSecondaryIndexSettingsUpdate: Option[List[GlobalTableGlobalSecondaryIndexSettingsUpdate]] = None, replicaSettingsUpdate: Option[List[ReplicaSettingsUpdate]] = None) = transformation[UpdateGlobalTableSettingsInput, DynamoDBGen.UpdateGlobalTableSettingsError, UpdateGlobalTableSettingsOutput, Nothing, Nothing](self.updateGlobalTableSettings(globalTableName, globalTableBillingMode, globalTableProvisionedWriteCapacityUnits, globalTableProvisionedWriteCapacityAutoScalingSettingsUpdate, globalTableGlobalSecondaryIndexSettingsUpdate, replicaSettingsUpdate))
    def updateItem(tableName: TableName, key: Map[AttributeName,AttributeValue], attributeUpdates: Option[Map[AttributeName,AttributeValueUpdate]] = None, expected: Option[Map[AttributeName,ExpectedAttributeValue]] = None, conditionalOperator: Option[ConditionalOperator] = None, returnValues: Option[ReturnValue] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, returnItemCollectionMetrics: Option[ReturnItemCollectionMetrics] = None, updateExpression: Option[UpdateExpression] = None, conditionExpression: Option[ConditionExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None, expressionAttributeValues: Option[Map[ExpressionAttributeValueVariable,AttributeValue]] = None) = transformation[UpdateItemInput, DynamoDBGen.UpdateItemError, UpdateItemOutput, Nothing, Nothing](self.updateItem(tableName, key, attributeUpdates, expected, conditionalOperator, returnValues, returnConsumedCapacity, returnItemCollectionMetrics, updateExpression, conditionExpression, expressionAttributeNames, expressionAttributeValues))
    def updateTable(tableName: TableName, attributeDefinitions: Option[List[AttributeDefinition]] = None, billingMode: Option[BillingMode] = None, provisionedThroughput: Option[ProvisionedThroughput] = None, globalSecondaryIndexUpdates: Option[List[GlobalSecondaryIndexUpdate]] = None, streamSpecification: Option[StreamSpecification] = None, sSESpecification: Option[SSESpecification] = None, replicaUpdates: Option[List[ReplicationGroupUpdate]] = None, tableClass: Option[TableClass] = None) = transformation[UpdateTableInput, DynamoDBGen.UpdateTableError, UpdateTableOutput, Nothing, Nothing](self.updateTable(tableName, attributeDefinitions, billingMode, provisionedThroughput, globalSecondaryIndexUpdates, streamSpecification, sSESpecification, replicaUpdates, tableClass))
    def updateTableReplicaAutoScaling(tableName: TableName, globalSecondaryIndexUpdates: Option[List[GlobalSecondaryIndexAutoScalingUpdate]] = None, provisionedWriteCapacityAutoScalingUpdate: Option[AutoScalingSettingsUpdate] = None, replicaUpdates: Option[List[ReplicaAutoScalingUpdate]] = None) = transformation[UpdateTableReplicaAutoScalingInput, DynamoDBGen.UpdateTableReplicaAutoScalingError, UpdateTableReplicaAutoScalingOutput, Nothing, Nothing](self.updateTableReplicaAutoScaling(tableName, globalSecondaryIndexUpdates, provisionedWriteCapacityAutoScalingUpdate, replicaUpdates))
    def updateTimeToLive(tableName: TableName, timeToLiveSpecification: TimeToLiveSpecification) = transformation[UpdateTimeToLiveInput, DynamoDBGen.UpdateTimeToLiveError, UpdateTimeToLiveOutput, Nothing, Nothing](self.updateTimeToLive(tableName, timeToLiveSpecification))
  }
}

object DynamoDBGen extends Service[DynamoDBGen, DynamoDBOperation] {

  def apply[F[_]](implicit F: Monadic[DynamoDBGen, F]): F.type = F

  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DynamoDB_20120810")

  val hints : Hints = Hints(
    aws.auth.Sigv4(name = "dynamodb"),
    smithy.api.Title("Amazon DynamoDB"),
    aws.protocols.AwsJson1_0(http = None, eventStreamHttp = None),
    smithy.api.Documentation("<fullname>Amazon DynamoDB</fullname>\n\n        <p>Amazon DynamoDB is a fully managed NoSQL database service that provides fast\n            and predictable performance with seamless scalability. DynamoDB lets you\n            offload the administrative burdens of operating and scaling a distributed database, so\n            that you don\'t have to worry about hardware provisioning, setup and configuration,\n            replication, software patching, or cluster scaling.</p>\n\n        <p>With DynamoDB, you can create database tables that can store and retrieve\n            any amount of data, and serve any level of request traffic. You can scale up or scale\n            down your tables\' throughput capacity without downtime or performance degradation, and\n            use the Amazon Web Services Management Console to monitor resource utilization and performance\n            metrics.</p>\n\n        <p>DynamoDB automatically spreads the data and traffic for your tables over\n            a sufficient number of servers to handle your throughput and storage requirements, while\n            maintaining consistent and fast performance. All of your data is stored on solid state\n            disks (SSDs) and automatically replicated across multiple Availability Zones in an\n                Amazon Web Services Region, providing built-in high availability and data\n            durability.</p>"),
    aws.api.Service(sdkId = "DynamoDB", arnNamespace = Some(aws.api.ArnNamespace("dynamodb")), cloudFormationName = Some(aws.api.CloudFormationName("DynamoDB")), cloudTrailEventSource = Some("dynamodb.amazonaws.com"), endpointPrefix = Some("dynamodb")),
    smithy.api.XmlNamespace(uri = smithy.api.NonEmptyString("http://dynamodb.amazonaws.com/doc/2012-08-10/"), prefix = None),
    aws.api.ClientEndpointDiscovery(operation = "com.amazonaws.dynamodb#DescribeEndpoints", error = Some("com.amazonaws.dynamodb#InvalidEndpointException")),
  )

  val endpoints: List[Endpoint[DynamoDBOperation, _, _, _, _, _]] = List(
    BatchExecuteStatement,
    BatchGetItem,
    BatchWriteItem,
    CreateBackup,
    CreateGlobalTable,
    CreateTable,
    DeleteBackup,
    DeleteItem,
    DeleteTable,
    DescribeBackup,
    DescribeContinuousBackups,
    DescribeContributorInsights,
    DescribeEndpoints,
    DescribeExport,
    DescribeGlobalTable,
    DescribeGlobalTableSettings,
    DescribeKinesisStreamingDestination,
    DescribeLimits,
    DescribeTable,
    DescribeTableReplicaAutoScaling,
    DescribeTimeToLive,
    DisableKinesisStreamingDestination,
    EnableKinesisStreamingDestination,
    ExecuteStatement,
    ExecuteTransaction,
    ExportTableToPointInTime,
    GetItem,
    ListBackups,
    ListContributorInsights,
    ListExports,
    ListGlobalTables,
    ListTables,
    ListTagsOfResource,
    PutItem,
    Query,
    RestoreTableFromBackup,
    RestoreTableToPointInTime,
    Scan,
    TagResource,
    TransactGetItems,
    TransactWriteItems,
    UntagResource,
    UpdateContinuousBackups,
    UpdateContributorInsights,
    UpdateGlobalTable,
    UpdateGlobalTableSettings,
    UpdateItem,
    UpdateTable,
    UpdateTableReplicaAutoScaling,
    UpdateTimeToLive,
  )

  val version: String = "2012-08-10"

  def endpoint[I, E, O, SI, SO](op : DynamoDBOperation[I, E, O, SI, SO]) = op match {
    case BatchExecuteStatement(input) => (input, BatchExecuteStatement)
    case BatchGetItem(input) => (input, BatchGetItem)
    case BatchWriteItem(input) => (input, BatchWriteItem)
    case CreateBackup(input) => (input, CreateBackup)
    case CreateGlobalTable(input) => (input, CreateGlobalTable)
    case CreateTable(input) => (input, CreateTable)
    case DeleteBackup(input) => (input, DeleteBackup)
    case DeleteItem(input) => (input, DeleteItem)
    case DeleteTable(input) => (input, DeleteTable)
    case DescribeBackup(input) => (input, DescribeBackup)
    case DescribeContinuousBackups(input) => (input, DescribeContinuousBackups)
    case DescribeContributorInsights(input) => (input, DescribeContributorInsights)
    case DescribeEndpoints(input) => (input, DescribeEndpoints)
    case DescribeExport(input) => (input, DescribeExport)
    case DescribeGlobalTable(input) => (input, DescribeGlobalTable)
    case DescribeGlobalTableSettings(input) => (input, DescribeGlobalTableSettings)
    case DescribeKinesisStreamingDestination(input) => (input, DescribeKinesisStreamingDestination)
    case DescribeLimits(input) => (input, DescribeLimits)
    case DescribeTable(input) => (input, DescribeTable)
    case DescribeTableReplicaAutoScaling(input) => (input, DescribeTableReplicaAutoScaling)
    case DescribeTimeToLive(input) => (input, DescribeTimeToLive)
    case DisableKinesisStreamingDestination(input) => (input, DisableKinesisStreamingDestination)
    case EnableKinesisStreamingDestination(input) => (input, EnableKinesisStreamingDestination)
    case ExecuteStatement(input) => (input, ExecuteStatement)
    case ExecuteTransaction(input) => (input, ExecuteTransaction)
    case ExportTableToPointInTime(input) => (input, ExportTableToPointInTime)
    case GetItem(input) => (input, GetItem)
    case ListBackups(input) => (input, ListBackups)
    case ListContributorInsights(input) => (input, ListContributorInsights)
    case ListExports(input) => (input, ListExports)
    case ListGlobalTables(input) => (input, ListGlobalTables)
    case ListTables(input) => (input, ListTables)
    case ListTagsOfResource(input) => (input, ListTagsOfResource)
    case PutItem(input) => (input, PutItem)
    case Query(input) => (input, Query)
    case RestoreTableFromBackup(input) => (input, RestoreTableFromBackup)
    case RestoreTableToPointInTime(input) => (input, RestoreTableToPointInTime)
    case Scan(input) => (input, Scan)
    case TagResource(input) => (input, TagResource)
    case TransactGetItems(input) => (input, TransactGetItems)
    case TransactWriteItems(input) => (input, TransactWriteItems)
    case UntagResource(input) => (input, UntagResource)
    case UpdateContinuousBackups(input) => (input, UpdateContinuousBackups)
    case UpdateContributorInsights(input) => (input, UpdateContributorInsights)
    case UpdateGlobalTable(input) => (input, UpdateGlobalTable)
    case UpdateGlobalTableSettings(input) => (input, UpdateGlobalTableSettings)
    case UpdateItem(input) => (input, UpdateItem)
    case UpdateTable(input) => (input, UpdateTable)
    case UpdateTableReplicaAutoScaling(input) => (input, UpdateTableReplicaAutoScaling)
    case UpdateTimeToLive(input) => (input, UpdateTimeToLive)
  }

  object reified extends DynamoDBGen[DynamoDBOperation] {
    def batchExecuteStatement(statements: List[BatchStatementRequest], returnConsumedCapacity: Option[ReturnConsumedCapacity] = None) = BatchExecuteStatement(BatchExecuteStatementInput(statements, returnConsumedCapacity))
    def batchGetItem(requestItems: Map[TableName,KeysAndAttributes], returnConsumedCapacity: Option[ReturnConsumedCapacity] = None) = BatchGetItem(BatchGetItemInput(requestItems, returnConsumedCapacity))
    def batchWriteItem(requestItems: Map[TableName,List[WriteRequest]], returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, returnItemCollectionMetrics: Option[ReturnItemCollectionMetrics] = None) = BatchWriteItem(BatchWriteItemInput(requestItems, returnConsumedCapacity, returnItemCollectionMetrics))
    def createBackup(tableName: TableName, backupName: BackupName) = CreateBackup(CreateBackupInput(tableName, backupName))
    def createGlobalTable(globalTableName: TableName, replicationGroup: List[Replica]) = CreateGlobalTable(CreateGlobalTableInput(globalTableName, replicationGroup))
    def createTable(attributeDefinitions: List[AttributeDefinition], tableName: TableName, keySchema: List[KeySchemaElement], localSecondaryIndexes: Option[List[LocalSecondaryIndex]] = None, globalSecondaryIndexes: Option[List[GlobalSecondaryIndex]] = None, billingMode: Option[BillingMode] = None, provisionedThroughput: Option[ProvisionedThroughput] = None, streamSpecification: Option[StreamSpecification] = None, sSESpecification: Option[SSESpecification] = None, tags: Option[List[Tag]] = None, tableClass: Option[TableClass] = None) = CreateTable(CreateTableInput(attributeDefinitions, tableName, keySchema, localSecondaryIndexes, globalSecondaryIndexes, billingMode, provisionedThroughput, streamSpecification, sSESpecification, tags, tableClass))
    def deleteBackup(backupArn: BackupArn) = DeleteBackup(DeleteBackupInput(backupArn))
    def deleteItem(tableName: TableName, key: Map[AttributeName,AttributeValue], expected: Option[Map[AttributeName,ExpectedAttributeValue]] = None, conditionalOperator: Option[ConditionalOperator] = None, returnValues: Option[ReturnValue] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, returnItemCollectionMetrics: Option[ReturnItemCollectionMetrics] = None, conditionExpression: Option[ConditionExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None, expressionAttributeValues: Option[Map[ExpressionAttributeValueVariable,AttributeValue]] = None) = DeleteItem(DeleteItemInput(tableName, key, expected, conditionalOperator, returnValues, returnConsumedCapacity, returnItemCollectionMetrics, conditionExpression, expressionAttributeNames, expressionAttributeValues))
    def deleteTable(tableName: TableName) = DeleteTable(DeleteTableInput(tableName))
    def describeBackup(backupArn: BackupArn) = DescribeBackup(DescribeBackupInput(backupArn))
    def describeContinuousBackups(tableName: TableName) = DescribeContinuousBackups(DescribeContinuousBackupsInput(tableName))
    def describeContributorInsights(tableName: TableName, indexName: Option[IndexName] = None) = DescribeContributorInsights(DescribeContributorInsightsInput(tableName, indexName))
    def describeEndpoints() = DescribeEndpoints(DescribeEndpointsRequest())
    def describeExport(exportArn: ExportArn) = DescribeExport(DescribeExportInput(exportArn))
    def describeGlobalTable(globalTableName: TableName) = DescribeGlobalTable(DescribeGlobalTableInput(globalTableName))
    def describeGlobalTableSettings(globalTableName: TableName) = DescribeGlobalTableSettings(DescribeGlobalTableSettingsInput(globalTableName))
    def describeKinesisStreamingDestination(tableName: TableName) = DescribeKinesisStreamingDestination(DescribeKinesisStreamingDestinationInput(tableName))
    def describeLimits() = DescribeLimits(DescribeLimitsInput())
    def describeTable(tableName: TableName) = DescribeTable(DescribeTableInput(tableName))
    def describeTableReplicaAutoScaling(tableName: TableName) = DescribeTableReplicaAutoScaling(DescribeTableReplicaAutoScalingInput(tableName))
    def describeTimeToLive(tableName: TableName) = DescribeTimeToLive(DescribeTimeToLiveInput(tableName))
    def disableKinesisStreamingDestination(tableName: TableName, streamArn: StreamArn) = DisableKinesisStreamingDestination(KinesisStreamingDestinationInput(tableName, streamArn))
    def enableKinesisStreamingDestination(tableName: TableName, streamArn: StreamArn) = EnableKinesisStreamingDestination(KinesisStreamingDestinationInput(tableName, streamArn))
    def executeStatement(statement: PartiQLStatement, parameters: Option[List[AttributeValue]] = None, consistentRead: Option[ConsistentRead] = None, nextToken: Option[PartiQLNextToken] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, limit: Option[PositiveIntegerObject] = None) = ExecuteStatement(ExecuteStatementInput(statement, parameters, consistentRead, nextToken, returnConsumedCapacity, limit))
    def executeTransaction(transactStatements: List[ParameterizedStatement], clientRequestToken: Option[ClientRequestToken] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None) = ExecuteTransaction(ExecuteTransactionInput(transactStatements, clientRequestToken, returnConsumedCapacity))
    def exportTableToPointInTime(tableArn: TableArn, s3Bucket: S3Bucket, exportTime: Option[ExportTime] = None, clientToken: Option[ClientToken] = None, s3BucketOwner: Option[S3BucketOwner] = None, s3Prefix: Option[S3Prefix] = None, s3SseAlgorithm: Option[S3SseAlgorithm] = None, s3SseKmsKeyId: Option[S3SseKmsKeyId] = None, exportFormat: Option[ExportFormat] = None) = ExportTableToPointInTime(ExportTableToPointInTimeInput(tableArn, s3Bucket, exportTime, clientToken, s3BucketOwner, s3Prefix, s3SseAlgorithm, s3SseKmsKeyId, exportFormat))
    def getItem(tableName: TableName, key: Map[AttributeName,AttributeValue], attributesToGet: Option[List[AttributeName]] = None, consistentRead: Option[ConsistentRead] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, projectionExpression: Option[ProjectionExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None) = GetItem(GetItemInput(tableName, key, attributesToGet, consistentRead, returnConsumedCapacity, projectionExpression, expressionAttributeNames))
    def listBackups(tableName: Option[TableName] = None, limit: Option[BackupsInputLimit] = None, timeRangeLowerBound: Option[TimeRangeLowerBound] = None, timeRangeUpperBound: Option[TimeRangeUpperBound] = None, exclusiveStartBackupArn: Option[BackupArn] = None, backupType: Option[BackupTypeFilter] = None) = ListBackups(ListBackupsInput(tableName, limit, timeRangeLowerBound, timeRangeUpperBound, exclusiveStartBackupArn, backupType))
    def listContributorInsights(maxResults: ListContributorInsightsLimit = com.amazonaws.dynamodb.ListContributorInsightsLimit(0), tableName: Option[TableName] = None, nextToken: Option[NextTokenString] = None) = ListContributorInsights(ListContributorInsightsInput(maxResults, tableName, nextToken))
    def listExports(tableArn: Option[TableArn] = None, maxResults: Option[ListExportsMaxLimit] = None, nextToken: Option[ExportNextToken] = None) = ListExports(ListExportsInput(tableArn, maxResults, nextToken))
    def listGlobalTables(exclusiveStartGlobalTableName: Option[TableName] = None, limit: Option[PositiveIntegerObject] = None, regionName: Option[RegionName] = None) = ListGlobalTables(ListGlobalTablesInput(exclusiveStartGlobalTableName, limit, regionName))
    def listTables(exclusiveStartTableName: Option[TableName] = None, limit: Option[ListTablesInputLimit] = None) = ListTables(ListTablesInput(exclusiveStartTableName, limit))
    def listTagsOfResource(resourceArn: ResourceArnString, nextToken: Option[NextTokenString] = None) = ListTagsOfResource(ListTagsOfResourceInput(resourceArn, nextToken))
    def putItem(tableName: TableName, item: Map[AttributeName,AttributeValue], expected: Option[Map[AttributeName,ExpectedAttributeValue]] = None, returnValues: Option[ReturnValue] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, returnItemCollectionMetrics: Option[ReturnItemCollectionMetrics] = None, conditionalOperator: Option[ConditionalOperator] = None, conditionExpression: Option[ConditionExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None, expressionAttributeValues: Option[Map[ExpressionAttributeValueVariable,AttributeValue]] = None) = PutItem(PutItemInput(tableName, item, expected, returnValues, returnConsumedCapacity, returnItemCollectionMetrics, conditionalOperator, conditionExpression, expressionAttributeNames, expressionAttributeValues))
    def query(tableName: TableName, indexName: Option[IndexName] = None, select: Option[Select] = None, attributesToGet: Option[List[AttributeName]] = None, limit: Option[PositiveIntegerObject] = None, consistentRead: Option[ConsistentRead] = None, keyConditions: Option[Map[AttributeName,Condition]] = None, queryFilter: Option[Map[AttributeName,Condition]] = None, conditionalOperator: Option[ConditionalOperator] = None, scanIndexForward: Option[BooleanObject] = None, exclusiveStartKey: Option[Map[AttributeName,AttributeValue]] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, projectionExpression: Option[ProjectionExpression] = None, filterExpression: Option[ConditionExpression] = None, keyConditionExpression: Option[KeyExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None, expressionAttributeValues: Option[Map[ExpressionAttributeValueVariable,AttributeValue]] = None) = Query(QueryInput(tableName, indexName, select, attributesToGet, limit, consistentRead, keyConditions, queryFilter, conditionalOperator, scanIndexForward, exclusiveStartKey, returnConsumedCapacity, projectionExpression, filterExpression, keyConditionExpression, expressionAttributeNames, expressionAttributeValues))
    def restoreTableFromBackup(targetTableName: TableName, backupArn: BackupArn, billingModeOverride: Option[BillingMode] = None, globalSecondaryIndexOverride: Option[List[GlobalSecondaryIndex]] = None, localSecondaryIndexOverride: Option[List[LocalSecondaryIndex]] = None, provisionedThroughputOverride: Option[ProvisionedThroughput] = None, sSESpecificationOverride: Option[SSESpecification] = None) = RestoreTableFromBackup(RestoreTableFromBackupInput(targetTableName, backupArn, billingModeOverride, globalSecondaryIndexOverride, localSecondaryIndexOverride, provisionedThroughputOverride, sSESpecificationOverride))
    def restoreTableToPointInTime(targetTableName: TableName, sourceTableArn: Option[TableArn] = None, sourceTableName: Option[TableName] = None, useLatestRestorableTime: Option[BooleanObject] = None, restoreDateTime: Option[Date] = None, billingModeOverride: Option[BillingMode] = None, globalSecondaryIndexOverride: Option[List[GlobalSecondaryIndex]] = None, localSecondaryIndexOverride: Option[List[LocalSecondaryIndex]] = None, provisionedThroughputOverride: Option[ProvisionedThroughput] = None, sSESpecificationOverride: Option[SSESpecification] = None) = RestoreTableToPointInTime(RestoreTableToPointInTimeInput(targetTableName, sourceTableArn, sourceTableName, useLatestRestorableTime, restoreDateTime, billingModeOverride, globalSecondaryIndexOverride, localSecondaryIndexOverride, provisionedThroughputOverride, sSESpecificationOverride))
    def scan(tableName: TableName, indexName: Option[IndexName] = None, attributesToGet: Option[List[AttributeName]] = None, limit: Option[PositiveIntegerObject] = None, select: Option[Select] = None, scanFilter: Option[Map[AttributeName,Condition]] = None, conditionalOperator: Option[ConditionalOperator] = None, exclusiveStartKey: Option[Map[AttributeName,AttributeValue]] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, totalSegments: Option[ScanTotalSegments] = None, segment: Option[ScanSegment] = None, projectionExpression: Option[ProjectionExpression] = None, filterExpression: Option[ConditionExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None, expressionAttributeValues: Option[Map[ExpressionAttributeValueVariable,AttributeValue]] = None, consistentRead: Option[ConsistentRead] = None) = Scan(ScanInput(tableName, indexName, attributesToGet, limit, select, scanFilter, conditionalOperator, exclusiveStartKey, returnConsumedCapacity, totalSegments, segment, projectionExpression, filterExpression, expressionAttributeNames, expressionAttributeValues, consistentRead))
    def tagResource(resourceArn: ResourceArnString, tags: List[Tag]) = TagResource(TagResourceInput(resourceArn, tags))
    def transactGetItems(transactItems: List[TransactGetItem], returnConsumedCapacity: Option[ReturnConsumedCapacity] = None) = TransactGetItems(TransactGetItemsInput(transactItems, returnConsumedCapacity))
    def transactWriteItems(transactItems: List[TransactWriteItem], returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, returnItemCollectionMetrics: Option[ReturnItemCollectionMetrics] = None, clientRequestToken: Option[ClientRequestToken] = None) = TransactWriteItems(TransactWriteItemsInput(transactItems, returnConsumedCapacity, returnItemCollectionMetrics, clientRequestToken))
    def untagResource(resourceArn: ResourceArnString, tagKeys: List[TagKeyString]) = UntagResource(UntagResourceInput(resourceArn, tagKeys))
    def updateContinuousBackups(tableName: TableName, pointInTimeRecoverySpecification: PointInTimeRecoverySpecification) = UpdateContinuousBackups(UpdateContinuousBackupsInput(tableName, pointInTimeRecoverySpecification))
    def updateContributorInsights(tableName: TableName, contributorInsightsAction: ContributorInsightsAction, indexName: Option[IndexName] = None) = UpdateContributorInsights(UpdateContributorInsightsInput(tableName, contributorInsightsAction, indexName))
    def updateGlobalTable(globalTableName: TableName, replicaUpdates: List[ReplicaUpdate]) = UpdateGlobalTable(UpdateGlobalTableInput(globalTableName, replicaUpdates))
    def updateGlobalTableSettings(globalTableName: TableName, globalTableBillingMode: Option[BillingMode] = None, globalTableProvisionedWriteCapacityUnits: Option[PositiveLongObject] = None, globalTableProvisionedWriteCapacityAutoScalingSettingsUpdate: Option[AutoScalingSettingsUpdate] = None, globalTableGlobalSecondaryIndexSettingsUpdate: Option[List[GlobalTableGlobalSecondaryIndexSettingsUpdate]] = None, replicaSettingsUpdate: Option[List[ReplicaSettingsUpdate]] = None) = UpdateGlobalTableSettings(UpdateGlobalTableSettingsInput(globalTableName, globalTableBillingMode, globalTableProvisionedWriteCapacityUnits, globalTableProvisionedWriteCapacityAutoScalingSettingsUpdate, globalTableGlobalSecondaryIndexSettingsUpdate, replicaSettingsUpdate))
    def updateItem(tableName: TableName, key: Map[AttributeName,AttributeValue], attributeUpdates: Option[Map[AttributeName,AttributeValueUpdate]] = None, expected: Option[Map[AttributeName,ExpectedAttributeValue]] = None, conditionalOperator: Option[ConditionalOperator] = None, returnValues: Option[ReturnValue] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, returnItemCollectionMetrics: Option[ReturnItemCollectionMetrics] = None, updateExpression: Option[UpdateExpression] = None, conditionExpression: Option[ConditionExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None, expressionAttributeValues: Option[Map[ExpressionAttributeValueVariable,AttributeValue]] = None) = UpdateItem(UpdateItemInput(tableName, key, attributeUpdates, expected, conditionalOperator, returnValues, returnConsumedCapacity, returnItemCollectionMetrics, updateExpression, conditionExpression, expressionAttributeNames, expressionAttributeValues))
    def updateTable(tableName: TableName, attributeDefinitions: Option[List[AttributeDefinition]] = None, billingMode: Option[BillingMode] = None, provisionedThroughput: Option[ProvisionedThroughput] = None, globalSecondaryIndexUpdates: Option[List[GlobalSecondaryIndexUpdate]] = None, streamSpecification: Option[StreamSpecification] = None, sSESpecification: Option[SSESpecification] = None, replicaUpdates: Option[List[ReplicationGroupUpdate]] = None, tableClass: Option[TableClass] = None) = UpdateTable(UpdateTableInput(tableName, attributeDefinitions, billingMode, provisionedThroughput, globalSecondaryIndexUpdates, streamSpecification, sSESpecification, replicaUpdates, tableClass))
    def updateTableReplicaAutoScaling(tableName: TableName, globalSecondaryIndexUpdates: Option[List[GlobalSecondaryIndexAutoScalingUpdate]] = None, provisionedWriteCapacityAutoScalingUpdate: Option[AutoScalingSettingsUpdate] = None, replicaUpdates: Option[List[ReplicaAutoScalingUpdate]] = None) = UpdateTableReplicaAutoScaling(UpdateTableReplicaAutoScalingInput(tableName, globalSecondaryIndexUpdates, provisionedWriteCapacityAutoScalingUpdate, replicaUpdates))
    def updateTimeToLive(tableName: TableName, timeToLiveSpecification: TimeToLiveSpecification) = UpdateTimeToLive(UpdateTimeToLiveInput(tableName, timeToLiveSpecification))
  }

  def transform[P[_, _, _, _, _]](transformation: Transformation[DynamoDBOperation, P]): DynamoDBGen[P] = reified.transform(transformation)

  def transform[P[_, _, _, _, _], P1[_, _, _, _, _]](alg: DynamoDBGen[P], transformation: Transformation[P, P1]): DynamoDBGen[P1] = alg.transform(transformation)

  def asTransformation[P[_, _, _, _, _]](impl : DynamoDBGen[P]): Transformation[DynamoDBOperation, P] = new Transformation[DynamoDBOperation, P] {
    def apply[I, E, O, SI, SO](op : DynamoDBOperation[I, E, O, SI, SO]) : P[I, E, O, SI, SO] = op match  {
      case BatchExecuteStatement(BatchExecuteStatementInput(statements, returnConsumedCapacity)) => impl.batchExecuteStatement(statements, returnConsumedCapacity)
      case BatchGetItem(BatchGetItemInput(requestItems, returnConsumedCapacity)) => impl.batchGetItem(requestItems, returnConsumedCapacity)
      case BatchWriteItem(BatchWriteItemInput(requestItems, returnConsumedCapacity, returnItemCollectionMetrics)) => impl.batchWriteItem(requestItems, returnConsumedCapacity, returnItemCollectionMetrics)
      case CreateBackup(CreateBackupInput(tableName, backupName)) => impl.createBackup(tableName, backupName)
      case CreateGlobalTable(CreateGlobalTableInput(globalTableName, replicationGroup)) => impl.createGlobalTable(globalTableName, replicationGroup)
      case CreateTable(CreateTableInput(attributeDefinitions, tableName, keySchema, localSecondaryIndexes, globalSecondaryIndexes, billingMode, provisionedThroughput, streamSpecification, sSESpecification, tags, tableClass)) => impl.createTable(attributeDefinitions, tableName, keySchema, localSecondaryIndexes, globalSecondaryIndexes, billingMode, provisionedThroughput, streamSpecification, sSESpecification, tags, tableClass)
      case DeleteBackup(DeleteBackupInput(backupArn)) => impl.deleteBackup(backupArn)
      case DeleteItem(DeleteItemInput(tableName, key, expected, conditionalOperator, returnValues, returnConsumedCapacity, returnItemCollectionMetrics, conditionExpression, expressionAttributeNames, expressionAttributeValues)) => impl.deleteItem(tableName, key, expected, conditionalOperator, returnValues, returnConsumedCapacity, returnItemCollectionMetrics, conditionExpression, expressionAttributeNames, expressionAttributeValues)
      case DeleteTable(DeleteTableInput(tableName)) => impl.deleteTable(tableName)
      case DescribeBackup(DescribeBackupInput(backupArn)) => impl.describeBackup(backupArn)
      case DescribeContinuousBackups(DescribeContinuousBackupsInput(tableName)) => impl.describeContinuousBackups(tableName)
      case DescribeContributorInsights(DescribeContributorInsightsInput(tableName, indexName)) => impl.describeContributorInsights(tableName, indexName)
      case DescribeEndpoints(DescribeEndpointsRequest()) => impl.describeEndpoints()
      case DescribeExport(DescribeExportInput(exportArn)) => impl.describeExport(exportArn)
      case DescribeGlobalTable(DescribeGlobalTableInput(globalTableName)) => impl.describeGlobalTable(globalTableName)
      case DescribeGlobalTableSettings(DescribeGlobalTableSettingsInput(globalTableName)) => impl.describeGlobalTableSettings(globalTableName)
      case DescribeKinesisStreamingDestination(DescribeKinesisStreamingDestinationInput(tableName)) => impl.describeKinesisStreamingDestination(tableName)
      case DescribeLimits(DescribeLimitsInput()) => impl.describeLimits()
      case DescribeTable(DescribeTableInput(tableName)) => impl.describeTable(tableName)
      case DescribeTableReplicaAutoScaling(DescribeTableReplicaAutoScalingInput(tableName)) => impl.describeTableReplicaAutoScaling(tableName)
      case DescribeTimeToLive(DescribeTimeToLiveInput(tableName)) => impl.describeTimeToLive(tableName)
      case DisableKinesisStreamingDestination(KinesisStreamingDestinationInput(tableName, streamArn)) => impl.disableKinesisStreamingDestination(tableName, streamArn)
      case EnableKinesisStreamingDestination(KinesisStreamingDestinationInput(tableName, streamArn)) => impl.enableKinesisStreamingDestination(tableName, streamArn)
      case ExecuteStatement(ExecuteStatementInput(statement, parameters, consistentRead, nextToken, returnConsumedCapacity, limit)) => impl.executeStatement(statement, parameters, consistentRead, nextToken, returnConsumedCapacity, limit)
      case ExecuteTransaction(ExecuteTransactionInput(transactStatements, clientRequestToken, returnConsumedCapacity)) => impl.executeTransaction(transactStatements, clientRequestToken, returnConsumedCapacity)
      case ExportTableToPointInTime(ExportTableToPointInTimeInput(tableArn, s3Bucket, exportTime, clientToken, s3BucketOwner, s3Prefix, s3SseAlgorithm, s3SseKmsKeyId, exportFormat)) => impl.exportTableToPointInTime(tableArn, s3Bucket, exportTime, clientToken, s3BucketOwner, s3Prefix, s3SseAlgorithm, s3SseKmsKeyId, exportFormat)
      case GetItem(GetItemInput(tableName, key, attributesToGet, consistentRead, returnConsumedCapacity, projectionExpression, expressionAttributeNames)) => impl.getItem(tableName, key, attributesToGet, consistentRead, returnConsumedCapacity, projectionExpression, expressionAttributeNames)
      case ListBackups(ListBackupsInput(tableName, limit, timeRangeLowerBound, timeRangeUpperBound, exclusiveStartBackupArn, backupType)) => impl.listBackups(tableName, limit, timeRangeLowerBound, timeRangeUpperBound, exclusiveStartBackupArn, backupType)
      case ListContributorInsights(ListContributorInsightsInput(maxResults, tableName, nextToken)) => impl.listContributorInsights(maxResults, tableName, nextToken)
      case ListExports(ListExportsInput(tableArn, maxResults, nextToken)) => impl.listExports(tableArn, maxResults, nextToken)
      case ListGlobalTables(ListGlobalTablesInput(exclusiveStartGlobalTableName, limit, regionName)) => impl.listGlobalTables(exclusiveStartGlobalTableName, limit, regionName)
      case ListTables(ListTablesInput(exclusiveStartTableName, limit)) => impl.listTables(exclusiveStartTableName, limit)
      case ListTagsOfResource(ListTagsOfResourceInput(resourceArn, nextToken)) => impl.listTagsOfResource(resourceArn, nextToken)
      case PutItem(PutItemInput(tableName, item, expected, returnValues, returnConsumedCapacity, returnItemCollectionMetrics, conditionalOperator, conditionExpression, expressionAttributeNames, expressionAttributeValues)) => impl.putItem(tableName, item, expected, returnValues, returnConsumedCapacity, returnItemCollectionMetrics, conditionalOperator, conditionExpression, expressionAttributeNames, expressionAttributeValues)
      case Query(QueryInput(tableName, indexName, select, attributesToGet, limit, consistentRead, keyConditions, queryFilter, conditionalOperator, scanIndexForward, exclusiveStartKey, returnConsumedCapacity, projectionExpression, filterExpression, keyConditionExpression, expressionAttributeNames, expressionAttributeValues)) => impl.query(tableName, indexName, select, attributesToGet, limit, consistentRead, keyConditions, queryFilter, conditionalOperator, scanIndexForward, exclusiveStartKey, returnConsumedCapacity, projectionExpression, filterExpression, keyConditionExpression, expressionAttributeNames, expressionAttributeValues)
      case RestoreTableFromBackup(RestoreTableFromBackupInput(targetTableName, backupArn, billingModeOverride, globalSecondaryIndexOverride, localSecondaryIndexOverride, provisionedThroughputOverride, sSESpecificationOverride)) => impl.restoreTableFromBackup(targetTableName, backupArn, billingModeOverride, globalSecondaryIndexOverride, localSecondaryIndexOverride, provisionedThroughputOverride, sSESpecificationOverride)
      case RestoreTableToPointInTime(RestoreTableToPointInTimeInput(targetTableName, sourceTableArn, sourceTableName, useLatestRestorableTime, restoreDateTime, billingModeOverride, globalSecondaryIndexOverride, localSecondaryIndexOverride, provisionedThroughputOverride, sSESpecificationOverride)) => impl.restoreTableToPointInTime(targetTableName, sourceTableArn, sourceTableName, useLatestRestorableTime, restoreDateTime, billingModeOverride, globalSecondaryIndexOverride, localSecondaryIndexOverride, provisionedThroughputOverride, sSESpecificationOverride)
      case Scan(ScanInput(tableName, indexName, attributesToGet, limit, select, scanFilter, conditionalOperator, exclusiveStartKey, returnConsumedCapacity, totalSegments, segment, projectionExpression, filterExpression, expressionAttributeNames, expressionAttributeValues, consistentRead)) => impl.scan(tableName, indexName, attributesToGet, limit, select, scanFilter, conditionalOperator, exclusiveStartKey, returnConsumedCapacity, totalSegments, segment, projectionExpression, filterExpression, expressionAttributeNames, expressionAttributeValues, consistentRead)
      case TagResource(TagResourceInput(resourceArn, tags)) => impl.tagResource(resourceArn, tags)
      case TransactGetItems(TransactGetItemsInput(transactItems, returnConsumedCapacity)) => impl.transactGetItems(transactItems, returnConsumedCapacity)
      case TransactWriteItems(TransactWriteItemsInput(transactItems, returnConsumedCapacity, returnItemCollectionMetrics, clientRequestToken)) => impl.transactWriteItems(transactItems, returnConsumedCapacity, returnItemCollectionMetrics, clientRequestToken)
      case UntagResource(UntagResourceInput(resourceArn, tagKeys)) => impl.untagResource(resourceArn, tagKeys)
      case UpdateContinuousBackups(UpdateContinuousBackupsInput(tableName, pointInTimeRecoverySpecification)) => impl.updateContinuousBackups(tableName, pointInTimeRecoverySpecification)
      case UpdateContributorInsights(UpdateContributorInsightsInput(tableName, contributorInsightsAction, indexName)) => impl.updateContributorInsights(tableName, contributorInsightsAction, indexName)
      case UpdateGlobalTable(UpdateGlobalTableInput(globalTableName, replicaUpdates)) => impl.updateGlobalTable(globalTableName, replicaUpdates)
      case UpdateGlobalTableSettings(UpdateGlobalTableSettingsInput(globalTableName, globalTableBillingMode, globalTableProvisionedWriteCapacityUnits, globalTableProvisionedWriteCapacityAutoScalingSettingsUpdate, globalTableGlobalSecondaryIndexSettingsUpdate, replicaSettingsUpdate)) => impl.updateGlobalTableSettings(globalTableName, globalTableBillingMode, globalTableProvisionedWriteCapacityUnits, globalTableProvisionedWriteCapacityAutoScalingSettingsUpdate, globalTableGlobalSecondaryIndexSettingsUpdate, replicaSettingsUpdate)
      case UpdateItem(UpdateItemInput(tableName, key, attributeUpdates, expected, conditionalOperator, returnValues, returnConsumedCapacity, returnItemCollectionMetrics, updateExpression, conditionExpression, expressionAttributeNames, expressionAttributeValues)) => impl.updateItem(tableName, key, attributeUpdates, expected, conditionalOperator, returnValues, returnConsumedCapacity, returnItemCollectionMetrics, updateExpression, conditionExpression, expressionAttributeNames, expressionAttributeValues)
      case UpdateTable(UpdateTableInput(tableName, attributeDefinitions, billingMode, provisionedThroughput, globalSecondaryIndexUpdates, streamSpecification, sSESpecification, replicaUpdates, tableClass)) => impl.updateTable(tableName, attributeDefinitions, billingMode, provisionedThroughput, globalSecondaryIndexUpdates, streamSpecification, sSESpecification, replicaUpdates, tableClass)
      case UpdateTableReplicaAutoScaling(UpdateTableReplicaAutoScalingInput(tableName, globalSecondaryIndexUpdates, provisionedWriteCapacityAutoScalingUpdate, replicaUpdates)) => impl.updateTableReplicaAutoScaling(tableName, globalSecondaryIndexUpdates, provisionedWriteCapacityAutoScalingUpdate, replicaUpdates)
      case UpdateTimeToLive(UpdateTimeToLiveInput(tableName, timeToLiveSpecification)) => impl.updateTimeToLive(tableName, timeToLiveSpecification)
    }
  }
  case class BatchExecuteStatement(input: BatchExecuteStatementInput) extends DynamoDBOperation[BatchExecuteStatementInput, DynamoDBGen.BatchExecuteStatementError, BatchExecuteStatementOutput, Nothing, Nothing]
  object BatchExecuteStatement extends Endpoint[DynamoDBOperation, BatchExecuteStatementInput, DynamoDBGen.BatchExecuteStatementError, BatchExecuteStatementOutput, Nothing, Nothing] with Errorable[BatchExecuteStatementError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BatchExecuteStatement")
    val input: Schema[BatchExecuteStatementInput] = BatchExecuteStatementInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[BatchExecuteStatementOutput] = BatchExecuteStatementOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>This operation allows you to perform batch reads or writes on data stored in DynamoDB,\n            using PartiQL. Each read statement in a <code>BatchExecuteStatement</code> must specify an equality\n            condition on all key attributes. This enforces that each <code>SELECT</code> statement in a\n            batch returns at most a single item.</p>\n        <note>\n            <p>The entire batch must consist of either read statements or write statements, you\n                cannot mix both in one batch.</p>\n        </note>\n        <important>\n            <p>A HTTP 200 response does not mean that all statements in the BatchExecuteStatement\n                succeeded. Error details for individual statements can be found under the <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/APIReference/API_BatchStatementResponse.html#DDB-Type-BatchStatementResponse-Error\">Error</a> field of the <code>BatchStatementResponse</code> for each\n                statement.</p>\n        </important>"),
    )
    def wrap(input: BatchExecuteStatementInput) = BatchExecuteStatement(input)
    override val errorable: Option[Errorable[BatchExecuteStatementError]] = Some(this)
    val error: UnionSchema[BatchExecuteStatementError] = BatchExecuteStatementError.schema
    def liftError(throwable: Throwable) : Option[BatchExecuteStatementError] = throwable match {
      case e: InternalServerError => Some(BatchExecuteStatementError.InternalServerErrorCase(e))
      case e: RequestLimitExceeded => Some(BatchExecuteStatementError.RequestLimitExceededCase(e))
      case _ => None
    }
    def unliftError(e: BatchExecuteStatementError) : Throwable = e match {
      case BatchExecuteStatementError.InternalServerErrorCase(e) => e
      case BatchExecuteStatementError.RequestLimitExceededCase(e) => e
    }
  }
  sealed trait BatchExecuteStatementError extends scala.Product with scala.Serializable {
    @inline final def widen: BatchExecuteStatementError = this
  }
  object BatchExecuteStatementError extends ShapeTag.Companion[BatchExecuteStatementError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BatchExecuteStatementError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends BatchExecuteStatementError
    case class RequestLimitExceededCase(requestLimitExceeded: RequestLimitExceeded) extends BatchExecuteStatementError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[BatchExecuteStatementError]("InternalServerError")
    }
    object RequestLimitExceededCase {
      val hints : Hints = Hints.empty
      val schema: Schema[RequestLimitExceededCase] = bijection(RequestLimitExceeded.schema.addHints(hints), RequestLimitExceededCase(_), _.requestLimitExceeded)
      val alt = schema.oneOf[BatchExecuteStatementError]("RequestLimitExceeded")
    }

    implicit val schema: UnionSchema[BatchExecuteStatementError] = union(
      InternalServerErrorCase.alt,
      RequestLimitExceededCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : RequestLimitExceededCase => RequestLimitExceededCase.alt(c)
    }
  }
  case class BatchGetItem(input: BatchGetItemInput) extends DynamoDBOperation[BatchGetItemInput, DynamoDBGen.BatchGetItemError, BatchGetItemOutput, Nothing, Nothing]
  object BatchGetItem extends Endpoint[DynamoDBOperation, BatchGetItemInput, DynamoDBGen.BatchGetItemError, BatchGetItemOutput, Nothing, Nothing] with Errorable[BatchGetItemError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BatchGetItem")
    val input: Schema[BatchGetItemInput] = BatchGetItemInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[BatchGetItemOutput] = BatchGetItemOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>The <code>BatchGetItem</code> operation returns the attributes of one or more items\n            from one or more tables. You identify requested items by primary key.</p>\n        <p>A single operation can retrieve up to 16 MB of data, which can contain as many as 100\n            items. <code>BatchGetItem</code> returns a partial result if the response size limit is\n            exceeded, the table\'s provisioned throughput is exceeded, or an internal processing\n            failure occurs. If a partial result is returned, the operation returns a value for\n                <code>UnprocessedKeys</code>. You can use this value to retry the operation starting\n            with the next item to get.</p>\n        <important>\n            <p>If you request more than 100 items, <code>BatchGetItem</code> returns a\n                    <code>ValidationException</code> with the message \"Too many items requested for\n                the BatchGetItem call.\"</p>\n        </important>\n        <p>For example, if you ask to retrieve 100 items, but each individual item is 300 KB in\n            size, the system returns 52 items (so as not to exceed the 16 MB limit). It also returns\n            an appropriate <code>UnprocessedKeys</code> value so you can get the next page of\n            results. If desired, your application can include its own logic to assemble the pages of\n            results into one dataset.</p>\n        <p>If <i>none</i> of the items can be processed due to insufficient\n            provisioned throughput on all of the tables in the request, then\n                <code>BatchGetItem</code> returns a\n                <code>ProvisionedThroughputExceededException</code>. If <i>at least\n                one</i> of the items is successfully processed, then\n                <code>BatchGetItem</code> completes successfully, while returning the keys of the\n            unread items in <code>UnprocessedKeys</code>.</p>\n        <important>\n            <p>If DynamoDB returns any unprocessed items, you should retry the batch operation on\n                those items. However, <i>we strongly recommend that you use an exponential\n                    backoff algorithm</i>. If you retry the batch operation immediately, the\n                underlying read or write requests can still fail due to throttling on the individual\n                tables. If you delay the batch operation using exponential backoff, the individual\n                requests in the batch are much more likely to succeed.</p>\n            <p>For more information, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/ErrorHandling.html#BatchOperations\">Batch Operations and Error Handling</a> in the <i>Amazon DynamoDB\n                    Developer Guide</i>.</p>\n        </important>\n        <p>By default, <code>BatchGetItem</code> performs eventually consistent reads on every\n            table in the request. If you want strongly consistent reads instead, you can set\n                <code>ConsistentRead</code> to <code>true</code> for any or all tables.</p>\n        <p>In order to minimize response latency, <code>BatchGetItem</code> retrieves items in\n            parallel.</p>\n        <p>When designing your application, keep in mind that DynamoDB does not return items in\n            any particular order. To help parse the response by item, include the primary key values\n            for the items in your request in the <code>ProjectionExpression</code> parameter.</p>\n        <p>If a requested item does not exist, it is not returned in the result. Requests for\n            nonexistent items consume the minimum read capacity units according to the type of read.\n            For more information, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/WorkingWithTables.html#CapacityUnitCalculations\">Working with Tables</a> in the <i>Amazon DynamoDB Developer\n                Guide</i>.</p>"),
    )
    def wrap(input: BatchGetItemInput) = BatchGetItem(input)
    override val errorable: Option[Errorable[BatchGetItemError]] = Some(this)
    val error: UnionSchema[BatchGetItemError] = BatchGetItemError.schema
    def liftError(throwable: Throwable) : Option[BatchGetItemError] = throwable match {
      case e: InternalServerError => Some(BatchGetItemError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(BatchGetItemError.InvalidEndpointExceptionCase(e))
      case e: ProvisionedThroughputExceededException => Some(BatchGetItemError.ProvisionedThroughputExceededExceptionCase(e))
      case e: RequestLimitExceeded => Some(BatchGetItemError.RequestLimitExceededCase(e))
      case e: ResourceNotFoundException => Some(BatchGetItemError.ResourceNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: BatchGetItemError) : Throwable = e match {
      case BatchGetItemError.InternalServerErrorCase(e) => e
      case BatchGetItemError.InvalidEndpointExceptionCase(e) => e
      case BatchGetItemError.ProvisionedThroughputExceededExceptionCase(e) => e
      case BatchGetItemError.RequestLimitExceededCase(e) => e
      case BatchGetItemError.ResourceNotFoundExceptionCase(e) => e
    }
  }
  sealed trait BatchGetItemError extends scala.Product with scala.Serializable {
    @inline final def widen: BatchGetItemError = this
  }
  object BatchGetItemError extends ShapeTag.Companion[BatchGetItemError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BatchGetItemError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends BatchGetItemError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends BatchGetItemError
    case class ProvisionedThroughputExceededExceptionCase(provisionedThroughputExceededException: ProvisionedThroughputExceededException) extends BatchGetItemError
    case class RequestLimitExceededCase(requestLimitExceeded: RequestLimitExceeded) extends BatchGetItemError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends BatchGetItemError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[BatchGetItemError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[BatchGetItemError]("InvalidEndpointException")
    }
    object ProvisionedThroughputExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ProvisionedThroughputExceededExceptionCase] = bijection(ProvisionedThroughputExceededException.schema.addHints(hints), ProvisionedThroughputExceededExceptionCase(_), _.provisionedThroughputExceededException)
      val alt = schema.oneOf[BatchGetItemError]("ProvisionedThroughputExceededException")
    }
    object RequestLimitExceededCase {
      val hints : Hints = Hints.empty
      val schema: Schema[RequestLimitExceededCase] = bijection(RequestLimitExceeded.schema.addHints(hints), RequestLimitExceededCase(_), _.requestLimitExceeded)
      val alt = schema.oneOf[BatchGetItemError]("RequestLimitExceeded")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[BatchGetItemError]("ResourceNotFoundException")
    }

    implicit val schema: UnionSchema[BatchGetItemError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      ProvisionedThroughputExceededExceptionCase.alt,
      RequestLimitExceededCase.alt,
      ResourceNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : ProvisionedThroughputExceededExceptionCase => ProvisionedThroughputExceededExceptionCase.alt(c)
      case c : RequestLimitExceededCase => RequestLimitExceededCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
    }
  }
  case class BatchWriteItem(input: BatchWriteItemInput) extends DynamoDBOperation[BatchWriteItemInput, DynamoDBGen.BatchWriteItemError, BatchWriteItemOutput, Nothing, Nothing]
  object BatchWriteItem extends Endpoint[DynamoDBOperation, BatchWriteItemInput, DynamoDBGen.BatchWriteItemError, BatchWriteItemOutput, Nothing, Nothing] with Errorable[BatchWriteItemError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BatchWriteItem")
    val input: Schema[BatchWriteItemInput] = BatchWriteItemInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[BatchWriteItemOutput] = BatchWriteItemOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>The <code>BatchWriteItem</code> operation puts or deletes multiple items in one or\n            more tables. A single call to <code>BatchWriteItem</code> can transmit up to 16MB of\n            data over the network, consisting of up to 25 item put or delete operations. While\n            individual items can be up to 400 KB once stored, it\'s important to note that an item\'s\n            representation might be greater than 400KB while being sent in DynamoDB\'s JSON format\n            for the API call. For more details on this distinction, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/HowItWorks.NamingRulesDataTypes.html\">Naming Rules and Data Types</a>.</p>\n        <note>\n            <p>\n                <code>BatchWriteItem</code> cannot update items. To update items, use the\n                    <code>UpdateItem</code> action.</p>\n        </note>\n        <p>The individual <code>PutItem</code> and <code>DeleteItem</code> operations specified\n            in <code>BatchWriteItem</code> are atomic; however <code>BatchWriteItem</code> as a\n            whole is not. If any requested operations fail because the table\'s provisioned\n            throughput is exceeded or an internal processing failure occurs, the failed operations\n            are returned in the <code>UnprocessedItems</code> response parameter. You can\n            investigate and optionally resend the requests. Typically, you would call\n                <code>BatchWriteItem</code> in a loop. Each iteration would check for unprocessed\n            items and submit a new <code>BatchWriteItem</code> request with those unprocessed items\n            until all items have been processed.</p>\n        <p>If <i>none</i> of the items can be processed due to insufficient\n            provisioned throughput on all of the tables in the request, then\n                <code>BatchWriteItem</code> returns a\n                <code>ProvisionedThroughputExceededException</code>.</p>\n        <important>\n            <p>If DynamoDB returns any unprocessed items, you should retry the batch operation on\n                those items. However, <i>we strongly recommend that you use an exponential\n                    backoff algorithm</i>. If you retry the batch operation immediately, the\n                underlying read or write requests can still fail due to throttling on the individual\n                tables. If you delay the batch operation using exponential backoff, the individual\n                requests in the batch are much more likely to succeed.</p>\n            <p>For more information, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/ErrorHandling.html#Programming.Errors.BatchOperations\">Batch Operations and Error Handling</a> in the <i>Amazon DynamoDB\n                    Developer Guide</i>.</p>\n        </important>\n\n        <p>With <code>BatchWriteItem</code>, you can efficiently write or delete large amounts of\n            data, such as from Amazon EMR, or copy data from another database into DynamoDB. In\n            order to improve performance with these large-scale operations,\n                <code>BatchWriteItem</code> does not behave in the same way as individual\n                <code>PutItem</code> and <code>DeleteItem</code> calls would. For example, you\n            cannot specify conditions on individual put and delete requests, and\n                <code>BatchWriteItem</code> does not return deleted items in the response.</p>\n        <p>If you use a programming language that supports concurrency, you can use threads to\n            write items in parallel. Your application must include the necessary logic to manage the\n            threads. With languages that don\'t support threading, you must update or delete the\n            specified items one at a time. In both situations, <code>BatchWriteItem</code> performs\n            the specified put and delete operations in parallel, giving you the power of the thread\n            pool approach without having to introduce complexity into your application.</p>\n        <p>Parallel processing reduces latency, but each specified put and delete request\n            consumes the same number of write capacity units whether it is processed in parallel or\n            not. Delete operations on nonexistent items consume one write capacity unit.</p>\n        <p>If one or more of the following is true, DynamoDB rejects the entire batch write\n            operation:</p>\n        <ul>\n            <li>\n                <p>One or more tables specified in the <code>BatchWriteItem</code> request does\n                    not exist.</p>\n            </li>\n            <li>\n                <p>Primary key attributes specified on an item in the request do not match those\n                    in the corresponding table\'s primary key schema.</p>\n            </li>\n            <li>\n                <p>You try to perform multiple operations on the same item in the same\n                        <code>BatchWriteItem</code> request. For example, you cannot put and delete\n                    the same item in the same <code>BatchWriteItem</code> request. </p>\n            </li>\n            <li>\n                <p> Your request contains at least two items with identical hash and range keys\n                    (which essentially is two put operations). </p>\n            </li>\n            <li>\n                <p>There are more than 25 requests in the batch.</p>\n            </li>\n            <li>\n                <p>Any individual item in a batch exceeds 400 KB.</p>\n            </li>\n            <li>\n                <p>The total request size exceeds 16 MB.</p>\n            </li>\n         </ul>"),
    )
    def wrap(input: BatchWriteItemInput) = BatchWriteItem(input)
    override val errorable: Option[Errorable[BatchWriteItemError]] = Some(this)
    val error: UnionSchema[BatchWriteItemError] = BatchWriteItemError.schema
    def liftError(throwable: Throwable) : Option[BatchWriteItemError] = throwable match {
      case e: InternalServerError => Some(BatchWriteItemError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(BatchWriteItemError.InvalidEndpointExceptionCase(e))
      case e: ItemCollectionSizeLimitExceededException => Some(BatchWriteItemError.ItemCollectionSizeLimitExceededExceptionCase(e))
      case e: ProvisionedThroughputExceededException => Some(BatchWriteItemError.ProvisionedThroughputExceededExceptionCase(e))
      case e: RequestLimitExceeded => Some(BatchWriteItemError.RequestLimitExceededCase(e))
      case e: ResourceNotFoundException => Some(BatchWriteItemError.ResourceNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: BatchWriteItemError) : Throwable = e match {
      case BatchWriteItemError.InternalServerErrorCase(e) => e
      case BatchWriteItemError.InvalidEndpointExceptionCase(e) => e
      case BatchWriteItemError.ItemCollectionSizeLimitExceededExceptionCase(e) => e
      case BatchWriteItemError.ProvisionedThroughputExceededExceptionCase(e) => e
      case BatchWriteItemError.RequestLimitExceededCase(e) => e
      case BatchWriteItemError.ResourceNotFoundExceptionCase(e) => e
    }
  }
  sealed trait BatchWriteItemError extends scala.Product with scala.Serializable {
    @inline final def widen: BatchWriteItemError = this
  }
  object BatchWriteItemError extends ShapeTag.Companion[BatchWriteItemError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BatchWriteItemError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends BatchWriteItemError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends BatchWriteItemError
    case class ItemCollectionSizeLimitExceededExceptionCase(itemCollectionSizeLimitExceededException: ItemCollectionSizeLimitExceededException) extends BatchWriteItemError
    case class ProvisionedThroughputExceededExceptionCase(provisionedThroughputExceededException: ProvisionedThroughputExceededException) extends BatchWriteItemError
    case class RequestLimitExceededCase(requestLimitExceeded: RequestLimitExceeded) extends BatchWriteItemError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends BatchWriteItemError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[BatchWriteItemError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[BatchWriteItemError]("InvalidEndpointException")
    }
    object ItemCollectionSizeLimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ItemCollectionSizeLimitExceededExceptionCase] = bijection(ItemCollectionSizeLimitExceededException.schema.addHints(hints), ItemCollectionSizeLimitExceededExceptionCase(_), _.itemCollectionSizeLimitExceededException)
      val alt = schema.oneOf[BatchWriteItemError]("ItemCollectionSizeLimitExceededException")
    }
    object ProvisionedThroughputExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ProvisionedThroughputExceededExceptionCase] = bijection(ProvisionedThroughputExceededException.schema.addHints(hints), ProvisionedThroughputExceededExceptionCase(_), _.provisionedThroughputExceededException)
      val alt = schema.oneOf[BatchWriteItemError]("ProvisionedThroughputExceededException")
    }
    object RequestLimitExceededCase {
      val hints : Hints = Hints.empty
      val schema: Schema[RequestLimitExceededCase] = bijection(RequestLimitExceeded.schema.addHints(hints), RequestLimitExceededCase(_), _.requestLimitExceeded)
      val alt = schema.oneOf[BatchWriteItemError]("RequestLimitExceeded")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[BatchWriteItemError]("ResourceNotFoundException")
    }

    implicit val schema: UnionSchema[BatchWriteItemError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      ItemCollectionSizeLimitExceededExceptionCase.alt,
      ProvisionedThroughputExceededExceptionCase.alt,
      RequestLimitExceededCase.alt,
      ResourceNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : ItemCollectionSizeLimitExceededExceptionCase => ItemCollectionSizeLimitExceededExceptionCase.alt(c)
      case c : ProvisionedThroughputExceededExceptionCase => ProvisionedThroughputExceededExceptionCase.alt(c)
      case c : RequestLimitExceededCase => RequestLimitExceededCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
    }
  }
  case class CreateBackup(input: CreateBackupInput) extends DynamoDBOperation[CreateBackupInput, DynamoDBGen.CreateBackupError, CreateBackupOutput, Nothing, Nothing]
  object CreateBackup extends Endpoint[DynamoDBOperation, CreateBackupInput, DynamoDBGen.CreateBackupError, CreateBackupOutput, Nothing, Nothing] with Errorable[CreateBackupError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "CreateBackup")
    val input: Schema[CreateBackupInput] = CreateBackupInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[CreateBackupOutput] = CreateBackupOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Creates a backup for an existing table.</p>\n        <p> Each time you create an on-demand backup, the entire table data is backed up. There\n            is no limit to the number of on-demand backups that can be taken. </p>\n        <p> When you create an on-demand backup, a time marker of the request is cataloged, and\n            the backup is created asynchronously, by applying all changes until the time of the\n            request to the last full table snapshot. Backup requests are processed instantaneously\n            and become available for restore within minutes. </p>\n        <p>You can call <code>CreateBackup</code> at a maximum rate of 50 times per\n            second.</p>\n        <p>All backups in DynamoDB work without consuming any provisioned throughput on the\n            table.</p>\n        <p> If you submit a backup request on 2018-12-14 at 14:25:00, the backup is guaranteed to\n            contain all data committed to the table up to 14:24:00, and data committed after\n            14:26:00 will not be. The backup might contain data modifications made between 14:24:00\n            and 14:26:00. On-demand backup does not support causal consistency. </p>\n        <p> Along with data, the following are also included on the backups: </p>\n        <ul>\n            <li>\n                <p>Global secondary indexes (GSIs)</p>\n            </li>\n            <li>\n                <p>Local secondary indexes (LSIs)</p>\n            </li>\n            <li>\n                <p>Streams</p>\n            </li>\n            <li>\n                <p>Provisioned read and write capacity</p>\n            </li>\n         </ul>"),
    )
    def wrap(input: CreateBackupInput) = CreateBackup(input)
    override val errorable: Option[Errorable[CreateBackupError]] = Some(this)
    val error: UnionSchema[CreateBackupError] = CreateBackupError.schema
    def liftError(throwable: Throwable) : Option[CreateBackupError] = throwable match {
      case e: BackupInUseException => Some(CreateBackupError.BackupInUseExceptionCase(e))
      case e: ContinuousBackupsUnavailableException => Some(CreateBackupError.ContinuousBackupsUnavailableExceptionCase(e))
      case e: InternalServerError => Some(CreateBackupError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(CreateBackupError.InvalidEndpointExceptionCase(e))
      case e: LimitExceededException => Some(CreateBackupError.LimitExceededExceptionCase(e))
      case e: TableInUseException => Some(CreateBackupError.TableInUseExceptionCase(e))
      case e: TableNotFoundException => Some(CreateBackupError.TableNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: CreateBackupError) : Throwable = e match {
      case CreateBackupError.BackupInUseExceptionCase(e) => e
      case CreateBackupError.ContinuousBackupsUnavailableExceptionCase(e) => e
      case CreateBackupError.InternalServerErrorCase(e) => e
      case CreateBackupError.InvalidEndpointExceptionCase(e) => e
      case CreateBackupError.LimitExceededExceptionCase(e) => e
      case CreateBackupError.TableInUseExceptionCase(e) => e
      case CreateBackupError.TableNotFoundExceptionCase(e) => e
    }
  }
  sealed trait CreateBackupError extends scala.Product with scala.Serializable {
    @inline final def widen: CreateBackupError = this
  }
  object CreateBackupError extends ShapeTag.Companion[CreateBackupError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "CreateBackupError")

    val hints : Hints = Hints.empty

    case class BackupInUseExceptionCase(backupInUseException: BackupInUseException) extends CreateBackupError
    case class ContinuousBackupsUnavailableExceptionCase(continuousBackupsUnavailableException: ContinuousBackupsUnavailableException) extends CreateBackupError
    case class InternalServerErrorCase(internalServerError: InternalServerError) extends CreateBackupError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends CreateBackupError
    case class LimitExceededExceptionCase(limitExceededException: LimitExceededException) extends CreateBackupError
    case class TableInUseExceptionCase(tableInUseException: TableInUseException) extends CreateBackupError
    case class TableNotFoundExceptionCase(tableNotFoundException: TableNotFoundException) extends CreateBackupError

    object BackupInUseExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[BackupInUseExceptionCase] = bijection(BackupInUseException.schema.addHints(hints), BackupInUseExceptionCase(_), _.backupInUseException)
      val alt = schema.oneOf[CreateBackupError]("BackupInUseException")
    }
    object ContinuousBackupsUnavailableExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ContinuousBackupsUnavailableExceptionCase] = bijection(ContinuousBackupsUnavailableException.schema.addHints(hints), ContinuousBackupsUnavailableExceptionCase(_), _.continuousBackupsUnavailableException)
      val alt = schema.oneOf[CreateBackupError]("ContinuousBackupsUnavailableException")
    }
    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[CreateBackupError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[CreateBackupError]("InvalidEndpointException")
    }
    object LimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[LimitExceededExceptionCase] = bijection(LimitExceededException.schema.addHints(hints), LimitExceededExceptionCase(_), _.limitExceededException)
      val alt = schema.oneOf[CreateBackupError]("LimitExceededException")
    }
    object TableInUseExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[TableInUseExceptionCase] = bijection(TableInUseException.schema.addHints(hints), TableInUseExceptionCase(_), _.tableInUseException)
      val alt = schema.oneOf[CreateBackupError]("TableInUseException")
    }
    object TableNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[TableNotFoundExceptionCase] = bijection(TableNotFoundException.schema.addHints(hints), TableNotFoundExceptionCase(_), _.tableNotFoundException)
      val alt = schema.oneOf[CreateBackupError]("TableNotFoundException")
    }

    implicit val schema: UnionSchema[CreateBackupError] = union(
      BackupInUseExceptionCase.alt,
      ContinuousBackupsUnavailableExceptionCase.alt,
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      LimitExceededExceptionCase.alt,
      TableInUseExceptionCase.alt,
      TableNotFoundExceptionCase.alt,
    ){
      case c : BackupInUseExceptionCase => BackupInUseExceptionCase.alt(c)
      case c : ContinuousBackupsUnavailableExceptionCase => ContinuousBackupsUnavailableExceptionCase.alt(c)
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : LimitExceededExceptionCase => LimitExceededExceptionCase.alt(c)
      case c : TableInUseExceptionCase => TableInUseExceptionCase.alt(c)
      case c : TableNotFoundExceptionCase => TableNotFoundExceptionCase.alt(c)
    }
  }
  case class CreateGlobalTable(input: CreateGlobalTableInput) extends DynamoDBOperation[CreateGlobalTableInput, DynamoDBGen.CreateGlobalTableError, CreateGlobalTableOutput, Nothing, Nothing]
  object CreateGlobalTable extends Endpoint[DynamoDBOperation, CreateGlobalTableInput, DynamoDBGen.CreateGlobalTableError, CreateGlobalTableOutput, Nothing, Nothing] with Errorable[CreateGlobalTableError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "CreateGlobalTable")
    val input: Schema[CreateGlobalTableInput] = CreateGlobalTableInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[CreateGlobalTableOutput] = CreateGlobalTableOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Creates a global table from an existing table. A global table creates a replication\n            relationship between two or more DynamoDB tables with the same table name in the\n            provided Regions. </p>\n        <note>\n            <p>This operation only applies to <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/globaltables.V1.html\">Version\n                    2017.11.29</a> of global tables.</p>\n        </note>\n\n        <p>If you want to add a new replica table to a global table, each of the following\n            conditions must be true:</p>\n        <ul>\n            <li>\n                <p>The table must have the same primary key as all of the other replicas.</p>\n            </li>\n            <li>\n                <p>The table must have the same name as all of the other replicas.</p>\n            </li>\n            <li>\n                <p>The table must have DynamoDB Streams enabled, with the stream containing both\n                    the new and the old images of the item.</p>\n            </li>\n            <li>\n                <p>None of the replica tables in the global table can contain any data.</p>\n            </li>\n         </ul>\n        <p> If global secondary indexes are specified, then the following conditions must also be\n            met: </p>\n        <ul>\n            <li>\n                <p> The global secondary indexes must have the same name. </p>\n            </li>\n            <li>\n                <p> The global secondary indexes must have the same hash key and sort key (if\n                    present). </p>\n            </li>\n         </ul>\n        <p> If local secondary indexes are specified, then the following conditions must also be\n            met: </p>\n        <ul>\n            <li>\n                <p> The local secondary indexes must have the same name. </p>\n            </li>\n            <li>\n                <p> The local secondary indexes must have the same hash key and sort key (if\n                    present). </p>\n            </li>\n         </ul>\n\n        <important>\n            <p> Write capacity settings should be set consistently across your replica tables and\n                secondary indexes. DynamoDB strongly recommends enabling auto scaling to manage the\n                write capacity settings for all of your global tables replicas and indexes. </p>\n            <p> If you prefer to manage write capacity settings manually, you should provision\n                equal replicated write capacity units to your replica tables. You should also\n                provision equal replicated write capacity units to matching secondary indexes across\n                your global table. </p>\n        </important>"),
    )
    def wrap(input: CreateGlobalTableInput) = CreateGlobalTable(input)
    override val errorable: Option[Errorable[CreateGlobalTableError]] = Some(this)
    val error: UnionSchema[CreateGlobalTableError] = CreateGlobalTableError.schema
    def liftError(throwable: Throwable) : Option[CreateGlobalTableError] = throwable match {
      case e: GlobalTableAlreadyExistsException => Some(CreateGlobalTableError.GlobalTableAlreadyExistsExceptionCase(e))
      case e: InternalServerError => Some(CreateGlobalTableError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(CreateGlobalTableError.InvalidEndpointExceptionCase(e))
      case e: LimitExceededException => Some(CreateGlobalTableError.LimitExceededExceptionCase(e))
      case e: TableNotFoundException => Some(CreateGlobalTableError.TableNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: CreateGlobalTableError) : Throwable = e match {
      case CreateGlobalTableError.GlobalTableAlreadyExistsExceptionCase(e) => e
      case CreateGlobalTableError.InternalServerErrorCase(e) => e
      case CreateGlobalTableError.InvalidEndpointExceptionCase(e) => e
      case CreateGlobalTableError.LimitExceededExceptionCase(e) => e
      case CreateGlobalTableError.TableNotFoundExceptionCase(e) => e
    }
  }
  sealed trait CreateGlobalTableError extends scala.Product with scala.Serializable {
    @inline final def widen: CreateGlobalTableError = this
  }
  object CreateGlobalTableError extends ShapeTag.Companion[CreateGlobalTableError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "CreateGlobalTableError")

    val hints : Hints = Hints.empty

    case class GlobalTableAlreadyExistsExceptionCase(globalTableAlreadyExistsException: GlobalTableAlreadyExistsException) extends CreateGlobalTableError
    case class InternalServerErrorCase(internalServerError: InternalServerError) extends CreateGlobalTableError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends CreateGlobalTableError
    case class LimitExceededExceptionCase(limitExceededException: LimitExceededException) extends CreateGlobalTableError
    case class TableNotFoundExceptionCase(tableNotFoundException: TableNotFoundException) extends CreateGlobalTableError

    object GlobalTableAlreadyExistsExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[GlobalTableAlreadyExistsExceptionCase] = bijection(GlobalTableAlreadyExistsException.schema.addHints(hints), GlobalTableAlreadyExistsExceptionCase(_), _.globalTableAlreadyExistsException)
      val alt = schema.oneOf[CreateGlobalTableError]("GlobalTableAlreadyExistsException")
    }
    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[CreateGlobalTableError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[CreateGlobalTableError]("InvalidEndpointException")
    }
    object LimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[LimitExceededExceptionCase] = bijection(LimitExceededException.schema.addHints(hints), LimitExceededExceptionCase(_), _.limitExceededException)
      val alt = schema.oneOf[CreateGlobalTableError]("LimitExceededException")
    }
    object TableNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[TableNotFoundExceptionCase] = bijection(TableNotFoundException.schema.addHints(hints), TableNotFoundExceptionCase(_), _.tableNotFoundException)
      val alt = schema.oneOf[CreateGlobalTableError]("TableNotFoundException")
    }

    implicit val schema: UnionSchema[CreateGlobalTableError] = union(
      GlobalTableAlreadyExistsExceptionCase.alt,
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      LimitExceededExceptionCase.alt,
      TableNotFoundExceptionCase.alt,
    ){
      case c : GlobalTableAlreadyExistsExceptionCase => GlobalTableAlreadyExistsExceptionCase.alt(c)
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : LimitExceededExceptionCase => LimitExceededExceptionCase.alt(c)
      case c : TableNotFoundExceptionCase => TableNotFoundExceptionCase.alt(c)
    }
  }
  case class CreateTable(input: CreateTableInput) extends DynamoDBOperation[CreateTableInput, DynamoDBGen.CreateTableError, CreateTableOutput, Nothing, Nothing]
  object CreateTable extends Endpoint[DynamoDBOperation, CreateTableInput, DynamoDBGen.CreateTableError, CreateTableOutput, Nothing, Nothing] with Errorable[CreateTableError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "CreateTable")
    val input: Schema[CreateTableInput] = CreateTableInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[CreateTableOutput] = CreateTableOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>The <code>CreateTable</code> operation adds a new table to your account. In an Amazon Web Services account, table names must be unique within each Region. That is, you can\n            have two tables with same name if you create the tables in different Regions.</p>\n        <p>\n            <code>CreateTable</code> is an asynchronous operation. Upon receiving a\n                <code>CreateTable</code> request, DynamoDB immediately returns a response with a\n                <code>TableStatus</code> of <code>CREATING</code>. After the table is created,\n            DynamoDB sets the <code>TableStatus</code> to <code>ACTIVE</code>. You can perform read\n            and write operations only on an <code>ACTIVE</code> table. </p>\n        <p>You can optionally define secondary indexes on the new table, as part of the\n                <code>CreateTable</code> operation. If you want to create multiple tables with\n            secondary indexes on them, you must create the tables sequentially. Only one table with\n            secondary indexes can be in the <code>CREATING</code> state at any given time.</p>\n        <p>You can use the <code>DescribeTable</code> action to check the table status.</p>"),
    )
    def wrap(input: CreateTableInput) = CreateTable(input)
    override val errorable: Option[Errorable[CreateTableError]] = Some(this)
    val error: UnionSchema[CreateTableError] = CreateTableError.schema
    def liftError(throwable: Throwable) : Option[CreateTableError] = throwable match {
      case e: InternalServerError => Some(CreateTableError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(CreateTableError.InvalidEndpointExceptionCase(e))
      case e: LimitExceededException => Some(CreateTableError.LimitExceededExceptionCase(e))
      case e: ResourceInUseException => Some(CreateTableError.ResourceInUseExceptionCase(e))
      case _ => None
    }
    def unliftError(e: CreateTableError) : Throwable = e match {
      case CreateTableError.InternalServerErrorCase(e) => e
      case CreateTableError.InvalidEndpointExceptionCase(e) => e
      case CreateTableError.LimitExceededExceptionCase(e) => e
      case CreateTableError.ResourceInUseExceptionCase(e) => e
    }
  }
  sealed trait CreateTableError extends scala.Product with scala.Serializable {
    @inline final def widen: CreateTableError = this
  }
  object CreateTableError extends ShapeTag.Companion[CreateTableError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "CreateTableError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends CreateTableError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends CreateTableError
    case class LimitExceededExceptionCase(limitExceededException: LimitExceededException) extends CreateTableError
    case class ResourceInUseExceptionCase(resourceInUseException: ResourceInUseException) extends CreateTableError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[CreateTableError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[CreateTableError]("InvalidEndpointException")
    }
    object LimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[LimitExceededExceptionCase] = bijection(LimitExceededException.schema.addHints(hints), LimitExceededExceptionCase(_), _.limitExceededException)
      val alt = schema.oneOf[CreateTableError]("LimitExceededException")
    }
    object ResourceInUseExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceInUseExceptionCase] = bijection(ResourceInUseException.schema.addHints(hints), ResourceInUseExceptionCase(_), _.resourceInUseException)
      val alt = schema.oneOf[CreateTableError]("ResourceInUseException")
    }

    implicit val schema: UnionSchema[CreateTableError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      LimitExceededExceptionCase.alt,
      ResourceInUseExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : LimitExceededExceptionCase => LimitExceededExceptionCase.alt(c)
      case c : ResourceInUseExceptionCase => ResourceInUseExceptionCase.alt(c)
    }
  }
  case class DeleteBackup(input: DeleteBackupInput) extends DynamoDBOperation[DeleteBackupInput, DynamoDBGen.DeleteBackupError, DeleteBackupOutput, Nothing, Nothing]
  object DeleteBackup extends Endpoint[DynamoDBOperation, DeleteBackupInput, DynamoDBGen.DeleteBackupError, DeleteBackupOutput, Nothing, Nothing] with Errorable[DeleteBackupError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DeleteBackup")
    val input: Schema[DeleteBackupInput] = DeleteBackupInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[DeleteBackupOutput] = DeleteBackupOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Deletes an existing backup of a table.</p>\n        <p>You can call <code>DeleteBackup</code> at a maximum rate of 10 times per\n            second.</p>"),
    )
    def wrap(input: DeleteBackupInput) = DeleteBackup(input)
    override val errorable: Option[Errorable[DeleteBackupError]] = Some(this)
    val error: UnionSchema[DeleteBackupError] = DeleteBackupError.schema
    def liftError(throwable: Throwable) : Option[DeleteBackupError] = throwable match {
      case e: BackupInUseException => Some(DeleteBackupError.BackupInUseExceptionCase(e))
      case e: BackupNotFoundException => Some(DeleteBackupError.BackupNotFoundExceptionCase(e))
      case e: InternalServerError => Some(DeleteBackupError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(DeleteBackupError.InvalidEndpointExceptionCase(e))
      case e: LimitExceededException => Some(DeleteBackupError.LimitExceededExceptionCase(e))
      case _ => None
    }
    def unliftError(e: DeleteBackupError) : Throwable = e match {
      case DeleteBackupError.BackupInUseExceptionCase(e) => e
      case DeleteBackupError.BackupNotFoundExceptionCase(e) => e
      case DeleteBackupError.InternalServerErrorCase(e) => e
      case DeleteBackupError.InvalidEndpointExceptionCase(e) => e
      case DeleteBackupError.LimitExceededExceptionCase(e) => e
    }
  }
  sealed trait DeleteBackupError extends scala.Product with scala.Serializable {
    @inline final def widen: DeleteBackupError = this
  }
  object DeleteBackupError extends ShapeTag.Companion[DeleteBackupError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DeleteBackupError")

    val hints : Hints = Hints.empty

    case class BackupInUseExceptionCase(backupInUseException: BackupInUseException) extends DeleteBackupError
    case class BackupNotFoundExceptionCase(backupNotFoundException: BackupNotFoundException) extends DeleteBackupError
    case class InternalServerErrorCase(internalServerError: InternalServerError) extends DeleteBackupError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends DeleteBackupError
    case class LimitExceededExceptionCase(limitExceededException: LimitExceededException) extends DeleteBackupError

    object BackupInUseExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[BackupInUseExceptionCase] = bijection(BackupInUseException.schema.addHints(hints), BackupInUseExceptionCase(_), _.backupInUseException)
      val alt = schema.oneOf[DeleteBackupError]("BackupInUseException")
    }
    object BackupNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[BackupNotFoundExceptionCase] = bijection(BackupNotFoundException.schema.addHints(hints), BackupNotFoundExceptionCase(_), _.backupNotFoundException)
      val alt = schema.oneOf[DeleteBackupError]("BackupNotFoundException")
    }
    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[DeleteBackupError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[DeleteBackupError]("InvalidEndpointException")
    }
    object LimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[LimitExceededExceptionCase] = bijection(LimitExceededException.schema.addHints(hints), LimitExceededExceptionCase(_), _.limitExceededException)
      val alt = schema.oneOf[DeleteBackupError]("LimitExceededException")
    }

    implicit val schema: UnionSchema[DeleteBackupError] = union(
      BackupInUseExceptionCase.alt,
      BackupNotFoundExceptionCase.alt,
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      LimitExceededExceptionCase.alt,
    ){
      case c : BackupInUseExceptionCase => BackupInUseExceptionCase.alt(c)
      case c : BackupNotFoundExceptionCase => BackupNotFoundExceptionCase.alt(c)
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : LimitExceededExceptionCase => LimitExceededExceptionCase.alt(c)
    }
  }
  case class DeleteItem(input: DeleteItemInput) extends DynamoDBOperation[DeleteItemInput, DynamoDBGen.DeleteItemError, DeleteItemOutput, Nothing, Nothing]
  object DeleteItem extends Endpoint[DynamoDBOperation, DeleteItemInput, DynamoDBGen.DeleteItemError, DeleteItemOutput, Nothing, Nothing] with Errorable[DeleteItemError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DeleteItem")
    val input: Schema[DeleteItemInput] = DeleteItemInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[DeleteItemOutput] = DeleteItemOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Deletes a single item in a table by primary key. You can perform a conditional delete\n            operation that deletes the item if it exists, or if it has an expected attribute\n            value.</p>\n        <p>In addition to deleting an item, you can also return the item\'s attribute values in\n            the same operation, using the <code>ReturnValues</code> parameter.</p>\n        <p>Unless you specify conditions, the <code>DeleteItem</code> is an idempotent operation;\n            running it multiple times on the same item or attribute does <i>not</i>\n            result in an error response.</p>\n        <p>Conditional deletes are useful for deleting items only if specific conditions are met.\n            If those conditions are met, DynamoDB performs the delete. Otherwise, the item is not\n            deleted.</p>"),
    )
    def wrap(input: DeleteItemInput) = DeleteItem(input)
    override val errorable: Option[Errorable[DeleteItemError]] = Some(this)
    val error: UnionSchema[DeleteItemError] = DeleteItemError.schema
    def liftError(throwable: Throwable) : Option[DeleteItemError] = throwable match {
      case e: ConditionalCheckFailedException => Some(DeleteItemError.ConditionalCheckFailedExceptionCase(e))
      case e: InternalServerError => Some(DeleteItemError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(DeleteItemError.InvalidEndpointExceptionCase(e))
      case e: ItemCollectionSizeLimitExceededException => Some(DeleteItemError.ItemCollectionSizeLimitExceededExceptionCase(e))
      case e: ProvisionedThroughputExceededException => Some(DeleteItemError.ProvisionedThroughputExceededExceptionCase(e))
      case e: RequestLimitExceeded => Some(DeleteItemError.RequestLimitExceededCase(e))
      case e: ResourceNotFoundException => Some(DeleteItemError.ResourceNotFoundExceptionCase(e))
      case e: TransactionConflictException => Some(DeleteItemError.TransactionConflictExceptionCase(e))
      case _ => None
    }
    def unliftError(e: DeleteItemError) : Throwable = e match {
      case DeleteItemError.ConditionalCheckFailedExceptionCase(e) => e
      case DeleteItemError.InternalServerErrorCase(e) => e
      case DeleteItemError.InvalidEndpointExceptionCase(e) => e
      case DeleteItemError.ItemCollectionSizeLimitExceededExceptionCase(e) => e
      case DeleteItemError.ProvisionedThroughputExceededExceptionCase(e) => e
      case DeleteItemError.RequestLimitExceededCase(e) => e
      case DeleteItemError.ResourceNotFoundExceptionCase(e) => e
      case DeleteItemError.TransactionConflictExceptionCase(e) => e
    }
  }
  sealed trait DeleteItemError extends scala.Product with scala.Serializable {
    @inline final def widen: DeleteItemError = this
  }
  object DeleteItemError extends ShapeTag.Companion[DeleteItemError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DeleteItemError")

    val hints : Hints = Hints.empty

    case class ConditionalCheckFailedExceptionCase(conditionalCheckFailedException: ConditionalCheckFailedException) extends DeleteItemError
    case class InternalServerErrorCase(internalServerError: InternalServerError) extends DeleteItemError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends DeleteItemError
    case class ItemCollectionSizeLimitExceededExceptionCase(itemCollectionSizeLimitExceededException: ItemCollectionSizeLimitExceededException) extends DeleteItemError
    case class ProvisionedThroughputExceededExceptionCase(provisionedThroughputExceededException: ProvisionedThroughputExceededException) extends DeleteItemError
    case class RequestLimitExceededCase(requestLimitExceeded: RequestLimitExceeded) extends DeleteItemError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends DeleteItemError
    case class TransactionConflictExceptionCase(transactionConflictException: TransactionConflictException) extends DeleteItemError

    object ConditionalCheckFailedExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ConditionalCheckFailedExceptionCase] = bijection(ConditionalCheckFailedException.schema.addHints(hints), ConditionalCheckFailedExceptionCase(_), _.conditionalCheckFailedException)
      val alt = schema.oneOf[DeleteItemError]("ConditionalCheckFailedException")
    }
    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[DeleteItemError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[DeleteItemError]("InvalidEndpointException")
    }
    object ItemCollectionSizeLimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ItemCollectionSizeLimitExceededExceptionCase] = bijection(ItemCollectionSizeLimitExceededException.schema.addHints(hints), ItemCollectionSizeLimitExceededExceptionCase(_), _.itemCollectionSizeLimitExceededException)
      val alt = schema.oneOf[DeleteItemError]("ItemCollectionSizeLimitExceededException")
    }
    object ProvisionedThroughputExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ProvisionedThroughputExceededExceptionCase] = bijection(ProvisionedThroughputExceededException.schema.addHints(hints), ProvisionedThroughputExceededExceptionCase(_), _.provisionedThroughputExceededException)
      val alt = schema.oneOf[DeleteItemError]("ProvisionedThroughputExceededException")
    }
    object RequestLimitExceededCase {
      val hints : Hints = Hints.empty
      val schema: Schema[RequestLimitExceededCase] = bijection(RequestLimitExceeded.schema.addHints(hints), RequestLimitExceededCase(_), _.requestLimitExceeded)
      val alt = schema.oneOf[DeleteItemError]("RequestLimitExceeded")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[DeleteItemError]("ResourceNotFoundException")
    }
    object TransactionConflictExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[TransactionConflictExceptionCase] = bijection(TransactionConflictException.schema.addHints(hints), TransactionConflictExceptionCase(_), _.transactionConflictException)
      val alt = schema.oneOf[DeleteItemError]("TransactionConflictException")
    }

    implicit val schema: UnionSchema[DeleteItemError] = union(
      ConditionalCheckFailedExceptionCase.alt,
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      ItemCollectionSizeLimitExceededExceptionCase.alt,
      ProvisionedThroughputExceededExceptionCase.alt,
      RequestLimitExceededCase.alt,
      ResourceNotFoundExceptionCase.alt,
      TransactionConflictExceptionCase.alt,
    ){
      case c : ConditionalCheckFailedExceptionCase => ConditionalCheckFailedExceptionCase.alt(c)
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : ItemCollectionSizeLimitExceededExceptionCase => ItemCollectionSizeLimitExceededExceptionCase.alt(c)
      case c : ProvisionedThroughputExceededExceptionCase => ProvisionedThroughputExceededExceptionCase.alt(c)
      case c : RequestLimitExceededCase => RequestLimitExceededCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
      case c : TransactionConflictExceptionCase => TransactionConflictExceptionCase.alt(c)
    }
  }
  case class DeleteTable(input: DeleteTableInput) extends DynamoDBOperation[DeleteTableInput, DynamoDBGen.DeleteTableError, DeleteTableOutput, Nothing, Nothing]
  object DeleteTable extends Endpoint[DynamoDBOperation, DeleteTableInput, DynamoDBGen.DeleteTableError, DeleteTableOutput, Nothing, Nothing] with Errorable[DeleteTableError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DeleteTable")
    val input: Schema[DeleteTableInput] = DeleteTableInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[DeleteTableOutput] = DeleteTableOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>The <code>DeleteTable</code> operation deletes a table and all of its items. After a\n                <code>DeleteTable</code> request, the specified table is in the\n                <code>DELETING</code> state until DynamoDB completes the deletion. If the table is\n            in the <code>ACTIVE</code> state, you can delete it. If a table is in\n                <code>CREATING</code> or <code>UPDATING</code> states, then DynamoDB returns a\n                <code>ResourceInUseException</code>. If the specified table does not exist, DynamoDB\n            returns a <code>ResourceNotFoundException</code>. If table is already in the\n                <code>DELETING</code> state, no error is returned. </p>\n        <note>\n            <p>DynamoDB might continue to accept data read and write operations, such as\n                    <code>GetItem</code> and <code>PutItem</code>, on a table in the\n                    <code>DELETING</code> state until the table deletion is complete.</p>\n        </note>\n        <p>When you delete a table, any indexes on that table are also deleted.</p>\n        <p>If you have DynamoDB Streams enabled on the table, then the corresponding stream on\n            that table goes into the <code>DISABLED</code> state, and the stream is automatically\n            deleted after 24 hours.</p>\n\n        <p>Use the <code>DescribeTable</code> action to check the status of the table. </p>"),
    )
    def wrap(input: DeleteTableInput) = DeleteTable(input)
    override val errorable: Option[Errorable[DeleteTableError]] = Some(this)
    val error: UnionSchema[DeleteTableError] = DeleteTableError.schema
    def liftError(throwable: Throwable) : Option[DeleteTableError] = throwable match {
      case e: InternalServerError => Some(DeleteTableError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(DeleteTableError.InvalidEndpointExceptionCase(e))
      case e: LimitExceededException => Some(DeleteTableError.LimitExceededExceptionCase(e))
      case e: ResourceInUseException => Some(DeleteTableError.ResourceInUseExceptionCase(e))
      case e: ResourceNotFoundException => Some(DeleteTableError.ResourceNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: DeleteTableError) : Throwable = e match {
      case DeleteTableError.InternalServerErrorCase(e) => e
      case DeleteTableError.InvalidEndpointExceptionCase(e) => e
      case DeleteTableError.LimitExceededExceptionCase(e) => e
      case DeleteTableError.ResourceInUseExceptionCase(e) => e
      case DeleteTableError.ResourceNotFoundExceptionCase(e) => e
    }
  }
  sealed trait DeleteTableError extends scala.Product with scala.Serializable {
    @inline final def widen: DeleteTableError = this
  }
  object DeleteTableError extends ShapeTag.Companion[DeleteTableError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DeleteTableError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends DeleteTableError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends DeleteTableError
    case class LimitExceededExceptionCase(limitExceededException: LimitExceededException) extends DeleteTableError
    case class ResourceInUseExceptionCase(resourceInUseException: ResourceInUseException) extends DeleteTableError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends DeleteTableError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[DeleteTableError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[DeleteTableError]("InvalidEndpointException")
    }
    object LimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[LimitExceededExceptionCase] = bijection(LimitExceededException.schema.addHints(hints), LimitExceededExceptionCase(_), _.limitExceededException)
      val alt = schema.oneOf[DeleteTableError]("LimitExceededException")
    }
    object ResourceInUseExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceInUseExceptionCase] = bijection(ResourceInUseException.schema.addHints(hints), ResourceInUseExceptionCase(_), _.resourceInUseException)
      val alt = schema.oneOf[DeleteTableError]("ResourceInUseException")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[DeleteTableError]("ResourceNotFoundException")
    }

    implicit val schema: UnionSchema[DeleteTableError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      LimitExceededExceptionCase.alt,
      ResourceInUseExceptionCase.alt,
      ResourceNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : LimitExceededExceptionCase => LimitExceededExceptionCase.alt(c)
      case c : ResourceInUseExceptionCase => ResourceInUseExceptionCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
    }
  }
  case class DescribeBackup(input: DescribeBackupInput) extends DynamoDBOperation[DescribeBackupInput, DynamoDBGen.DescribeBackupError, DescribeBackupOutput, Nothing, Nothing]
  object DescribeBackup extends Endpoint[DynamoDBOperation, DescribeBackupInput, DynamoDBGen.DescribeBackupError, DescribeBackupOutput, Nothing, Nothing] with Errorable[DescribeBackupError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeBackup")
    val input: Schema[DescribeBackupInput] = DescribeBackupInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[DescribeBackupOutput] = DescribeBackupOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Describes an existing backup of a table.</p>\n        <p>You can call <code>DescribeBackup</code> at a maximum rate of 10 times per\n            second.</p>"),
    )
    def wrap(input: DescribeBackupInput) = DescribeBackup(input)
    override val errorable: Option[Errorable[DescribeBackupError]] = Some(this)
    val error: UnionSchema[DescribeBackupError] = DescribeBackupError.schema
    def liftError(throwable: Throwable) : Option[DescribeBackupError] = throwable match {
      case e: BackupNotFoundException => Some(DescribeBackupError.BackupNotFoundExceptionCase(e))
      case e: InternalServerError => Some(DescribeBackupError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(DescribeBackupError.InvalidEndpointExceptionCase(e))
      case _ => None
    }
    def unliftError(e: DescribeBackupError) : Throwable = e match {
      case DescribeBackupError.BackupNotFoundExceptionCase(e) => e
      case DescribeBackupError.InternalServerErrorCase(e) => e
      case DescribeBackupError.InvalidEndpointExceptionCase(e) => e
    }
  }
  sealed trait DescribeBackupError extends scala.Product with scala.Serializable {
    @inline final def widen: DescribeBackupError = this
  }
  object DescribeBackupError extends ShapeTag.Companion[DescribeBackupError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeBackupError")

    val hints : Hints = Hints.empty

    case class BackupNotFoundExceptionCase(backupNotFoundException: BackupNotFoundException) extends DescribeBackupError
    case class InternalServerErrorCase(internalServerError: InternalServerError) extends DescribeBackupError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends DescribeBackupError

    object BackupNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[BackupNotFoundExceptionCase] = bijection(BackupNotFoundException.schema.addHints(hints), BackupNotFoundExceptionCase(_), _.backupNotFoundException)
      val alt = schema.oneOf[DescribeBackupError]("BackupNotFoundException")
    }
    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[DescribeBackupError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[DescribeBackupError]("InvalidEndpointException")
    }

    implicit val schema: UnionSchema[DescribeBackupError] = union(
      BackupNotFoundExceptionCase.alt,
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
    ){
      case c : BackupNotFoundExceptionCase => BackupNotFoundExceptionCase.alt(c)
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
    }
  }
  case class DescribeContinuousBackups(input: DescribeContinuousBackupsInput) extends DynamoDBOperation[DescribeContinuousBackupsInput, DynamoDBGen.DescribeContinuousBackupsError, DescribeContinuousBackupsOutput, Nothing, Nothing]
  object DescribeContinuousBackups extends Endpoint[DynamoDBOperation, DescribeContinuousBackupsInput, DynamoDBGen.DescribeContinuousBackupsError, DescribeContinuousBackupsOutput, Nothing, Nothing] with Errorable[DescribeContinuousBackupsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeContinuousBackups")
    val input: Schema[DescribeContinuousBackupsInput] = DescribeContinuousBackupsInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[DescribeContinuousBackupsOutput] = DescribeContinuousBackupsOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Checks the status of continuous backups and point in time recovery on the specified\n            table. Continuous backups are <code>ENABLED</code> on all tables at table creation. If\n            point in time recovery is enabled, <code>PointInTimeRecoveryStatus</code> will be set to\n            ENABLED.</p>\n        <p> After continuous backups and point in time recovery are enabled, you can restore to\n            any point in time within <code>EarliestRestorableDateTime</code> and\n                <code>LatestRestorableDateTime</code>. </p>\n        <p>\n            <code>LatestRestorableDateTime</code> is typically 5 minutes before the current time.\n            You can restore your table to any point in time during the last 35 days. </p>\n        <p>You can call <code>DescribeContinuousBackups</code> at a maximum rate of 10 times per\n            second.</p>"),
    )
    def wrap(input: DescribeContinuousBackupsInput) = DescribeContinuousBackups(input)
    override val errorable: Option[Errorable[DescribeContinuousBackupsError]] = Some(this)
    val error: UnionSchema[DescribeContinuousBackupsError] = DescribeContinuousBackupsError.schema
    def liftError(throwable: Throwable) : Option[DescribeContinuousBackupsError] = throwable match {
      case e: InternalServerError => Some(DescribeContinuousBackupsError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(DescribeContinuousBackupsError.InvalidEndpointExceptionCase(e))
      case e: TableNotFoundException => Some(DescribeContinuousBackupsError.TableNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: DescribeContinuousBackupsError) : Throwable = e match {
      case DescribeContinuousBackupsError.InternalServerErrorCase(e) => e
      case DescribeContinuousBackupsError.InvalidEndpointExceptionCase(e) => e
      case DescribeContinuousBackupsError.TableNotFoundExceptionCase(e) => e
    }
  }
  sealed trait DescribeContinuousBackupsError extends scala.Product with scala.Serializable {
    @inline final def widen: DescribeContinuousBackupsError = this
  }
  object DescribeContinuousBackupsError extends ShapeTag.Companion[DescribeContinuousBackupsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeContinuousBackupsError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends DescribeContinuousBackupsError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends DescribeContinuousBackupsError
    case class TableNotFoundExceptionCase(tableNotFoundException: TableNotFoundException) extends DescribeContinuousBackupsError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[DescribeContinuousBackupsError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[DescribeContinuousBackupsError]("InvalidEndpointException")
    }
    object TableNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[TableNotFoundExceptionCase] = bijection(TableNotFoundException.schema.addHints(hints), TableNotFoundExceptionCase(_), _.tableNotFoundException)
      val alt = schema.oneOf[DescribeContinuousBackupsError]("TableNotFoundException")
    }

    implicit val schema: UnionSchema[DescribeContinuousBackupsError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      TableNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : TableNotFoundExceptionCase => TableNotFoundExceptionCase.alt(c)
    }
  }
  case class DescribeContributorInsights(input: DescribeContributorInsightsInput) extends DynamoDBOperation[DescribeContributorInsightsInput, DynamoDBGen.DescribeContributorInsightsError, DescribeContributorInsightsOutput, Nothing, Nothing]
  object DescribeContributorInsights extends Endpoint[DynamoDBOperation, DescribeContributorInsightsInput, DynamoDBGen.DescribeContributorInsightsError, DescribeContributorInsightsOutput, Nothing, Nothing] with Errorable[DescribeContributorInsightsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeContributorInsights")
    val input: Schema[DescribeContributorInsightsInput] = DescribeContributorInsightsInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[DescribeContributorInsightsOutput] = DescribeContributorInsightsOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>Returns information about contributor insights, for a given table or global secondary\n            index.</p>"),
    )
    def wrap(input: DescribeContributorInsightsInput) = DescribeContributorInsights(input)
    override val errorable: Option[Errorable[DescribeContributorInsightsError]] = Some(this)
    val error: UnionSchema[DescribeContributorInsightsError] = DescribeContributorInsightsError.schema
    def liftError(throwable: Throwable) : Option[DescribeContributorInsightsError] = throwable match {
      case e: InternalServerError => Some(DescribeContributorInsightsError.InternalServerErrorCase(e))
      case e: ResourceNotFoundException => Some(DescribeContributorInsightsError.ResourceNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: DescribeContributorInsightsError) : Throwable = e match {
      case DescribeContributorInsightsError.InternalServerErrorCase(e) => e
      case DescribeContributorInsightsError.ResourceNotFoundExceptionCase(e) => e
    }
  }
  sealed trait DescribeContributorInsightsError extends scala.Product with scala.Serializable {
    @inline final def widen: DescribeContributorInsightsError = this
  }
  object DescribeContributorInsightsError extends ShapeTag.Companion[DescribeContributorInsightsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeContributorInsightsError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends DescribeContributorInsightsError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends DescribeContributorInsightsError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[DescribeContributorInsightsError]("InternalServerError")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[DescribeContributorInsightsError]("ResourceNotFoundException")
    }

    implicit val schema: UnionSchema[DescribeContributorInsightsError] = union(
      InternalServerErrorCase.alt,
      ResourceNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
    }
  }
  case class DescribeEndpoints(input: DescribeEndpointsRequest) extends DynamoDBOperation[DescribeEndpointsRequest, Nothing, DescribeEndpointsResponse, Nothing, Nothing]
  object DescribeEndpoints extends Endpoint[DynamoDBOperation, DescribeEndpointsRequest, Nothing, DescribeEndpointsResponse, Nothing, Nothing] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeEndpoints")
    val input: Schema[DescribeEndpointsRequest] = DescribeEndpointsRequest.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[DescribeEndpointsResponse] = DescribeEndpointsResponse.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>Returns the regional endpoint information.</p>"),
    )
    def wrap(input: DescribeEndpointsRequest) = DescribeEndpoints(input)
  }
  case class DescribeExport(input: DescribeExportInput) extends DynamoDBOperation[DescribeExportInput, DynamoDBGen.DescribeExportError, DescribeExportOutput, Nothing, Nothing]
  object DescribeExport extends Endpoint[DynamoDBOperation, DescribeExportInput, DynamoDBGen.DescribeExportError, DescribeExportOutput, Nothing, Nothing] with Errorable[DescribeExportError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeExport")
    val input: Schema[DescribeExportInput] = DescribeExportInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[DescribeExportOutput] = DescribeExportOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>Describes an existing table export.</p>"),
    )
    def wrap(input: DescribeExportInput) = DescribeExport(input)
    override val errorable: Option[Errorable[DescribeExportError]] = Some(this)
    val error: UnionSchema[DescribeExportError] = DescribeExportError.schema
    def liftError(throwable: Throwable) : Option[DescribeExportError] = throwable match {
      case e: ExportNotFoundException => Some(DescribeExportError.ExportNotFoundExceptionCase(e))
      case e: InternalServerError => Some(DescribeExportError.InternalServerErrorCase(e))
      case e: LimitExceededException => Some(DescribeExportError.LimitExceededExceptionCase(e))
      case _ => None
    }
    def unliftError(e: DescribeExportError) : Throwable = e match {
      case DescribeExportError.ExportNotFoundExceptionCase(e) => e
      case DescribeExportError.InternalServerErrorCase(e) => e
      case DescribeExportError.LimitExceededExceptionCase(e) => e
    }
  }
  sealed trait DescribeExportError extends scala.Product with scala.Serializable {
    @inline final def widen: DescribeExportError = this
  }
  object DescribeExportError extends ShapeTag.Companion[DescribeExportError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeExportError")

    val hints : Hints = Hints.empty

    case class ExportNotFoundExceptionCase(exportNotFoundException: ExportNotFoundException) extends DescribeExportError
    case class InternalServerErrorCase(internalServerError: InternalServerError) extends DescribeExportError
    case class LimitExceededExceptionCase(limitExceededException: LimitExceededException) extends DescribeExportError

    object ExportNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ExportNotFoundExceptionCase] = bijection(ExportNotFoundException.schema.addHints(hints), ExportNotFoundExceptionCase(_), _.exportNotFoundException)
      val alt = schema.oneOf[DescribeExportError]("ExportNotFoundException")
    }
    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[DescribeExportError]("InternalServerError")
    }
    object LimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[LimitExceededExceptionCase] = bijection(LimitExceededException.schema.addHints(hints), LimitExceededExceptionCase(_), _.limitExceededException)
      val alt = schema.oneOf[DescribeExportError]("LimitExceededException")
    }

    implicit val schema: UnionSchema[DescribeExportError] = union(
      ExportNotFoundExceptionCase.alt,
      InternalServerErrorCase.alt,
      LimitExceededExceptionCase.alt,
    ){
      case c : ExportNotFoundExceptionCase => ExportNotFoundExceptionCase.alt(c)
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : LimitExceededExceptionCase => LimitExceededExceptionCase.alt(c)
    }
  }
  case class DescribeGlobalTable(input: DescribeGlobalTableInput) extends DynamoDBOperation[DescribeGlobalTableInput, DynamoDBGen.DescribeGlobalTableError, DescribeGlobalTableOutput, Nothing, Nothing]
  object DescribeGlobalTable extends Endpoint[DynamoDBOperation, DescribeGlobalTableInput, DynamoDBGen.DescribeGlobalTableError, DescribeGlobalTableOutput, Nothing, Nothing] with Errorable[DescribeGlobalTableError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeGlobalTable")
    val input: Schema[DescribeGlobalTableInput] = DescribeGlobalTableInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[DescribeGlobalTableOutput] = DescribeGlobalTableOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Returns information about the specified global table.</p>\n        <note>\n            <p>This operation only applies to <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/globaltables.V1.html\">Version\n                    2017.11.29</a> of global tables. If you are using global tables <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/globaltables.V2.html\">Version\n                    2019.11.21</a> you can use <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/APIReference/API_DescribeTable.html\">DescribeTable</a> instead.</p>\n        </note>"),
    )
    def wrap(input: DescribeGlobalTableInput) = DescribeGlobalTable(input)
    override val errorable: Option[Errorable[DescribeGlobalTableError]] = Some(this)
    val error: UnionSchema[DescribeGlobalTableError] = DescribeGlobalTableError.schema
    def liftError(throwable: Throwable) : Option[DescribeGlobalTableError] = throwable match {
      case e: GlobalTableNotFoundException => Some(DescribeGlobalTableError.GlobalTableNotFoundExceptionCase(e))
      case e: InternalServerError => Some(DescribeGlobalTableError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(DescribeGlobalTableError.InvalidEndpointExceptionCase(e))
      case _ => None
    }
    def unliftError(e: DescribeGlobalTableError) : Throwable = e match {
      case DescribeGlobalTableError.GlobalTableNotFoundExceptionCase(e) => e
      case DescribeGlobalTableError.InternalServerErrorCase(e) => e
      case DescribeGlobalTableError.InvalidEndpointExceptionCase(e) => e
    }
  }
  sealed trait DescribeGlobalTableError extends scala.Product with scala.Serializable {
    @inline final def widen: DescribeGlobalTableError = this
  }
  object DescribeGlobalTableError extends ShapeTag.Companion[DescribeGlobalTableError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeGlobalTableError")

    val hints : Hints = Hints.empty

    case class GlobalTableNotFoundExceptionCase(globalTableNotFoundException: GlobalTableNotFoundException) extends DescribeGlobalTableError
    case class InternalServerErrorCase(internalServerError: InternalServerError) extends DescribeGlobalTableError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends DescribeGlobalTableError

    object GlobalTableNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[GlobalTableNotFoundExceptionCase] = bijection(GlobalTableNotFoundException.schema.addHints(hints), GlobalTableNotFoundExceptionCase(_), _.globalTableNotFoundException)
      val alt = schema.oneOf[DescribeGlobalTableError]("GlobalTableNotFoundException")
    }
    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[DescribeGlobalTableError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[DescribeGlobalTableError]("InvalidEndpointException")
    }

    implicit val schema: UnionSchema[DescribeGlobalTableError] = union(
      GlobalTableNotFoundExceptionCase.alt,
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
    ){
      case c : GlobalTableNotFoundExceptionCase => GlobalTableNotFoundExceptionCase.alt(c)
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
    }
  }
  case class DescribeGlobalTableSettings(input: DescribeGlobalTableSettingsInput) extends DynamoDBOperation[DescribeGlobalTableSettingsInput, DynamoDBGen.DescribeGlobalTableSettingsError, DescribeGlobalTableSettingsOutput, Nothing, Nothing]
  object DescribeGlobalTableSettings extends Endpoint[DynamoDBOperation, DescribeGlobalTableSettingsInput, DynamoDBGen.DescribeGlobalTableSettingsError, DescribeGlobalTableSettingsOutput, Nothing, Nothing] with Errorable[DescribeGlobalTableSettingsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeGlobalTableSettings")
    val input: Schema[DescribeGlobalTableSettingsInput] = DescribeGlobalTableSettingsInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[DescribeGlobalTableSettingsOutput] = DescribeGlobalTableSettingsOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Describes Region-specific settings for a global table.</p>\n        <note>\n            <p>This operation only applies to <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/globaltables.V1.html\">Version\n                    2017.11.29</a> of global tables.</p>\n        </note>"),
    )
    def wrap(input: DescribeGlobalTableSettingsInput) = DescribeGlobalTableSettings(input)
    override val errorable: Option[Errorable[DescribeGlobalTableSettingsError]] = Some(this)
    val error: UnionSchema[DescribeGlobalTableSettingsError] = DescribeGlobalTableSettingsError.schema
    def liftError(throwable: Throwable) : Option[DescribeGlobalTableSettingsError] = throwable match {
      case e: GlobalTableNotFoundException => Some(DescribeGlobalTableSettingsError.GlobalTableNotFoundExceptionCase(e))
      case e: InternalServerError => Some(DescribeGlobalTableSettingsError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(DescribeGlobalTableSettingsError.InvalidEndpointExceptionCase(e))
      case _ => None
    }
    def unliftError(e: DescribeGlobalTableSettingsError) : Throwable = e match {
      case DescribeGlobalTableSettingsError.GlobalTableNotFoundExceptionCase(e) => e
      case DescribeGlobalTableSettingsError.InternalServerErrorCase(e) => e
      case DescribeGlobalTableSettingsError.InvalidEndpointExceptionCase(e) => e
    }
  }
  sealed trait DescribeGlobalTableSettingsError extends scala.Product with scala.Serializable {
    @inline final def widen: DescribeGlobalTableSettingsError = this
  }
  object DescribeGlobalTableSettingsError extends ShapeTag.Companion[DescribeGlobalTableSettingsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeGlobalTableSettingsError")

    val hints : Hints = Hints.empty

    case class GlobalTableNotFoundExceptionCase(globalTableNotFoundException: GlobalTableNotFoundException) extends DescribeGlobalTableSettingsError
    case class InternalServerErrorCase(internalServerError: InternalServerError) extends DescribeGlobalTableSettingsError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends DescribeGlobalTableSettingsError

    object GlobalTableNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[GlobalTableNotFoundExceptionCase] = bijection(GlobalTableNotFoundException.schema.addHints(hints), GlobalTableNotFoundExceptionCase(_), _.globalTableNotFoundException)
      val alt = schema.oneOf[DescribeGlobalTableSettingsError]("GlobalTableNotFoundException")
    }
    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[DescribeGlobalTableSettingsError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[DescribeGlobalTableSettingsError]("InvalidEndpointException")
    }

    implicit val schema: UnionSchema[DescribeGlobalTableSettingsError] = union(
      GlobalTableNotFoundExceptionCase.alt,
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
    ){
      case c : GlobalTableNotFoundExceptionCase => GlobalTableNotFoundExceptionCase.alt(c)
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
    }
  }
  case class DescribeKinesisStreamingDestination(input: DescribeKinesisStreamingDestinationInput) extends DynamoDBOperation[DescribeKinesisStreamingDestinationInput, DynamoDBGen.DescribeKinesisStreamingDestinationError, DescribeKinesisStreamingDestinationOutput, Nothing, Nothing]
  object DescribeKinesisStreamingDestination extends Endpoint[DynamoDBOperation, DescribeKinesisStreamingDestinationInput, DynamoDBGen.DescribeKinesisStreamingDestinationError, DescribeKinesisStreamingDestinationOutput, Nothing, Nothing] with Errorable[DescribeKinesisStreamingDestinationError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeKinesisStreamingDestination")
    val input: Schema[DescribeKinesisStreamingDestinationInput] = DescribeKinesisStreamingDestinationInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[DescribeKinesisStreamingDestinationOutput] = DescribeKinesisStreamingDestinationOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Returns information about the status of Kinesis streaming.</p>"),
    )
    def wrap(input: DescribeKinesisStreamingDestinationInput) = DescribeKinesisStreamingDestination(input)
    override val errorable: Option[Errorable[DescribeKinesisStreamingDestinationError]] = Some(this)
    val error: UnionSchema[DescribeKinesisStreamingDestinationError] = DescribeKinesisStreamingDestinationError.schema
    def liftError(throwable: Throwable) : Option[DescribeKinesisStreamingDestinationError] = throwable match {
      case e: InternalServerError => Some(DescribeKinesisStreamingDestinationError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(DescribeKinesisStreamingDestinationError.InvalidEndpointExceptionCase(e))
      case e: ResourceNotFoundException => Some(DescribeKinesisStreamingDestinationError.ResourceNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: DescribeKinesisStreamingDestinationError) : Throwable = e match {
      case DescribeKinesisStreamingDestinationError.InternalServerErrorCase(e) => e
      case DescribeKinesisStreamingDestinationError.InvalidEndpointExceptionCase(e) => e
      case DescribeKinesisStreamingDestinationError.ResourceNotFoundExceptionCase(e) => e
    }
  }
  sealed trait DescribeKinesisStreamingDestinationError extends scala.Product with scala.Serializable {
    @inline final def widen: DescribeKinesisStreamingDestinationError = this
  }
  object DescribeKinesisStreamingDestinationError extends ShapeTag.Companion[DescribeKinesisStreamingDestinationError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeKinesisStreamingDestinationError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends DescribeKinesisStreamingDestinationError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends DescribeKinesisStreamingDestinationError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends DescribeKinesisStreamingDestinationError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[DescribeKinesisStreamingDestinationError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[DescribeKinesisStreamingDestinationError]("InvalidEndpointException")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[DescribeKinesisStreamingDestinationError]("ResourceNotFoundException")
    }

    implicit val schema: UnionSchema[DescribeKinesisStreamingDestinationError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      ResourceNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
    }
  }
  case class DescribeLimits(input: DescribeLimitsInput) extends DynamoDBOperation[DescribeLimitsInput, DynamoDBGen.DescribeLimitsError, DescribeLimitsOutput, Nothing, Nothing]
  object DescribeLimits extends Endpoint[DynamoDBOperation, DescribeLimitsInput, DynamoDBGen.DescribeLimitsError, DescribeLimitsOutput, Nothing, Nothing] with Errorable[DescribeLimitsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeLimits")
    val input: Schema[DescribeLimitsInput] = DescribeLimitsInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[DescribeLimitsOutput] = DescribeLimitsOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Returns the current provisioned-capacity quotas for your Amazon Web Services account in\n            a Region, both for the Region as a whole and for any one DynamoDB table that you create\n            there.</p>\n        <p>When you establish an Amazon Web Services account, the account has initial quotas on\n            the maximum read capacity units and write capacity units that you can provision across\n            all of your DynamoDB tables in a given Region. Also, there are per-table\n            quotas that apply when you create a table there. For more information, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Limits.html\">Service,\n                Account, and Table Quotas</a> page in the <i>Amazon DynamoDB\n                Developer Guide</i>.</p>\n\n        <p>Although you can increase these quotas by filing a case at <a href=\"https://console.aws.amazon.com/support/home#/\">Amazon Web Services Support Center</a>, obtaining the\n            increase is not instantaneous. The <code>DescribeLimits</code> action lets you write\n            code to compare the capacity you are currently using to those quotas imposed by your\n            account so that you have enough time to apply for an increase before you hit a\n            quota.</p>\n\n        <p>For example, you could use one of the Amazon Web Services SDKs to do the\n            following:</p>\n\n        <ol>\n            <li>\n                <p>Call <code>DescribeLimits</code> for a particular Region to obtain your\n                    current account quotas on provisioned capacity there.</p>\n            </li>\n            <li>\n                <p>Create a variable to hold the aggregate read capacity units provisioned for\n                    all your tables in that Region, and one to hold the aggregate write capacity\n                    units. Zero them both.</p>\n            </li>\n            <li>\n                <p>Call <code>ListTables</code> to obtain a list of all your DynamoDB\n                    tables.</p>\n            </li>\n            <li>\n                <p>For each table name listed by <code>ListTables</code>, do the\n                    following:</p>\n                <ul>\n                  <li>\n                        <p>Call <code>DescribeTable</code> with the table name.</p>\n                    </li>\n                  <li>\n                        <p>Use the data returned by <code>DescribeTable</code> to add the read\n                            capacity units and write capacity units provisioned for the table itself\n                            to your variables.</p>\n                    </li>\n                  <li>\n                        <p>If the table has one or more global secondary indexes (GSIs), loop\n                            over these GSIs and add their provisioned capacity values to your\n                            variables as well.</p>\n                    </li>\n               </ul>\n            </li>\n            <li>\n                <p>Report the account quotas for that Region returned by\n                        <code>DescribeLimits</code>, along with the total current provisioned\n                    capacity levels you have calculated.</p>\n            </li>\n         </ol>\n\n        <p>This will let you see whether you are getting close to your account-level\n            quotas.</p>\n        <p>The per-table quotas apply only when you are creating a new table. They restrict the\n            sum of the provisioned capacity of the new table itself and all its global secondary\n            indexes.</p>\n        <p>For existing tables and their GSIs, DynamoDB doesn\'t let you increase provisioned\n            capacity extremely rapidly, but the only quota that applies is that the aggregate\n            provisioned capacity over all your tables and GSIs cannot exceed either of the\n            per-account quotas.</p>\n        <note>\n            <p>\n                <code>DescribeLimits</code> should only be called periodically. You can expect\n                throttling errors if you call it more than once in a minute.</p>\n        </note>\n        <p>The <code>DescribeLimits</code> Request element has no content.</p>"),
    )
    def wrap(input: DescribeLimitsInput) = DescribeLimits(input)
    override val errorable: Option[Errorable[DescribeLimitsError]] = Some(this)
    val error: UnionSchema[DescribeLimitsError] = DescribeLimitsError.schema
    def liftError(throwable: Throwable) : Option[DescribeLimitsError] = throwable match {
      case e: InternalServerError => Some(DescribeLimitsError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(DescribeLimitsError.InvalidEndpointExceptionCase(e))
      case _ => None
    }
    def unliftError(e: DescribeLimitsError) : Throwable = e match {
      case DescribeLimitsError.InternalServerErrorCase(e) => e
      case DescribeLimitsError.InvalidEndpointExceptionCase(e) => e
    }
  }
  sealed trait DescribeLimitsError extends scala.Product with scala.Serializable {
    @inline final def widen: DescribeLimitsError = this
  }
  object DescribeLimitsError extends ShapeTag.Companion[DescribeLimitsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeLimitsError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends DescribeLimitsError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends DescribeLimitsError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[DescribeLimitsError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[DescribeLimitsError]("InvalidEndpointException")
    }

    implicit val schema: UnionSchema[DescribeLimitsError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
    }
  }
  case class DescribeTable(input: DescribeTableInput) extends DynamoDBOperation[DescribeTableInput, DynamoDBGen.DescribeTableError, DescribeTableOutput, Nothing, Nothing]
  object DescribeTable extends Endpoint[DynamoDBOperation, DescribeTableInput, DynamoDBGen.DescribeTableError, DescribeTableOutput, Nothing, Nothing] with Errorable[DescribeTableError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeTable")
    val input: Schema[DescribeTableInput] = DescribeTableInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[DescribeTableOutput] = DescribeTableOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      smithy.waiters.Waitable(Map(smithy.waiters.WaiterName("TableExists") -> smithy.waiters.Waiter(acceptors = List(smithy.waiters.Acceptor(state = smithy.waiters.AcceptorState.SUCCESS.widen, matcher = smithy.waiters.Matcher.OutputCase(smithy.waiters.PathMatcher(path = "Table.TableStatus", expected = "ACTIVE", comparator = smithy.waiters.PathComparator.STRING_EQUALS.widen)).widen), smithy.waiters.Acceptor(state = smithy.waiters.AcceptorState.RETRY.widen, matcher = smithy.waiters.Matcher.ErrorTypeCase("ResourceNotFoundException").widen)), minDelay = smithy.waiters.WaiterDelay(20), maxDelay = smithy.waiters.WaiterDelay(120), documentation = None, deprecated = None, tags = None), smithy.waiters.WaiterName("TableNotExists") -> smithy.waiters.Waiter(acceptors = List(smithy.waiters.Acceptor(state = smithy.waiters.AcceptorState.SUCCESS.widen, matcher = smithy.waiters.Matcher.ErrorTypeCase("ResourceNotFoundException").widen)), minDelay = smithy.waiters.WaiterDelay(20), maxDelay = smithy.waiters.WaiterDelay(120), documentation = None, deprecated = None, tags = None))),
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Returns information about the table, including the current status of the table, when\n            it was created, the primary key schema, and any indexes on the table.</p>\n        <note>\n            <p>If you issue a <code>DescribeTable</code> request immediately after a\n                    <code>CreateTable</code> request, DynamoDB might return a\n                    <code>ResourceNotFoundException</code>. This is because\n                    <code>DescribeTable</code> uses an eventually consistent query, and the metadata\n                for your table might not be available at that moment. Wait for a few seconds, and\n                then try the <code>DescribeTable</code> request again.</p>\n        </note>"),
    )
    def wrap(input: DescribeTableInput) = DescribeTable(input)
    override val errorable: Option[Errorable[DescribeTableError]] = Some(this)
    val error: UnionSchema[DescribeTableError] = DescribeTableError.schema
    def liftError(throwable: Throwable) : Option[DescribeTableError] = throwable match {
      case e: InternalServerError => Some(DescribeTableError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(DescribeTableError.InvalidEndpointExceptionCase(e))
      case e: ResourceNotFoundException => Some(DescribeTableError.ResourceNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: DescribeTableError) : Throwable = e match {
      case DescribeTableError.InternalServerErrorCase(e) => e
      case DescribeTableError.InvalidEndpointExceptionCase(e) => e
      case DescribeTableError.ResourceNotFoundExceptionCase(e) => e
    }
  }
  sealed trait DescribeTableError extends scala.Product with scala.Serializable {
    @inline final def widen: DescribeTableError = this
  }
  object DescribeTableError extends ShapeTag.Companion[DescribeTableError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeTableError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends DescribeTableError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends DescribeTableError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends DescribeTableError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[DescribeTableError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[DescribeTableError]("InvalidEndpointException")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[DescribeTableError]("ResourceNotFoundException")
    }

    implicit val schema: UnionSchema[DescribeTableError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      ResourceNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
    }
  }
  case class DescribeTableReplicaAutoScaling(input: DescribeTableReplicaAutoScalingInput) extends DynamoDBOperation[DescribeTableReplicaAutoScalingInput, DynamoDBGen.DescribeTableReplicaAutoScalingError, DescribeTableReplicaAutoScalingOutput, Nothing, Nothing]
  object DescribeTableReplicaAutoScaling extends Endpoint[DynamoDBOperation, DescribeTableReplicaAutoScalingInput, DynamoDBGen.DescribeTableReplicaAutoScalingError, DescribeTableReplicaAutoScalingOutput, Nothing, Nothing] with Errorable[DescribeTableReplicaAutoScalingError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeTableReplicaAutoScaling")
    val input: Schema[DescribeTableReplicaAutoScalingInput] = DescribeTableReplicaAutoScalingInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[DescribeTableReplicaAutoScalingOutput] = DescribeTableReplicaAutoScalingOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>Describes auto scaling settings across replicas of the global table at once.</p>\n        <note>\n            <p>This operation only applies to <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/globaltables.V2.html\">Version\n                    2019.11.21</a> of global tables.</p>\n        </note>"),
    )
    def wrap(input: DescribeTableReplicaAutoScalingInput) = DescribeTableReplicaAutoScaling(input)
    override val errorable: Option[Errorable[DescribeTableReplicaAutoScalingError]] = Some(this)
    val error: UnionSchema[DescribeTableReplicaAutoScalingError] = DescribeTableReplicaAutoScalingError.schema
    def liftError(throwable: Throwable) : Option[DescribeTableReplicaAutoScalingError] = throwable match {
      case e: InternalServerError => Some(DescribeTableReplicaAutoScalingError.InternalServerErrorCase(e))
      case e: ResourceNotFoundException => Some(DescribeTableReplicaAutoScalingError.ResourceNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: DescribeTableReplicaAutoScalingError) : Throwable = e match {
      case DescribeTableReplicaAutoScalingError.InternalServerErrorCase(e) => e
      case DescribeTableReplicaAutoScalingError.ResourceNotFoundExceptionCase(e) => e
    }
  }
  sealed trait DescribeTableReplicaAutoScalingError extends scala.Product with scala.Serializable {
    @inline final def widen: DescribeTableReplicaAutoScalingError = this
  }
  object DescribeTableReplicaAutoScalingError extends ShapeTag.Companion[DescribeTableReplicaAutoScalingError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeTableReplicaAutoScalingError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends DescribeTableReplicaAutoScalingError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends DescribeTableReplicaAutoScalingError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[DescribeTableReplicaAutoScalingError]("InternalServerError")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[DescribeTableReplicaAutoScalingError]("ResourceNotFoundException")
    }

    implicit val schema: UnionSchema[DescribeTableReplicaAutoScalingError] = union(
      InternalServerErrorCase.alt,
      ResourceNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
    }
  }
  case class DescribeTimeToLive(input: DescribeTimeToLiveInput) extends DynamoDBOperation[DescribeTimeToLiveInput, DynamoDBGen.DescribeTimeToLiveError, DescribeTimeToLiveOutput, Nothing, Nothing]
  object DescribeTimeToLive extends Endpoint[DynamoDBOperation, DescribeTimeToLiveInput, DynamoDBGen.DescribeTimeToLiveError, DescribeTimeToLiveOutput, Nothing, Nothing] with Errorable[DescribeTimeToLiveError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeTimeToLive")
    val input: Schema[DescribeTimeToLiveInput] = DescribeTimeToLiveInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[DescribeTimeToLiveOutput] = DescribeTimeToLiveOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Gives a description of the Time to Live (TTL) status on the specified table. </p>"),
    )
    def wrap(input: DescribeTimeToLiveInput) = DescribeTimeToLive(input)
    override val errorable: Option[Errorable[DescribeTimeToLiveError]] = Some(this)
    val error: UnionSchema[DescribeTimeToLiveError] = DescribeTimeToLiveError.schema
    def liftError(throwable: Throwable) : Option[DescribeTimeToLiveError] = throwable match {
      case e: InternalServerError => Some(DescribeTimeToLiveError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(DescribeTimeToLiveError.InvalidEndpointExceptionCase(e))
      case e: ResourceNotFoundException => Some(DescribeTimeToLiveError.ResourceNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: DescribeTimeToLiveError) : Throwable = e match {
      case DescribeTimeToLiveError.InternalServerErrorCase(e) => e
      case DescribeTimeToLiveError.InvalidEndpointExceptionCase(e) => e
      case DescribeTimeToLiveError.ResourceNotFoundExceptionCase(e) => e
    }
  }
  sealed trait DescribeTimeToLiveError extends scala.Product with scala.Serializable {
    @inline final def widen: DescribeTimeToLiveError = this
  }
  object DescribeTimeToLiveError extends ShapeTag.Companion[DescribeTimeToLiveError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeTimeToLiveError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends DescribeTimeToLiveError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends DescribeTimeToLiveError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends DescribeTimeToLiveError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[DescribeTimeToLiveError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[DescribeTimeToLiveError]("InvalidEndpointException")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[DescribeTimeToLiveError]("ResourceNotFoundException")
    }

    implicit val schema: UnionSchema[DescribeTimeToLiveError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      ResourceNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
    }
  }
  case class DisableKinesisStreamingDestination(input: KinesisStreamingDestinationInput) extends DynamoDBOperation[KinesisStreamingDestinationInput, DynamoDBGen.DisableKinesisStreamingDestinationError, KinesisStreamingDestinationOutput, Nothing, Nothing]
  object DisableKinesisStreamingDestination extends Endpoint[DynamoDBOperation, KinesisStreamingDestinationInput, DynamoDBGen.DisableKinesisStreamingDestinationError, KinesisStreamingDestinationOutput, Nothing, Nothing] with Errorable[DisableKinesisStreamingDestinationError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DisableKinesisStreamingDestination")
    val input: Schema[KinesisStreamingDestinationInput] = KinesisStreamingDestinationInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[KinesisStreamingDestinationOutput] = KinesisStreamingDestinationOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Stops replication from the DynamoDB table to the Kinesis data stream. This is done\n            without deleting either of the resources.</p>"),
    )
    def wrap(input: KinesisStreamingDestinationInput) = DisableKinesisStreamingDestination(input)
    override val errorable: Option[Errorable[DisableKinesisStreamingDestinationError]] = Some(this)
    val error: UnionSchema[DisableKinesisStreamingDestinationError] = DisableKinesisStreamingDestinationError.schema
    def liftError(throwable: Throwable) : Option[DisableKinesisStreamingDestinationError] = throwable match {
      case e: InternalServerError => Some(DisableKinesisStreamingDestinationError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(DisableKinesisStreamingDestinationError.InvalidEndpointExceptionCase(e))
      case e: LimitExceededException => Some(DisableKinesisStreamingDestinationError.LimitExceededExceptionCase(e))
      case e: ResourceInUseException => Some(DisableKinesisStreamingDestinationError.ResourceInUseExceptionCase(e))
      case e: ResourceNotFoundException => Some(DisableKinesisStreamingDestinationError.ResourceNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: DisableKinesisStreamingDestinationError) : Throwable = e match {
      case DisableKinesisStreamingDestinationError.InternalServerErrorCase(e) => e
      case DisableKinesisStreamingDestinationError.InvalidEndpointExceptionCase(e) => e
      case DisableKinesisStreamingDestinationError.LimitExceededExceptionCase(e) => e
      case DisableKinesisStreamingDestinationError.ResourceInUseExceptionCase(e) => e
      case DisableKinesisStreamingDestinationError.ResourceNotFoundExceptionCase(e) => e
    }
  }
  sealed trait DisableKinesisStreamingDestinationError extends scala.Product with scala.Serializable {
    @inline final def widen: DisableKinesisStreamingDestinationError = this
  }
  object DisableKinesisStreamingDestinationError extends ShapeTag.Companion[DisableKinesisStreamingDestinationError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DisableKinesisStreamingDestinationError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends DisableKinesisStreamingDestinationError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends DisableKinesisStreamingDestinationError
    case class LimitExceededExceptionCase(limitExceededException: LimitExceededException) extends DisableKinesisStreamingDestinationError
    case class ResourceInUseExceptionCase(resourceInUseException: ResourceInUseException) extends DisableKinesisStreamingDestinationError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends DisableKinesisStreamingDestinationError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[DisableKinesisStreamingDestinationError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[DisableKinesisStreamingDestinationError]("InvalidEndpointException")
    }
    object LimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[LimitExceededExceptionCase] = bijection(LimitExceededException.schema.addHints(hints), LimitExceededExceptionCase(_), _.limitExceededException)
      val alt = schema.oneOf[DisableKinesisStreamingDestinationError]("LimitExceededException")
    }
    object ResourceInUseExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceInUseExceptionCase] = bijection(ResourceInUseException.schema.addHints(hints), ResourceInUseExceptionCase(_), _.resourceInUseException)
      val alt = schema.oneOf[DisableKinesisStreamingDestinationError]("ResourceInUseException")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[DisableKinesisStreamingDestinationError]("ResourceNotFoundException")
    }

    implicit val schema: UnionSchema[DisableKinesisStreamingDestinationError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      LimitExceededExceptionCase.alt,
      ResourceInUseExceptionCase.alt,
      ResourceNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : LimitExceededExceptionCase => LimitExceededExceptionCase.alt(c)
      case c : ResourceInUseExceptionCase => ResourceInUseExceptionCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
    }
  }
  case class EnableKinesisStreamingDestination(input: KinesisStreamingDestinationInput) extends DynamoDBOperation[KinesisStreamingDestinationInput, DynamoDBGen.EnableKinesisStreamingDestinationError, KinesisStreamingDestinationOutput, Nothing, Nothing]
  object EnableKinesisStreamingDestination extends Endpoint[DynamoDBOperation, KinesisStreamingDestinationInput, DynamoDBGen.EnableKinesisStreamingDestinationError, KinesisStreamingDestinationOutput, Nothing, Nothing] with Errorable[EnableKinesisStreamingDestinationError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "EnableKinesisStreamingDestination")
    val input: Schema[KinesisStreamingDestinationInput] = KinesisStreamingDestinationInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[KinesisStreamingDestinationOutput] = KinesisStreamingDestinationOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Starts table data replication to the specified Kinesis data stream at a timestamp\n            chosen during the enable workflow. If this operation doesn\'t return results immediately,\n            use DescribeKinesisStreamingDestination to check if streaming to the Kinesis data stream\n            is ACTIVE.</p>"),
    )
    def wrap(input: KinesisStreamingDestinationInput) = EnableKinesisStreamingDestination(input)
    override val errorable: Option[Errorable[EnableKinesisStreamingDestinationError]] = Some(this)
    val error: UnionSchema[EnableKinesisStreamingDestinationError] = EnableKinesisStreamingDestinationError.schema
    def liftError(throwable: Throwable) : Option[EnableKinesisStreamingDestinationError] = throwable match {
      case e: InternalServerError => Some(EnableKinesisStreamingDestinationError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(EnableKinesisStreamingDestinationError.InvalidEndpointExceptionCase(e))
      case e: LimitExceededException => Some(EnableKinesisStreamingDestinationError.LimitExceededExceptionCase(e))
      case e: ResourceInUseException => Some(EnableKinesisStreamingDestinationError.ResourceInUseExceptionCase(e))
      case e: ResourceNotFoundException => Some(EnableKinesisStreamingDestinationError.ResourceNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: EnableKinesisStreamingDestinationError) : Throwable = e match {
      case EnableKinesisStreamingDestinationError.InternalServerErrorCase(e) => e
      case EnableKinesisStreamingDestinationError.InvalidEndpointExceptionCase(e) => e
      case EnableKinesisStreamingDestinationError.LimitExceededExceptionCase(e) => e
      case EnableKinesisStreamingDestinationError.ResourceInUseExceptionCase(e) => e
      case EnableKinesisStreamingDestinationError.ResourceNotFoundExceptionCase(e) => e
    }
  }
  sealed trait EnableKinesisStreamingDestinationError extends scala.Product with scala.Serializable {
    @inline final def widen: EnableKinesisStreamingDestinationError = this
  }
  object EnableKinesisStreamingDestinationError extends ShapeTag.Companion[EnableKinesisStreamingDestinationError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "EnableKinesisStreamingDestinationError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends EnableKinesisStreamingDestinationError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends EnableKinesisStreamingDestinationError
    case class LimitExceededExceptionCase(limitExceededException: LimitExceededException) extends EnableKinesisStreamingDestinationError
    case class ResourceInUseExceptionCase(resourceInUseException: ResourceInUseException) extends EnableKinesisStreamingDestinationError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends EnableKinesisStreamingDestinationError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[EnableKinesisStreamingDestinationError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[EnableKinesisStreamingDestinationError]("InvalidEndpointException")
    }
    object LimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[LimitExceededExceptionCase] = bijection(LimitExceededException.schema.addHints(hints), LimitExceededExceptionCase(_), _.limitExceededException)
      val alt = schema.oneOf[EnableKinesisStreamingDestinationError]("LimitExceededException")
    }
    object ResourceInUseExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceInUseExceptionCase] = bijection(ResourceInUseException.schema.addHints(hints), ResourceInUseExceptionCase(_), _.resourceInUseException)
      val alt = schema.oneOf[EnableKinesisStreamingDestinationError]("ResourceInUseException")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[EnableKinesisStreamingDestinationError]("ResourceNotFoundException")
    }

    implicit val schema: UnionSchema[EnableKinesisStreamingDestinationError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      LimitExceededExceptionCase.alt,
      ResourceInUseExceptionCase.alt,
      ResourceNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : LimitExceededExceptionCase => LimitExceededExceptionCase.alt(c)
      case c : ResourceInUseExceptionCase => ResourceInUseExceptionCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
    }
  }
  case class ExecuteStatement(input: ExecuteStatementInput) extends DynamoDBOperation[ExecuteStatementInput, DynamoDBGen.ExecuteStatementError, ExecuteStatementOutput, Nothing, Nothing]
  object ExecuteStatement extends Endpoint[DynamoDBOperation, ExecuteStatementInput, DynamoDBGen.ExecuteStatementError, ExecuteStatementOutput, Nothing, Nothing] with Errorable[ExecuteStatementError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExecuteStatement")
    val input: Schema[ExecuteStatementInput] = ExecuteStatementInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[ExecuteStatementOutput] = ExecuteStatementOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>This operation allows you to perform reads and singleton writes on data stored in\n            DynamoDB, using PartiQL.</p>\n        <p>For PartiQL reads (<code>SELECT</code> statement), if the total number of processed\n            items exceeds the maximum dataset size limit of 1 MB, the read stops and results are\n            returned to the user as a <code>LastEvaluatedKey</code> value to continue the read in a\n            subsequent operation. If the filter criteria in <code>WHERE</code> clause does not match\n            any data, the read will return an empty result set.</p>\n        <p>A single <code>SELECT</code> statement response can return up to the maximum number of\n            items (if using the Limit parameter) or a maximum of 1 MB of data (and then apply any\n            filtering to the results using <code>WHERE</code> clause). If\n                <code>LastEvaluatedKey</code> is present in the response, you need to paginate the\n            result set.</p>"),
    )
    def wrap(input: ExecuteStatementInput) = ExecuteStatement(input)
    override val errorable: Option[Errorable[ExecuteStatementError]] = Some(this)
    val error: UnionSchema[ExecuteStatementError] = ExecuteStatementError.schema
    def liftError(throwable: Throwable) : Option[ExecuteStatementError] = throwable match {
      case e: ConditionalCheckFailedException => Some(ExecuteStatementError.ConditionalCheckFailedExceptionCase(e))
      case e: DuplicateItemException => Some(ExecuteStatementError.DuplicateItemExceptionCase(e))
      case e: InternalServerError => Some(ExecuteStatementError.InternalServerErrorCase(e))
      case e: ItemCollectionSizeLimitExceededException => Some(ExecuteStatementError.ItemCollectionSizeLimitExceededExceptionCase(e))
      case e: ProvisionedThroughputExceededException => Some(ExecuteStatementError.ProvisionedThroughputExceededExceptionCase(e))
      case e: RequestLimitExceeded => Some(ExecuteStatementError.RequestLimitExceededCase(e))
      case e: ResourceNotFoundException => Some(ExecuteStatementError.ResourceNotFoundExceptionCase(e))
      case e: TransactionConflictException => Some(ExecuteStatementError.TransactionConflictExceptionCase(e))
      case _ => None
    }
    def unliftError(e: ExecuteStatementError) : Throwable = e match {
      case ExecuteStatementError.ConditionalCheckFailedExceptionCase(e) => e
      case ExecuteStatementError.DuplicateItemExceptionCase(e) => e
      case ExecuteStatementError.InternalServerErrorCase(e) => e
      case ExecuteStatementError.ItemCollectionSizeLimitExceededExceptionCase(e) => e
      case ExecuteStatementError.ProvisionedThroughputExceededExceptionCase(e) => e
      case ExecuteStatementError.RequestLimitExceededCase(e) => e
      case ExecuteStatementError.ResourceNotFoundExceptionCase(e) => e
      case ExecuteStatementError.TransactionConflictExceptionCase(e) => e
    }
  }
  sealed trait ExecuteStatementError extends scala.Product with scala.Serializable {
    @inline final def widen: ExecuteStatementError = this
  }
  object ExecuteStatementError extends ShapeTag.Companion[ExecuteStatementError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExecuteStatementError")

    val hints : Hints = Hints.empty

    case class ConditionalCheckFailedExceptionCase(conditionalCheckFailedException: ConditionalCheckFailedException) extends ExecuteStatementError
    case class DuplicateItemExceptionCase(duplicateItemException: DuplicateItemException) extends ExecuteStatementError
    case class InternalServerErrorCase(internalServerError: InternalServerError) extends ExecuteStatementError
    case class ItemCollectionSizeLimitExceededExceptionCase(itemCollectionSizeLimitExceededException: ItemCollectionSizeLimitExceededException) extends ExecuteStatementError
    case class ProvisionedThroughputExceededExceptionCase(provisionedThroughputExceededException: ProvisionedThroughputExceededException) extends ExecuteStatementError
    case class RequestLimitExceededCase(requestLimitExceeded: RequestLimitExceeded) extends ExecuteStatementError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends ExecuteStatementError
    case class TransactionConflictExceptionCase(transactionConflictException: TransactionConflictException) extends ExecuteStatementError

    object ConditionalCheckFailedExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ConditionalCheckFailedExceptionCase] = bijection(ConditionalCheckFailedException.schema.addHints(hints), ConditionalCheckFailedExceptionCase(_), _.conditionalCheckFailedException)
      val alt = schema.oneOf[ExecuteStatementError]("ConditionalCheckFailedException")
    }
    object DuplicateItemExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[DuplicateItemExceptionCase] = bijection(DuplicateItemException.schema.addHints(hints), DuplicateItemExceptionCase(_), _.duplicateItemException)
      val alt = schema.oneOf[ExecuteStatementError]("DuplicateItemException")
    }
    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[ExecuteStatementError]("InternalServerError")
    }
    object ItemCollectionSizeLimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ItemCollectionSizeLimitExceededExceptionCase] = bijection(ItemCollectionSizeLimitExceededException.schema.addHints(hints), ItemCollectionSizeLimitExceededExceptionCase(_), _.itemCollectionSizeLimitExceededException)
      val alt = schema.oneOf[ExecuteStatementError]("ItemCollectionSizeLimitExceededException")
    }
    object ProvisionedThroughputExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ProvisionedThroughputExceededExceptionCase] = bijection(ProvisionedThroughputExceededException.schema.addHints(hints), ProvisionedThroughputExceededExceptionCase(_), _.provisionedThroughputExceededException)
      val alt = schema.oneOf[ExecuteStatementError]("ProvisionedThroughputExceededException")
    }
    object RequestLimitExceededCase {
      val hints : Hints = Hints.empty
      val schema: Schema[RequestLimitExceededCase] = bijection(RequestLimitExceeded.schema.addHints(hints), RequestLimitExceededCase(_), _.requestLimitExceeded)
      val alt = schema.oneOf[ExecuteStatementError]("RequestLimitExceeded")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[ExecuteStatementError]("ResourceNotFoundException")
    }
    object TransactionConflictExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[TransactionConflictExceptionCase] = bijection(TransactionConflictException.schema.addHints(hints), TransactionConflictExceptionCase(_), _.transactionConflictException)
      val alt = schema.oneOf[ExecuteStatementError]("TransactionConflictException")
    }

    implicit val schema: UnionSchema[ExecuteStatementError] = union(
      ConditionalCheckFailedExceptionCase.alt,
      DuplicateItemExceptionCase.alt,
      InternalServerErrorCase.alt,
      ItemCollectionSizeLimitExceededExceptionCase.alt,
      ProvisionedThroughputExceededExceptionCase.alt,
      RequestLimitExceededCase.alt,
      ResourceNotFoundExceptionCase.alt,
      TransactionConflictExceptionCase.alt,
    ){
      case c : ConditionalCheckFailedExceptionCase => ConditionalCheckFailedExceptionCase.alt(c)
      case c : DuplicateItemExceptionCase => DuplicateItemExceptionCase.alt(c)
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : ItemCollectionSizeLimitExceededExceptionCase => ItemCollectionSizeLimitExceededExceptionCase.alt(c)
      case c : ProvisionedThroughputExceededExceptionCase => ProvisionedThroughputExceededExceptionCase.alt(c)
      case c : RequestLimitExceededCase => RequestLimitExceededCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
      case c : TransactionConflictExceptionCase => TransactionConflictExceptionCase.alt(c)
    }
  }
  case class ExecuteTransaction(input: ExecuteTransactionInput) extends DynamoDBOperation[ExecuteTransactionInput, DynamoDBGen.ExecuteTransactionError, ExecuteTransactionOutput, Nothing, Nothing]
  object ExecuteTransaction extends Endpoint[DynamoDBOperation, ExecuteTransactionInput, DynamoDBGen.ExecuteTransactionError, ExecuteTransactionOutput, Nothing, Nothing] with Errorable[ExecuteTransactionError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExecuteTransaction")
    val input: Schema[ExecuteTransactionInput] = ExecuteTransactionInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[ExecuteTransactionOutput] = ExecuteTransactionOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>This operation allows you to perform transactional reads or writes on data stored in\n            DynamoDB, using PartiQL.</p>\n        <note>\n            <p>The entire transaction must consist of either read statements or write statements,\n                you cannot mix both in one transaction. The EXISTS function is an exception and can\n                be used to check the condition of specific attributes of the item in a similar\n                manner to <code>ConditionCheck</code> in the <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/transaction-apis.html#transaction-apis-txwriteitems\">TransactWriteItems</a> API.</p>\n        </note>"),
    )
    def wrap(input: ExecuteTransactionInput) = ExecuteTransaction(input)
    override val errorable: Option[Errorable[ExecuteTransactionError]] = Some(this)
    val error: UnionSchema[ExecuteTransactionError] = ExecuteTransactionError.schema
    def liftError(throwable: Throwable) : Option[ExecuteTransactionError] = throwable match {
      case e: IdempotentParameterMismatchException => Some(ExecuteTransactionError.IdempotentParameterMismatchExceptionCase(e))
      case e: InternalServerError => Some(ExecuteTransactionError.InternalServerErrorCase(e))
      case e: ProvisionedThroughputExceededException => Some(ExecuteTransactionError.ProvisionedThroughputExceededExceptionCase(e))
      case e: RequestLimitExceeded => Some(ExecuteTransactionError.RequestLimitExceededCase(e))
      case e: ResourceNotFoundException => Some(ExecuteTransactionError.ResourceNotFoundExceptionCase(e))
      case e: TransactionCanceledException => Some(ExecuteTransactionError.TransactionCanceledExceptionCase(e))
      case e: TransactionInProgressException => Some(ExecuteTransactionError.TransactionInProgressExceptionCase(e))
      case _ => None
    }
    def unliftError(e: ExecuteTransactionError) : Throwable = e match {
      case ExecuteTransactionError.IdempotentParameterMismatchExceptionCase(e) => e
      case ExecuteTransactionError.InternalServerErrorCase(e) => e
      case ExecuteTransactionError.ProvisionedThroughputExceededExceptionCase(e) => e
      case ExecuteTransactionError.RequestLimitExceededCase(e) => e
      case ExecuteTransactionError.ResourceNotFoundExceptionCase(e) => e
      case ExecuteTransactionError.TransactionCanceledExceptionCase(e) => e
      case ExecuteTransactionError.TransactionInProgressExceptionCase(e) => e
    }
  }
  sealed trait ExecuteTransactionError extends scala.Product with scala.Serializable {
    @inline final def widen: ExecuteTransactionError = this
  }
  object ExecuteTransactionError extends ShapeTag.Companion[ExecuteTransactionError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExecuteTransactionError")

    val hints : Hints = Hints.empty

    case class IdempotentParameterMismatchExceptionCase(idempotentParameterMismatchException: IdempotentParameterMismatchException) extends ExecuteTransactionError
    case class InternalServerErrorCase(internalServerError: InternalServerError) extends ExecuteTransactionError
    case class ProvisionedThroughputExceededExceptionCase(provisionedThroughputExceededException: ProvisionedThroughputExceededException) extends ExecuteTransactionError
    case class RequestLimitExceededCase(requestLimitExceeded: RequestLimitExceeded) extends ExecuteTransactionError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends ExecuteTransactionError
    case class TransactionCanceledExceptionCase(transactionCanceledException: TransactionCanceledException) extends ExecuteTransactionError
    case class TransactionInProgressExceptionCase(transactionInProgressException: TransactionInProgressException) extends ExecuteTransactionError

    object IdempotentParameterMismatchExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[IdempotentParameterMismatchExceptionCase] = bijection(IdempotentParameterMismatchException.schema.addHints(hints), IdempotentParameterMismatchExceptionCase(_), _.idempotentParameterMismatchException)
      val alt = schema.oneOf[ExecuteTransactionError]("IdempotentParameterMismatchException")
    }
    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[ExecuteTransactionError]("InternalServerError")
    }
    object ProvisionedThroughputExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ProvisionedThroughputExceededExceptionCase] = bijection(ProvisionedThroughputExceededException.schema.addHints(hints), ProvisionedThroughputExceededExceptionCase(_), _.provisionedThroughputExceededException)
      val alt = schema.oneOf[ExecuteTransactionError]("ProvisionedThroughputExceededException")
    }
    object RequestLimitExceededCase {
      val hints : Hints = Hints.empty
      val schema: Schema[RequestLimitExceededCase] = bijection(RequestLimitExceeded.schema.addHints(hints), RequestLimitExceededCase(_), _.requestLimitExceeded)
      val alt = schema.oneOf[ExecuteTransactionError]("RequestLimitExceeded")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[ExecuteTransactionError]("ResourceNotFoundException")
    }
    object TransactionCanceledExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[TransactionCanceledExceptionCase] = bijection(TransactionCanceledException.schema.addHints(hints), TransactionCanceledExceptionCase(_), _.transactionCanceledException)
      val alt = schema.oneOf[ExecuteTransactionError]("TransactionCanceledException")
    }
    object TransactionInProgressExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[TransactionInProgressExceptionCase] = bijection(TransactionInProgressException.schema.addHints(hints), TransactionInProgressExceptionCase(_), _.transactionInProgressException)
      val alt = schema.oneOf[ExecuteTransactionError]("TransactionInProgressException")
    }

    implicit val schema: UnionSchema[ExecuteTransactionError] = union(
      IdempotentParameterMismatchExceptionCase.alt,
      InternalServerErrorCase.alt,
      ProvisionedThroughputExceededExceptionCase.alt,
      RequestLimitExceededCase.alt,
      ResourceNotFoundExceptionCase.alt,
      TransactionCanceledExceptionCase.alt,
      TransactionInProgressExceptionCase.alt,
    ){
      case c : IdempotentParameterMismatchExceptionCase => IdempotentParameterMismatchExceptionCase.alt(c)
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : ProvisionedThroughputExceededExceptionCase => ProvisionedThroughputExceededExceptionCase.alt(c)
      case c : RequestLimitExceededCase => RequestLimitExceededCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
      case c : TransactionCanceledExceptionCase => TransactionCanceledExceptionCase.alt(c)
      case c : TransactionInProgressExceptionCase => TransactionInProgressExceptionCase.alt(c)
    }
  }
  case class ExportTableToPointInTime(input: ExportTableToPointInTimeInput) extends DynamoDBOperation[ExportTableToPointInTimeInput, DynamoDBGen.ExportTableToPointInTimeError, ExportTableToPointInTimeOutput, Nothing, Nothing]
  object ExportTableToPointInTime extends Endpoint[DynamoDBOperation, ExportTableToPointInTimeInput, DynamoDBGen.ExportTableToPointInTimeError, ExportTableToPointInTimeOutput, Nothing, Nothing] with Errorable[ExportTableToPointInTimeError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExportTableToPointInTime")
    val input: Schema[ExportTableToPointInTimeInput] = ExportTableToPointInTimeInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[ExportTableToPointInTimeOutput] = ExportTableToPointInTimeOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>Exports table data to an S3 bucket. The table must have point in time recovery\n            enabled, and you can export data from any time within the point in time recovery\n            window.</p>"),
    )
    def wrap(input: ExportTableToPointInTimeInput) = ExportTableToPointInTime(input)
    override val errorable: Option[Errorable[ExportTableToPointInTimeError]] = Some(this)
    val error: UnionSchema[ExportTableToPointInTimeError] = ExportTableToPointInTimeError.schema
    def liftError(throwable: Throwable) : Option[ExportTableToPointInTimeError] = throwable match {
      case e: ExportConflictException => Some(ExportTableToPointInTimeError.ExportConflictExceptionCase(e))
      case e: InternalServerError => Some(ExportTableToPointInTimeError.InternalServerErrorCase(e))
      case e: InvalidExportTimeException => Some(ExportTableToPointInTimeError.InvalidExportTimeExceptionCase(e))
      case e: LimitExceededException => Some(ExportTableToPointInTimeError.LimitExceededExceptionCase(e))
      case e: PointInTimeRecoveryUnavailableException => Some(ExportTableToPointInTimeError.PointInTimeRecoveryUnavailableExceptionCase(e))
      case e: TableNotFoundException => Some(ExportTableToPointInTimeError.TableNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: ExportTableToPointInTimeError) : Throwable = e match {
      case ExportTableToPointInTimeError.ExportConflictExceptionCase(e) => e
      case ExportTableToPointInTimeError.InternalServerErrorCase(e) => e
      case ExportTableToPointInTimeError.InvalidExportTimeExceptionCase(e) => e
      case ExportTableToPointInTimeError.LimitExceededExceptionCase(e) => e
      case ExportTableToPointInTimeError.PointInTimeRecoveryUnavailableExceptionCase(e) => e
      case ExportTableToPointInTimeError.TableNotFoundExceptionCase(e) => e
    }
  }
  sealed trait ExportTableToPointInTimeError extends scala.Product with scala.Serializable {
    @inline final def widen: ExportTableToPointInTimeError = this
  }
  object ExportTableToPointInTimeError extends ShapeTag.Companion[ExportTableToPointInTimeError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExportTableToPointInTimeError")

    val hints : Hints = Hints.empty

    case class ExportConflictExceptionCase(exportConflictException: ExportConflictException) extends ExportTableToPointInTimeError
    case class InternalServerErrorCase(internalServerError: InternalServerError) extends ExportTableToPointInTimeError
    case class InvalidExportTimeExceptionCase(invalidExportTimeException: InvalidExportTimeException) extends ExportTableToPointInTimeError
    case class LimitExceededExceptionCase(limitExceededException: LimitExceededException) extends ExportTableToPointInTimeError
    case class PointInTimeRecoveryUnavailableExceptionCase(pointInTimeRecoveryUnavailableException: PointInTimeRecoveryUnavailableException) extends ExportTableToPointInTimeError
    case class TableNotFoundExceptionCase(tableNotFoundException: TableNotFoundException) extends ExportTableToPointInTimeError

    object ExportConflictExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ExportConflictExceptionCase] = bijection(ExportConflictException.schema.addHints(hints), ExportConflictExceptionCase(_), _.exportConflictException)
      val alt = schema.oneOf[ExportTableToPointInTimeError]("ExportConflictException")
    }
    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[ExportTableToPointInTimeError]("InternalServerError")
    }
    object InvalidExportTimeExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidExportTimeExceptionCase] = bijection(InvalidExportTimeException.schema.addHints(hints), InvalidExportTimeExceptionCase(_), _.invalidExportTimeException)
      val alt = schema.oneOf[ExportTableToPointInTimeError]("InvalidExportTimeException")
    }
    object LimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[LimitExceededExceptionCase] = bijection(LimitExceededException.schema.addHints(hints), LimitExceededExceptionCase(_), _.limitExceededException)
      val alt = schema.oneOf[ExportTableToPointInTimeError]("LimitExceededException")
    }
    object PointInTimeRecoveryUnavailableExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[PointInTimeRecoveryUnavailableExceptionCase] = bijection(PointInTimeRecoveryUnavailableException.schema.addHints(hints), PointInTimeRecoveryUnavailableExceptionCase(_), _.pointInTimeRecoveryUnavailableException)
      val alt = schema.oneOf[ExportTableToPointInTimeError]("PointInTimeRecoveryUnavailableException")
    }
    object TableNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[TableNotFoundExceptionCase] = bijection(TableNotFoundException.schema.addHints(hints), TableNotFoundExceptionCase(_), _.tableNotFoundException)
      val alt = schema.oneOf[ExportTableToPointInTimeError]("TableNotFoundException")
    }

    implicit val schema: UnionSchema[ExportTableToPointInTimeError] = union(
      ExportConflictExceptionCase.alt,
      InternalServerErrorCase.alt,
      InvalidExportTimeExceptionCase.alt,
      LimitExceededExceptionCase.alt,
      PointInTimeRecoveryUnavailableExceptionCase.alt,
      TableNotFoundExceptionCase.alt,
    ){
      case c : ExportConflictExceptionCase => ExportConflictExceptionCase.alt(c)
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidExportTimeExceptionCase => InvalidExportTimeExceptionCase.alt(c)
      case c : LimitExceededExceptionCase => LimitExceededExceptionCase.alt(c)
      case c : PointInTimeRecoveryUnavailableExceptionCase => PointInTimeRecoveryUnavailableExceptionCase.alt(c)
      case c : TableNotFoundExceptionCase => TableNotFoundExceptionCase.alt(c)
    }
  }
  case class GetItem(input: GetItemInput) extends DynamoDBOperation[GetItemInput, DynamoDBGen.GetItemError, GetItemOutput, Nothing, Nothing]
  object GetItem extends Endpoint[DynamoDBOperation, GetItemInput, DynamoDBGen.GetItemError, GetItemOutput, Nothing, Nothing] with Errorable[GetItemError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "GetItem")
    val input: Schema[GetItemInput] = GetItemInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[GetItemOutput] = GetItemOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>The <code>GetItem</code> operation returns a set of attributes for the item with the\n            given primary key. If there is no matching item, <code>GetItem</code> does not return\n            any data and there will be no <code>Item</code> element in the response.</p>\n        <p>\n            <code>GetItem</code> provides an eventually consistent read by default. If your\n            application requires a strongly consistent read, set <code>ConsistentRead</code> to\n                <code>true</code>. Although a strongly consistent read might take more time than an\n            eventually consistent read, it always returns the last updated value.</p>"),
    )
    def wrap(input: GetItemInput) = GetItem(input)
    override val errorable: Option[Errorable[GetItemError]] = Some(this)
    val error: UnionSchema[GetItemError] = GetItemError.schema
    def liftError(throwable: Throwable) : Option[GetItemError] = throwable match {
      case e: InternalServerError => Some(GetItemError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(GetItemError.InvalidEndpointExceptionCase(e))
      case e: ProvisionedThroughputExceededException => Some(GetItemError.ProvisionedThroughputExceededExceptionCase(e))
      case e: RequestLimitExceeded => Some(GetItemError.RequestLimitExceededCase(e))
      case e: ResourceNotFoundException => Some(GetItemError.ResourceNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: GetItemError) : Throwable = e match {
      case GetItemError.InternalServerErrorCase(e) => e
      case GetItemError.InvalidEndpointExceptionCase(e) => e
      case GetItemError.ProvisionedThroughputExceededExceptionCase(e) => e
      case GetItemError.RequestLimitExceededCase(e) => e
      case GetItemError.ResourceNotFoundExceptionCase(e) => e
    }
  }
  sealed trait GetItemError extends scala.Product with scala.Serializable {
    @inline final def widen: GetItemError = this
  }
  object GetItemError extends ShapeTag.Companion[GetItemError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "GetItemError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends GetItemError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends GetItemError
    case class ProvisionedThroughputExceededExceptionCase(provisionedThroughputExceededException: ProvisionedThroughputExceededException) extends GetItemError
    case class RequestLimitExceededCase(requestLimitExceeded: RequestLimitExceeded) extends GetItemError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends GetItemError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[GetItemError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[GetItemError]("InvalidEndpointException")
    }
    object ProvisionedThroughputExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ProvisionedThroughputExceededExceptionCase] = bijection(ProvisionedThroughputExceededException.schema.addHints(hints), ProvisionedThroughputExceededExceptionCase(_), _.provisionedThroughputExceededException)
      val alt = schema.oneOf[GetItemError]("ProvisionedThroughputExceededException")
    }
    object RequestLimitExceededCase {
      val hints : Hints = Hints.empty
      val schema: Schema[RequestLimitExceededCase] = bijection(RequestLimitExceeded.schema.addHints(hints), RequestLimitExceededCase(_), _.requestLimitExceeded)
      val alt = schema.oneOf[GetItemError]("RequestLimitExceeded")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[GetItemError]("ResourceNotFoundException")
    }

    implicit val schema: UnionSchema[GetItemError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      ProvisionedThroughputExceededExceptionCase.alt,
      RequestLimitExceededCase.alt,
      ResourceNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : ProvisionedThroughputExceededExceptionCase => ProvisionedThroughputExceededExceptionCase.alt(c)
      case c : RequestLimitExceededCase => RequestLimitExceededCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
    }
  }
  case class ListBackups(input: ListBackupsInput) extends DynamoDBOperation[ListBackupsInput, DynamoDBGen.ListBackupsError, ListBackupsOutput, Nothing, Nothing]
  object ListBackups extends Endpoint[DynamoDBOperation, ListBackupsInput, DynamoDBGen.ListBackupsError, ListBackupsOutput, Nothing, Nothing] with Errorable[ListBackupsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListBackups")
    val input: Schema[ListBackupsInput] = ListBackupsInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[ListBackupsOutput] = ListBackupsOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>List backups associated with an Amazon Web Services account. To list backups for a\n            given table, specify <code>TableName</code>. <code>ListBackups</code> returns a\n            paginated list of results with at most 1 MB worth of items in a page. You can also\n            specify a maximum number of entries to be returned in a page.</p>\n        <p>In the request, start time is inclusive, but end time is exclusive. Note that these\n            boundaries are for the time at which the original backup was requested.</p>\n        <p>You can call <code>ListBackups</code> a maximum of five times per second.</p>"),
    )
    def wrap(input: ListBackupsInput) = ListBackups(input)
    override val errorable: Option[Errorable[ListBackupsError]] = Some(this)
    val error: UnionSchema[ListBackupsError] = ListBackupsError.schema
    def liftError(throwable: Throwable) : Option[ListBackupsError] = throwable match {
      case e: InternalServerError => Some(ListBackupsError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(ListBackupsError.InvalidEndpointExceptionCase(e))
      case _ => None
    }
    def unliftError(e: ListBackupsError) : Throwable = e match {
      case ListBackupsError.InternalServerErrorCase(e) => e
      case ListBackupsError.InvalidEndpointExceptionCase(e) => e
    }
  }
  sealed trait ListBackupsError extends scala.Product with scala.Serializable {
    @inline final def widen: ListBackupsError = this
  }
  object ListBackupsError extends ShapeTag.Companion[ListBackupsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListBackupsError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends ListBackupsError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends ListBackupsError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[ListBackupsError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[ListBackupsError]("InvalidEndpointException")
    }

    implicit val schema: UnionSchema[ListBackupsError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
    }
  }
  case class ListContributorInsights(input: ListContributorInsightsInput) extends DynamoDBOperation[ListContributorInsightsInput, DynamoDBGen.ListContributorInsightsError, ListContributorInsightsOutput, Nothing, Nothing]
  object ListContributorInsights extends Endpoint[DynamoDBOperation, ListContributorInsightsInput, DynamoDBGen.ListContributorInsightsError, ListContributorInsightsOutput, Nothing, Nothing] with Errorable[ListContributorInsightsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListContributorInsights")
    val input: Schema[ListContributorInsightsInput] = ListContributorInsightsInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[ListContributorInsightsOutput] = ListContributorInsightsOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>Returns a list of ContributorInsightsSummary for a table and all its global secondary\n            indexes.</p>"),
      smithy.api.Paginated(inputToken = Some(smithy.api.NonEmptyString("NextToken")), outputToken = Some(smithy.api.NonEmptyString("NextToken")), items = None, pageSize = Some(smithy.api.NonEmptyString("MaxResults"))),
    )
    def wrap(input: ListContributorInsightsInput) = ListContributorInsights(input)
    override val errorable: Option[Errorable[ListContributorInsightsError]] = Some(this)
    val error: UnionSchema[ListContributorInsightsError] = ListContributorInsightsError.schema
    def liftError(throwable: Throwable) : Option[ListContributorInsightsError] = throwable match {
      case e: InternalServerError => Some(ListContributorInsightsError.InternalServerErrorCase(e))
      case e: ResourceNotFoundException => Some(ListContributorInsightsError.ResourceNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: ListContributorInsightsError) : Throwable = e match {
      case ListContributorInsightsError.InternalServerErrorCase(e) => e
      case ListContributorInsightsError.ResourceNotFoundExceptionCase(e) => e
    }
  }
  sealed trait ListContributorInsightsError extends scala.Product with scala.Serializable {
    @inline final def widen: ListContributorInsightsError = this
  }
  object ListContributorInsightsError extends ShapeTag.Companion[ListContributorInsightsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListContributorInsightsError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends ListContributorInsightsError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends ListContributorInsightsError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[ListContributorInsightsError]("InternalServerError")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[ListContributorInsightsError]("ResourceNotFoundException")
    }

    implicit val schema: UnionSchema[ListContributorInsightsError] = union(
      InternalServerErrorCase.alt,
      ResourceNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
    }
  }
  case class ListExports(input: ListExportsInput) extends DynamoDBOperation[ListExportsInput, DynamoDBGen.ListExportsError, ListExportsOutput, Nothing, Nothing]
  object ListExports extends Endpoint[DynamoDBOperation, ListExportsInput, DynamoDBGen.ListExportsError, ListExportsOutput, Nothing, Nothing] with Errorable[ListExportsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListExports")
    val input: Schema[ListExportsInput] = ListExportsInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[ListExportsOutput] = ListExportsOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>Lists completed exports within the past 90 days.</p>"),
      smithy.api.Paginated(inputToken = Some(smithy.api.NonEmptyString("NextToken")), outputToken = Some(smithy.api.NonEmptyString("NextToken")), items = None, pageSize = Some(smithy.api.NonEmptyString("MaxResults"))),
    )
    def wrap(input: ListExportsInput) = ListExports(input)
    override val errorable: Option[Errorable[ListExportsError]] = Some(this)
    val error: UnionSchema[ListExportsError] = ListExportsError.schema
    def liftError(throwable: Throwable) : Option[ListExportsError] = throwable match {
      case e: InternalServerError => Some(ListExportsError.InternalServerErrorCase(e))
      case e: LimitExceededException => Some(ListExportsError.LimitExceededExceptionCase(e))
      case _ => None
    }
    def unliftError(e: ListExportsError) : Throwable = e match {
      case ListExportsError.InternalServerErrorCase(e) => e
      case ListExportsError.LimitExceededExceptionCase(e) => e
    }
  }
  sealed trait ListExportsError extends scala.Product with scala.Serializable {
    @inline final def widen: ListExportsError = this
  }
  object ListExportsError extends ShapeTag.Companion[ListExportsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListExportsError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends ListExportsError
    case class LimitExceededExceptionCase(limitExceededException: LimitExceededException) extends ListExportsError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[ListExportsError]("InternalServerError")
    }
    object LimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[LimitExceededExceptionCase] = bijection(LimitExceededException.schema.addHints(hints), LimitExceededExceptionCase(_), _.limitExceededException)
      val alt = schema.oneOf[ListExportsError]("LimitExceededException")
    }

    implicit val schema: UnionSchema[ListExportsError] = union(
      InternalServerErrorCase.alt,
      LimitExceededExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : LimitExceededExceptionCase => LimitExceededExceptionCase.alt(c)
    }
  }
  case class ListGlobalTables(input: ListGlobalTablesInput) extends DynamoDBOperation[ListGlobalTablesInput, DynamoDBGen.ListGlobalTablesError, ListGlobalTablesOutput, Nothing, Nothing]
  object ListGlobalTables extends Endpoint[DynamoDBOperation, ListGlobalTablesInput, DynamoDBGen.ListGlobalTablesError, ListGlobalTablesOutput, Nothing, Nothing] with Errorable[ListGlobalTablesError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListGlobalTables")
    val input: Schema[ListGlobalTablesInput] = ListGlobalTablesInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[ListGlobalTablesOutput] = ListGlobalTablesOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Lists all global tables that have a replica in the specified Region.</p>\n        <note>\n            <p>This operation only applies to <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/globaltables.V1.html\">Version\n                    2017.11.29</a> of global tables.</p>\n        </note>"),
    )
    def wrap(input: ListGlobalTablesInput) = ListGlobalTables(input)
    override val errorable: Option[Errorable[ListGlobalTablesError]] = Some(this)
    val error: UnionSchema[ListGlobalTablesError] = ListGlobalTablesError.schema
    def liftError(throwable: Throwable) : Option[ListGlobalTablesError] = throwable match {
      case e: InternalServerError => Some(ListGlobalTablesError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(ListGlobalTablesError.InvalidEndpointExceptionCase(e))
      case _ => None
    }
    def unliftError(e: ListGlobalTablesError) : Throwable = e match {
      case ListGlobalTablesError.InternalServerErrorCase(e) => e
      case ListGlobalTablesError.InvalidEndpointExceptionCase(e) => e
    }
  }
  sealed trait ListGlobalTablesError extends scala.Product with scala.Serializable {
    @inline final def widen: ListGlobalTablesError = this
  }
  object ListGlobalTablesError extends ShapeTag.Companion[ListGlobalTablesError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListGlobalTablesError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends ListGlobalTablesError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends ListGlobalTablesError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[ListGlobalTablesError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[ListGlobalTablesError]("InvalidEndpointException")
    }

    implicit val schema: UnionSchema[ListGlobalTablesError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
    }
  }
  case class ListTables(input: ListTablesInput) extends DynamoDBOperation[ListTablesInput, DynamoDBGen.ListTablesError, ListTablesOutput, Nothing, Nothing]
  object ListTables extends Endpoint[DynamoDBOperation, ListTablesInput, DynamoDBGen.ListTablesError, ListTablesOutput, Nothing, Nothing] with Errorable[ListTablesError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListTables")
    val input: Schema[ListTablesInput] = ListTablesInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[ListTablesOutput] = ListTablesOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Returns an array of table names associated with the current account and endpoint. The\n            output from <code>ListTables</code> is paginated, with each page returning a maximum of\n            100 table names.</p>"),
      smithy.api.Paginated(inputToken = Some(smithy.api.NonEmptyString("ExclusiveStartTableName")), outputToken = Some(smithy.api.NonEmptyString("LastEvaluatedTableName")), items = Some(smithy.api.NonEmptyString("TableNames")), pageSize = Some(smithy.api.NonEmptyString("Limit"))),
    )
    def wrap(input: ListTablesInput) = ListTables(input)
    override val errorable: Option[Errorable[ListTablesError]] = Some(this)
    val error: UnionSchema[ListTablesError] = ListTablesError.schema
    def liftError(throwable: Throwable) : Option[ListTablesError] = throwable match {
      case e: InternalServerError => Some(ListTablesError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(ListTablesError.InvalidEndpointExceptionCase(e))
      case _ => None
    }
    def unliftError(e: ListTablesError) : Throwable = e match {
      case ListTablesError.InternalServerErrorCase(e) => e
      case ListTablesError.InvalidEndpointExceptionCase(e) => e
    }
  }
  sealed trait ListTablesError extends scala.Product with scala.Serializable {
    @inline final def widen: ListTablesError = this
  }
  object ListTablesError extends ShapeTag.Companion[ListTablesError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListTablesError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends ListTablesError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends ListTablesError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[ListTablesError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[ListTablesError]("InvalidEndpointException")
    }

    implicit val schema: UnionSchema[ListTablesError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
    }
  }
  case class ListTagsOfResource(input: ListTagsOfResourceInput) extends DynamoDBOperation[ListTagsOfResourceInput, DynamoDBGen.ListTagsOfResourceError, ListTagsOfResourceOutput, Nothing, Nothing]
  object ListTagsOfResource extends Endpoint[DynamoDBOperation, ListTagsOfResourceInput, DynamoDBGen.ListTagsOfResourceError, ListTagsOfResourceOutput, Nothing, Nothing] with Errorable[ListTagsOfResourceError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListTagsOfResource")
    val input: Schema[ListTagsOfResourceInput] = ListTagsOfResourceInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[ListTagsOfResourceOutput] = ListTagsOfResourceOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>List all tags on an Amazon DynamoDB resource. You can call ListTagsOfResource up to 10\n            times per second, per account.</p>\n        <p>For an overview on tagging DynamoDB resources, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Tagging.html\">Tagging for DynamoDB</a>\n            in the <i>Amazon DynamoDB Developer Guide</i>.</p>"),
    )
    def wrap(input: ListTagsOfResourceInput) = ListTagsOfResource(input)
    override val errorable: Option[Errorable[ListTagsOfResourceError]] = Some(this)
    val error: UnionSchema[ListTagsOfResourceError] = ListTagsOfResourceError.schema
    def liftError(throwable: Throwable) : Option[ListTagsOfResourceError] = throwable match {
      case e: InternalServerError => Some(ListTagsOfResourceError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(ListTagsOfResourceError.InvalidEndpointExceptionCase(e))
      case e: ResourceNotFoundException => Some(ListTagsOfResourceError.ResourceNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: ListTagsOfResourceError) : Throwable = e match {
      case ListTagsOfResourceError.InternalServerErrorCase(e) => e
      case ListTagsOfResourceError.InvalidEndpointExceptionCase(e) => e
      case ListTagsOfResourceError.ResourceNotFoundExceptionCase(e) => e
    }
  }
  sealed trait ListTagsOfResourceError extends scala.Product with scala.Serializable {
    @inline final def widen: ListTagsOfResourceError = this
  }
  object ListTagsOfResourceError extends ShapeTag.Companion[ListTagsOfResourceError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListTagsOfResourceError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends ListTagsOfResourceError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends ListTagsOfResourceError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends ListTagsOfResourceError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[ListTagsOfResourceError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[ListTagsOfResourceError]("InvalidEndpointException")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[ListTagsOfResourceError]("ResourceNotFoundException")
    }

    implicit val schema: UnionSchema[ListTagsOfResourceError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      ResourceNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
    }
  }
  case class PutItem(input: PutItemInput) extends DynamoDBOperation[PutItemInput, DynamoDBGen.PutItemError, PutItemOutput, Nothing, Nothing]
  object PutItem extends Endpoint[DynamoDBOperation, PutItemInput, DynamoDBGen.PutItemError, PutItemOutput, Nothing, Nothing] with Errorable[PutItemError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "PutItem")
    val input: Schema[PutItemInput] = PutItemInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[PutItemOutput] = PutItemOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Creates a new item, or replaces an old item with a new item. If an item that has the\n            same primary key as the new item already exists in the specified table, the new item\n            completely replaces the existing item. You can perform a conditional put operation (add\n            a new item if one with the specified primary key doesn\'t exist), or replace an existing\n            item if it has certain attribute values. You can return the item\'s attribute values in\n            the same operation, using the <code>ReturnValues</code> parameter.</p>\n\n        <p>When you add an item, the primary key attributes are the only required attributes.\n            Attribute values cannot be null.</p>\n        <p>Empty String and Binary attribute values are allowed. Attribute values of type String\n            and Binary must have a length greater than zero if the attribute is used as a key\n            attribute for a table or index. Set type attributes cannot be empty. </p>\n        <p>Invalid Requests with empty values will be rejected with a\n                <code>ValidationException</code> exception.</p>\n        <note>\n            <p>To prevent a new item from replacing an existing item, use a conditional\n                expression that contains the <code>attribute_not_exists</code> function with the\n                name of the attribute being used as the partition key for the table. Since every\n                record must contain that attribute, the <code>attribute_not_exists</code> function\n                will only succeed if no matching item exists.</p>\n        </note>\n        <p>For more information about <code>PutItem</code>, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/WorkingWithItems.html\">Working with\n                Items</a> in the <i>Amazon DynamoDB Developer Guide</i>.</p>"),
    )
    def wrap(input: PutItemInput) = PutItem(input)
    override val errorable: Option[Errorable[PutItemError]] = Some(this)
    val error: UnionSchema[PutItemError] = PutItemError.schema
    def liftError(throwable: Throwable) : Option[PutItemError] = throwable match {
      case e: ConditionalCheckFailedException => Some(PutItemError.ConditionalCheckFailedExceptionCase(e))
      case e: InternalServerError => Some(PutItemError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(PutItemError.InvalidEndpointExceptionCase(e))
      case e: ItemCollectionSizeLimitExceededException => Some(PutItemError.ItemCollectionSizeLimitExceededExceptionCase(e))
      case e: ProvisionedThroughputExceededException => Some(PutItemError.ProvisionedThroughputExceededExceptionCase(e))
      case e: RequestLimitExceeded => Some(PutItemError.RequestLimitExceededCase(e))
      case e: ResourceNotFoundException => Some(PutItemError.ResourceNotFoundExceptionCase(e))
      case e: TransactionConflictException => Some(PutItemError.TransactionConflictExceptionCase(e))
      case _ => None
    }
    def unliftError(e: PutItemError) : Throwable = e match {
      case PutItemError.ConditionalCheckFailedExceptionCase(e) => e
      case PutItemError.InternalServerErrorCase(e) => e
      case PutItemError.InvalidEndpointExceptionCase(e) => e
      case PutItemError.ItemCollectionSizeLimitExceededExceptionCase(e) => e
      case PutItemError.ProvisionedThroughputExceededExceptionCase(e) => e
      case PutItemError.RequestLimitExceededCase(e) => e
      case PutItemError.ResourceNotFoundExceptionCase(e) => e
      case PutItemError.TransactionConflictExceptionCase(e) => e
    }
  }
  sealed trait PutItemError extends scala.Product with scala.Serializable {
    @inline final def widen: PutItemError = this
  }
  object PutItemError extends ShapeTag.Companion[PutItemError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "PutItemError")

    val hints : Hints = Hints.empty

    case class ConditionalCheckFailedExceptionCase(conditionalCheckFailedException: ConditionalCheckFailedException) extends PutItemError
    case class InternalServerErrorCase(internalServerError: InternalServerError) extends PutItemError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends PutItemError
    case class ItemCollectionSizeLimitExceededExceptionCase(itemCollectionSizeLimitExceededException: ItemCollectionSizeLimitExceededException) extends PutItemError
    case class ProvisionedThroughputExceededExceptionCase(provisionedThroughputExceededException: ProvisionedThroughputExceededException) extends PutItemError
    case class RequestLimitExceededCase(requestLimitExceeded: RequestLimitExceeded) extends PutItemError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends PutItemError
    case class TransactionConflictExceptionCase(transactionConflictException: TransactionConflictException) extends PutItemError

    object ConditionalCheckFailedExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ConditionalCheckFailedExceptionCase] = bijection(ConditionalCheckFailedException.schema.addHints(hints), ConditionalCheckFailedExceptionCase(_), _.conditionalCheckFailedException)
      val alt = schema.oneOf[PutItemError]("ConditionalCheckFailedException")
    }
    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[PutItemError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[PutItemError]("InvalidEndpointException")
    }
    object ItemCollectionSizeLimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ItemCollectionSizeLimitExceededExceptionCase] = bijection(ItemCollectionSizeLimitExceededException.schema.addHints(hints), ItemCollectionSizeLimitExceededExceptionCase(_), _.itemCollectionSizeLimitExceededException)
      val alt = schema.oneOf[PutItemError]("ItemCollectionSizeLimitExceededException")
    }
    object ProvisionedThroughputExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ProvisionedThroughputExceededExceptionCase] = bijection(ProvisionedThroughputExceededException.schema.addHints(hints), ProvisionedThroughputExceededExceptionCase(_), _.provisionedThroughputExceededException)
      val alt = schema.oneOf[PutItemError]("ProvisionedThroughputExceededException")
    }
    object RequestLimitExceededCase {
      val hints : Hints = Hints.empty
      val schema: Schema[RequestLimitExceededCase] = bijection(RequestLimitExceeded.schema.addHints(hints), RequestLimitExceededCase(_), _.requestLimitExceeded)
      val alt = schema.oneOf[PutItemError]("RequestLimitExceeded")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[PutItemError]("ResourceNotFoundException")
    }
    object TransactionConflictExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[TransactionConflictExceptionCase] = bijection(TransactionConflictException.schema.addHints(hints), TransactionConflictExceptionCase(_), _.transactionConflictException)
      val alt = schema.oneOf[PutItemError]("TransactionConflictException")
    }

    implicit val schema: UnionSchema[PutItemError] = union(
      ConditionalCheckFailedExceptionCase.alt,
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      ItemCollectionSizeLimitExceededExceptionCase.alt,
      ProvisionedThroughputExceededExceptionCase.alt,
      RequestLimitExceededCase.alt,
      ResourceNotFoundExceptionCase.alt,
      TransactionConflictExceptionCase.alt,
    ){
      case c : ConditionalCheckFailedExceptionCase => ConditionalCheckFailedExceptionCase.alt(c)
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : ItemCollectionSizeLimitExceededExceptionCase => ItemCollectionSizeLimitExceededExceptionCase.alt(c)
      case c : ProvisionedThroughputExceededExceptionCase => ProvisionedThroughputExceededExceptionCase.alt(c)
      case c : RequestLimitExceededCase => RequestLimitExceededCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
      case c : TransactionConflictExceptionCase => TransactionConflictExceptionCase.alt(c)
    }
  }
  case class Query(input: QueryInput) extends DynamoDBOperation[QueryInput, DynamoDBGen.QueryError, QueryOutput, Nothing, Nothing]
  object Query extends Endpoint[DynamoDBOperation, QueryInput, DynamoDBGen.QueryError, QueryOutput, Nothing, Nothing] with Errorable[QueryError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "Query")
    val input: Schema[QueryInput] = QueryInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[QueryOutput] = QueryOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>You must provide the name of the partition key attribute and a single value for that\n            attribute. <code>Query</code> returns all items with that partition key value.\n            Optionally, you can provide a sort key attribute and use a comparison operator to refine\n            the search results.</p>\n\n        <p>Use the <code>KeyConditionExpression</code> parameter to provide a specific value for\n            the partition key. The <code>Query</code> operation will return all of the items from\n            the table or index with that partition key value. You can optionally narrow the scope of\n            the <code>Query</code> operation by specifying a sort key value and a comparison\n            operator in <code>KeyConditionExpression</code>. To further refine the\n                <code>Query</code> results, you can optionally provide a\n                <code>FilterExpression</code>. A <code>FilterExpression</code> determines which\n            items within the results should be returned to you. All of the other results are\n            discarded. </p>\n        <p> A <code>Query</code> operation always returns a result set. If no matching items are\n            found, the result set will be empty. Queries that do not return results consume the\n            minimum number of read capacity units for that type of read operation. </p>\n        <note>\n            <p> DynamoDB calculates the number of read capacity units consumed based on item\n                size, not on the amount of data that is returned to an application. The number of\n                capacity units consumed will be the same whether you request all of the attributes\n                (the default behavior) or just some of them (using a projection expression). The\n                number will also be the same whether or not you use a <code>FilterExpression</code>.\n            </p>\n        </note>\n        <p>\n            <code>Query</code> results are always sorted by the sort key value. If the data type of\n            the sort key is Number, the results are returned in numeric order; otherwise, the\n            results are returned in order of UTF-8 bytes. By default, the sort order is ascending.\n            To reverse the order, set the <code>ScanIndexForward</code> parameter to false. </p>\n        <p> A single <code>Query</code> operation will read up to the maximum number of items set\n            (if using the <code>Limit</code> parameter) or a maximum of 1 MB of data and then apply\n            any filtering to the results using <code>FilterExpression</code>. If\n                <code>LastEvaluatedKey</code> is present in the response, you will need to paginate\n            the result set. For more information, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Query.html#Query.Pagination\">Paginating\n                the Results</a> in the <i>Amazon DynamoDB Developer Guide</i>. </p>\n        <p>\n            <code>FilterExpression</code> is applied after a <code>Query</code> finishes, but before\n            the results are returned. A <code>FilterExpression</code> cannot contain partition key\n            or sort key attributes. You need to specify those attributes in the\n                <code>KeyConditionExpression</code>. </p>\n        <note>\n            <p> A <code>Query</code> operation can return an empty result set and a\n                    <code>LastEvaluatedKey</code> if all the items read for the page of results are\n                filtered out. </p>\n        </note>\n        <p>You can query a table, a local secondary index, or a global secondary index. For a\n            query on a table or on a local secondary index, you can set the\n                <code>ConsistentRead</code> parameter to <code>true</code> and obtain a strongly\n            consistent result. Global secondary indexes support eventually consistent reads only, so\n            do not specify <code>ConsistentRead</code> when querying a global secondary\n            index.</p>"),
      smithy.api.Paginated(inputToken = Some(smithy.api.NonEmptyString("ExclusiveStartKey")), outputToken = Some(smithy.api.NonEmptyString("LastEvaluatedKey")), items = Some(smithy.api.NonEmptyString("Items")), pageSize = Some(smithy.api.NonEmptyString("Limit"))),
      smithy.api.Suppress(List("PaginatedTrait")),
    )
    def wrap(input: QueryInput) = Query(input)
    override val errorable: Option[Errorable[QueryError]] = Some(this)
    val error: UnionSchema[QueryError] = QueryError.schema
    def liftError(throwable: Throwable) : Option[QueryError] = throwable match {
      case e: InternalServerError => Some(QueryError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(QueryError.InvalidEndpointExceptionCase(e))
      case e: ProvisionedThroughputExceededException => Some(QueryError.ProvisionedThroughputExceededExceptionCase(e))
      case e: RequestLimitExceeded => Some(QueryError.RequestLimitExceededCase(e))
      case e: ResourceNotFoundException => Some(QueryError.ResourceNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: QueryError) : Throwable = e match {
      case QueryError.InternalServerErrorCase(e) => e
      case QueryError.InvalidEndpointExceptionCase(e) => e
      case QueryError.ProvisionedThroughputExceededExceptionCase(e) => e
      case QueryError.RequestLimitExceededCase(e) => e
      case QueryError.ResourceNotFoundExceptionCase(e) => e
    }
  }
  sealed trait QueryError extends scala.Product with scala.Serializable {
    @inline final def widen: QueryError = this
  }
  object QueryError extends ShapeTag.Companion[QueryError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "QueryError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends QueryError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends QueryError
    case class ProvisionedThroughputExceededExceptionCase(provisionedThroughputExceededException: ProvisionedThroughputExceededException) extends QueryError
    case class RequestLimitExceededCase(requestLimitExceeded: RequestLimitExceeded) extends QueryError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends QueryError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[QueryError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[QueryError]("InvalidEndpointException")
    }
    object ProvisionedThroughputExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ProvisionedThroughputExceededExceptionCase] = bijection(ProvisionedThroughputExceededException.schema.addHints(hints), ProvisionedThroughputExceededExceptionCase(_), _.provisionedThroughputExceededException)
      val alt = schema.oneOf[QueryError]("ProvisionedThroughputExceededException")
    }
    object RequestLimitExceededCase {
      val hints : Hints = Hints.empty
      val schema: Schema[RequestLimitExceededCase] = bijection(RequestLimitExceeded.schema.addHints(hints), RequestLimitExceededCase(_), _.requestLimitExceeded)
      val alt = schema.oneOf[QueryError]("RequestLimitExceeded")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[QueryError]("ResourceNotFoundException")
    }

    implicit val schema: UnionSchema[QueryError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      ProvisionedThroughputExceededExceptionCase.alt,
      RequestLimitExceededCase.alt,
      ResourceNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : ProvisionedThroughputExceededExceptionCase => ProvisionedThroughputExceededExceptionCase.alt(c)
      case c : RequestLimitExceededCase => RequestLimitExceededCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
    }
  }
  case class RestoreTableFromBackup(input: RestoreTableFromBackupInput) extends DynamoDBOperation[RestoreTableFromBackupInput, DynamoDBGen.RestoreTableFromBackupError, RestoreTableFromBackupOutput, Nothing, Nothing]
  object RestoreTableFromBackup extends Endpoint[DynamoDBOperation, RestoreTableFromBackupInput, DynamoDBGen.RestoreTableFromBackupError, RestoreTableFromBackupOutput, Nothing, Nothing] with Errorable[RestoreTableFromBackupError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "RestoreTableFromBackup")
    val input: Schema[RestoreTableFromBackupInput] = RestoreTableFromBackupInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[RestoreTableFromBackupOutput] = RestoreTableFromBackupOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Creates a new table from an existing backup. Any number of users can execute up to 4\n            concurrent restores (any type of restore) in a given account. </p>\n        <p>You can call <code>RestoreTableFromBackup</code> at a maximum rate of 10 times per\n            second.</p>\n        <p>You must manually set up the following on the restored table:</p>\n        <ul>\n            <li>\n                <p>Auto scaling policies</p>\n            </li>\n            <li>\n                <p>IAM policies</p>\n            </li>\n            <li>\n                <p>Amazon CloudWatch metrics and alarms</p>\n            </li>\n            <li>\n                <p>Tags</p>\n            </li>\n            <li>\n                <p>Stream settings</p>\n            </li>\n            <li>\n                <p>Time to Live (TTL) settings</p>\n            </li>\n         </ul>"),
    )
    def wrap(input: RestoreTableFromBackupInput) = RestoreTableFromBackup(input)
    override val errorable: Option[Errorable[RestoreTableFromBackupError]] = Some(this)
    val error: UnionSchema[RestoreTableFromBackupError] = RestoreTableFromBackupError.schema
    def liftError(throwable: Throwable) : Option[RestoreTableFromBackupError] = throwable match {
      case e: BackupInUseException => Some(RestoreTableFromBackupError.BackupInUseExceptionCase(e))
      case e: BackupNotFoundException => Some(RestoreTableFromBackupError.BackupNotFoundExceptionCase(e))
      case e: InternalServerError => Some(RestoreTableFromBackupError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(RestoreTableFromBackupError.InvalidEndpointExceptionCase(e))
      case e: LimitExceededException => Some(RestoreTableFromBackupError.LimitExceededExceptionCase(e))
      case e: TableAlreadyExistsException => Some(RestoreTableFromBackupError.TableAlreadyExistsExceptionCase(e))
      case e: TableInUseException => Some(RestoreTableFromBackupError.TableInUseExceptionCase(e))
      case _ => None
    }
    def unliftError(e: RestoreTableFromBackupError) : Throwable = e match {
      case RestoreTableFromBackupError.BackupInUseExceptionCase(e) => e
      case RestoreTableFromBackupError.BackupNotFoundExceptionCase(e) => e
      case RestoreTableFromBackupError.InternalServerErrorCase(e) => e
      case RestoreTableFromBackupError.InvalidEndpointExceptionCase(e) => e
      case RestoreTableFromBackupError.LimitExceededExceptionCase(e) => e
      case RestoreTableFromBackupError.TableAlreadyExistsExceptionCase(e) => e
      case RestoreTableFromBackupError.TableInUseExceptionCase(e) => e
    }
  }
  sealed trait RestoreTableFromBackupError extends scala.Product with scala.Serializable {
    @inline final def widen: RestoreTableFromBackupError = this
  }
  object RestoreTableFromBackupError extends ShapeTag.Companion[RestoreTableFromBackupError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "RestoreTableFromBackupError")

    val hints : Hints = Hints.empty

    case class BackupInUseExceptionCase(backupInUseException: BackupInUseException) extends RestoreTableFromBackupError
    case class BackupNotFoundExceptionCase(backupNotFoundException: BackupNotFoundException) extends RestoreTableFromBackupError
    case class InternalServerErrorCase(internalServerError: InternalServerError) extends RestoreTableFromBackupError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends RestoreTableFromBackupError
    case class LimitExceededExceptionCase(limitExceededException: LimitExceededException) extends RestoreTableFromBackupError
    case class TableAlreadyExistsExceptionCase(tableAlreadyExistsException: TableAlreadyExistsException) extends RestoreTableFromBackupError
    case class TableInUseExceptionCase(tableInUseException: TableInUseException) extends RestoreTableFromBackupError

    object BackupInUseExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[BackupInUseExceptionCase] = bijection(BackupInUseException.schema.addHints(hints), BackupInUseExceptionCase(_), _.backupInUseException)
      val alt = schema.oneOf[RestoreTableFromBackupError]("BackupInUseException")
    }
    object BackupNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[BackupNotFoundExceptionCase] = bijection(BackupNotFoundException.schema.addHints(hints), BackupNotFoundExceptionCase(_), _.backupNotFoundException)
      val alt = schema.oneOf[RestoreTableFromBackupError]("BackupNotFoundException")
    }
    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[RestoreTableFromBackupError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[RestoreTableFromBackupError]("InvalidEndpointException")
    }
    object LimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[LimitExceededExceptionCase] = bijection(LimitExceededException.schema.addHints(hints), LimitExceededExceptionCase(_), _.limitExceededException)
      val alt = schema.oneOf[RestoreTableFromBackupError]("LimitExceededException")
    }
    object TableAlreadyExistsExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[TableAlreadyExistsExceptionCase] = bijection(TableAlreadyExistsException.schema.addHints(hints), TableAlreadyExistsExceptionCase(_), _.tableAlreadyExistsException)
      val alt = schema.oneOf[RestoreTableFromBackupError]("TableAlreadyExistsException")
    }
    object TableInUseExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[TableInUseExceptionCase] = bijection(TableInUseException.schema.addHints(hints), TableInUseExceptionCase(_), _.tableInUseException)
      val alt = schema.oneOf[RestoreTableFromBackupError]("TableInUseException")
    }

    implicit val schema: UnionSchema[RestoreTableFromBackupError] = union(
      BackupInUseExceptionCase.alt,
      BackupNotFoundExceptionCase.alt,
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      LimitExceededExceptionCase.alt,
      TableAlreadyExistsExceptionCase.alt,
      TableInUseExceptionCase.alt,
    ){
      case c : BackupInUseExceptionCase => BackupInUseExceptionCase.alt(c)
      case c : BackupNotFoundExceptionCase => BackupNotFoundExceptionCase.alt(c)
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : LimitExceededExceptionCase => LimitExceededExceptionCase.alt(c)
      case c : TableAlreadyExistsExceptionCase => TableAlreadyExistsExceptionCase.alt(c)
      case c : TableInUseExceptionCase => TableInUseExceptionCase.alt(c)
    }
  }
  case class RestoreTableToPointInTime(input: RestoreTableToPointInTimeInput) extends DynamoDBOperation[RestoreTableToPointInTimeInput, DynamoDBGen.RestoreTableToPointInTimeError, RestoreTableToPointInTimeOutput, Nothing, Nothing]
  object RestoreTableToPointInTime extends Endpoint[DynamoDBOperation, RestoreTableToPointInTimeInput, DynamoDBGen.RestoreTableToPointInTimeError, RestoreTableToPointInTimeOutput, Nothing, Nothing] with Errorable[RestoreTableToPointInTimeError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "RestoreTableToPointInTime")
    val input: Schema[RestoreTableToPointInTimeInput] = RestoreTableToPointInTimeInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[RestoreTableToPointInTimeOutput] = RestoreTableToPointInTimeOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Restores the specified table to the specified point in time within\n                <code>EarliestRestorableDateTime</code> and <code>LatestRestorableDateTime</code>.\n            You can restore your table to any point in time during the last 35 days. Any number of\n            users can execute up to 4 concurrent restores (any type of restore) in a given account. </p>\n        <p> When you restore using point in time recovery, DynamoDB restores your table data to\n            the state based on the selected date and time (day:hour:minute:second) to a new table. </p>\n        <p> Along with data, the following are also included on the new restored table using\n            point in time recovery: </p>\n        <ul>\n            <li>\n                <p>Global secondary indexes (GSIs)</p>\n            </li>\n            <li>\n                <p>Local secondary indexes (LSIs)</p>\n            </li>\n            <li>\n                <p>Provisioned read and write capacity</p>\n            </li>\n            <li>\n                <p>Encryption settings</p>\n                <important>\n                    <p> All these settings come from the current settings of the source table at\n                        the time of restore. </p>\n                </important>\n            </li>\n         </ul>\n\n        <p>You must manually set up the following on the restored table:</p>\n        <ul>\n            <li>\n                <p>Auto scaling policies</p>\n            </li>\n            <li>\n                <p>IAM policies</p>\n            </li>\n            <li>\n                <p>Amazon CloudWatch metrics and alarms</p>\n            </li>\n            <li>\n                <p>Tags</p>\n            </li>\n            <li>\n                <p>Stream settings</p>\n            </li>\n            <li>\n                <p>Time to Live (TTL) settings</p>\n            </li>\n            <li>\n                <p>Point in time recovery settings</p>\n            </li>\n         </ul>"),
    )
    def wrap(input: RestoreTableToPointInTimeInput) = RestoreTableToPointInTime(input)
    override val errorable: Option[Errorable[RestoreTableToPointInTimeError]] = Some(this)
    val error: UnionSchema[RestoreTableToPointInTimeError] = RestoreTableToPointInTimeError.schema
    def liftError(throwable: Throwable) : Option[RestoreTableToPointInTimeError] = throwable match {
      case e: InternalServerError => Some(RestoreTableToPointInTimeError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(RestoreTableToPointInTimeError.InvalidEndpointExceptionCase(e))
      case e: InvalidRestoreTimeException => Some(RestoreTableToPointInTimeError.InvalidRestoreTimeExceptionCase(e))
      case e: LimitExceededException => Some(RestoreTableToPointInTimeError.LimitExceededExceptionCase(e))
      case e: PointInTimeRecoveryUnavailableException => Some(RestoreTableToPointInTimeError.PointInTimeRecoveryUnavailableExceptionCase(e))
      case e: TableAlreadyExistsException => Some(RestoreTableToPointInTimeError.TableAlreadyExistsExceptionCase(e))
      case e: TableInUseException => Some(RestoreTableToPointInTimeError.TableInUseExceptionCase(e))
      case e: TableNotFoundException => Some(RestoreTableToPointInTimeError.TableNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: RestoreTableToPointInTimeError) : Throwable = e match {
      case RestoreTableToPointInTimeError.InternalServerErrorCase(e) => e
      case RestoreTableToPointInTimeError.InvalidEndpointExceptionCase(e) => e
      case RestoreTableToPointInTimeError.InvalidRestoreTimeExceptionCase(e) => e
      case RestoreTableToPointInTimeError.LimitExceededExceptionCase(e) => e
      case RestoreTableToPointInTimeError.PointInTimeRecoveryUnavailableExceptionCase(e) => e
      case RestoreTableToPointInTimeError.TableAlreadyExistsExceptionCase(e) => e
      case RestoreTableToPointInTimeError.TableInUseExceptionCase(e) => e
      case RestoreTableToPointInTimeError.TableNotFoundExceptionCase(e) => e
    }
  }
  sealed trait RestoreTableToPointInTimeError extends scala.Product with scala.Serializable {
    @inline final def widen: RestoreTableToPointInTimeError = this
  }
  object RestoreTableToPointInTimeError extends ShapeTag.Companion[RestoreTableToPointInTimeError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "RestoreTableToPointInTimeError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends RestoreTableToPointInTimeError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends RestoreTableToPointInTimeError
    case class InvalidRestoreTimeExceptionCase(invalidRestoreTimeException: InvalidRestoreTimeException) extends RestoreTableToPointInTimeError
    case class LimitExceededExceptionCase(limitExceededException: LimitExceededException) extends RestoreTableToPointInTimeError
    case class PointInTimeRecoveryUnavailableExceptionCase(pointInTimeRecoveryUnavailableException: PointInTimeRecoveryUnavailableException) extends RestoreTableToPointInTimeError
    case class TableAlreadyExistsExceptionCase(tableAlreadyExistsException: TableAlreadyExistsException) extends RestoreTableToPointInTimeError
    case class TableInUseExceptionCase(tableInUseException: TableInUseException) extends RestoreTableToPointInTimeError
    case class TableNotFoundExceptionCase(tableNotFoundException: TableNotFoundException) extends RestoreTableToPointInTimeError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[RestoreTableToPointInTimeError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[RestoreTableToPointInTimeError]("InvalidEndpointException")
    }
    object InvalidRestoreTimeExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidRestoreTimeExceptionCase] = bijection(InvalidRestoreTimeException.schema.addHints(hints), InvalidRestoreTimeExceptionCase(_), _.invalidRestoreTimeException)
      val alt = schema.oneOf[RestoreTableToPointInTimeError]("InvalidRestoreTimeException")
    }
    object LimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[LimitExceededExceptionCase] = bijection(LimitExceededException.schema.addHints(hints), LimitExceededExceptionCase(_), _.limitExceededException)
      val alt = schema.oneOf[RestoreTableToPointInTimeError]("LimitExceededException")
    }
    object PointInTimeRecoveryUnavailableExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[PointInTimeRecoveryUnavailableExceptionCase] = bijection(PointInTimeRecoveryUnavailableException.schema.addHints(hints), PointInTimeRecoveryUnavailableExceptionCase(_), _.pointInTimeRecoveryUnavailableException)
      val alt = schema.oneOf[RestoreTableToPointInTimeError]("PointInTimeRecoveryUnavailableException")
    }
    object TableAlreadyExistsExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[TableAlreadyExistsExceptionCase] = bijection(TableAlreadyExistsException.schema.addHints(hints), TableAlreadyExistsExceptionCase(_), _.tableAlreadyExistsException)
      val alt = schema.oneOf[RestoreTableToPointInTimeError]("TableAlreadyExistsException")
    }
    object TableInUseExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[TableInUseExceptionCase] = bijection(TableInUseException.schema.addHints(hints), TableInUseExceptionCase(_), _.tableInUseException)
      val alt = schema.oneOf[RestoreTableToPointInTimeError]("TableInUseException")
    }
    object TableNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[TableNotFoundExceptionCase] = bijection(TableNotFoundException.schema.addHints(hints), TableNotFoundExceptionCase(_), _.tableNotFoundException)
      val alt = schema.oneOf[RestoreTableToPointInTimeError]("TableNotFoundException")
    }

    implicit val schema: UnionSchema[RestoreTableToPointInTimeError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      InvalidRestoreTimeExceptionCase.alt,
      LimitExceededExceptionCase.alt,
      PointInTimeRecoveryUnavailableExceptionCase.alt,
      TableAlreadyExistsExceptionCase.alt,
      TableInUseExceptionCase.alt,
      TableNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : InvalidRestoreTimeExceptionCase => InvalidRestoreTimeExceptionCase.alt(c)
      case c : LimitExceededExceptionCase => LimitExceededExceptionCase.alt(c)
      case c : PointInTimeRecoveryUnavailableExceptionCase => PointInTimeRecoveryUnavailableExceptionCase.alt(c)
      case c : TableAlreadyExistsExceptionCase => TableAlreadyExistsExceptionCase.alt(c)
      case c : TableInUseExceptionCase => TableInUseExceptionCase.alt(c)
      case c : TableNotFoundExceptionCase => TableNotFoundExceptionCase.alt(c)
    }
  }
  case class Scan(input: ScanInput) extends DynamoDBOperation[ScanInput, DynamoDBGen.ScanError, ScanOutput, Nothing, Nothing]
  object Scan extends Endpoint[DynamoDBOperation, ScanInput, DynamoDBGen.ScanError, ScanOutput, Nothing, Nothing] with Errorable[ScanError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "Scan")
    val input: Schema[ScanInput] = ScanInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[ScanOutput] = ScanOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>The <code>Scan</code> operation returns one or more items and item attributes by\n            accessing every item in a table or a secondary index. To have DynamoDB return fewer\n            items, you can provide a <code>FilterExpression</code> operation.</p>\n        <p>If the total number of scanned items exceeds the maximum dataset size limit of 1 MB,\n            the scan stops and results are returned to the user as a <code>LastEvaluatedKey</code>\n            value to continue the scan in a subsequent operation. The results also include the\n            number of items exceeding the limit. A scan can result in no table data meeting the\n            filter criteria. </p>\n        <p>A single <code>Scan</code> operation reads up to the maximum number of items set (if\n            using the <code>Limit</code> parameter) or a maximum of 1 MB of data and then apply any\n            filtering to the results using <code>FilterExpression</code>. If\n                <code>LastEvaluatedKey</code> is present in the response, you need to paginate the\n            result set. For more information, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Scan.html#Scan.Pagination\">Paginating the\n                Results</a> in the <i>Amazon DynamoDB Developer Guide</i>. </p>\n        <p>\n            <code>Scan</code> operations proceed sequentially; however, for faster performance on\n            a large table or secondary index, applications can request a parallel <code>Scan</code>\n            operation by providing the <code>Segment</code> and <code>TotalSegments</code>\n            parameters. For more information, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Scan.html#Scan.ParallelScan\">Parallel\n                Scan</a> in the <i>Amazon DynamoDB Developer Guide</i>.</p>\n        <p>\n            <code>Scan</code> uses eventually consistent reads when accessing the data in a table;\n            therefore, the result set might not include the changes to data in the table immediately\n            before the operation began. If you need a consistent copy of the data, as of the time\n            that the <code>Scan</code> begins, you can set the <code>ConsistentRead</code> parameter\n            to <code>true</code>.</p>"),
      smithy.api.Paginated(inputToken = Some(smithy.api.NonEmptyString("ExclusiveStartKey")), outputToken = Some(smithy.api.NonEmptyString("LastEvaluatedKey")), items = Some(smithy.api.NonEmptyString("Items")), pageSize = Some(smithy.api.NonEmptyString("Limit"))),
      smithy.api.Suppress(List("PaginatedTrait")),
    )
    def wrap(input: ScanInput) = Scan(input)
    override val errorable: Option[Errorable[ScanError]] = Some(this)
    val error: UnionSchema[ScanError] = ScanError.schema
    def liftError(throwable: Throwable) : Option[ScanError] = throwable match {
      case e: InternalServerError => Some(ScanError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(ScanError.InvalidEndpointExceptionCase(e))
      case e: ProvisionedThroughputExceededException => Some(ScanError.ProvisionedThroughputExceededExceptionCase(e))
      case e: RequestLimitExceeded => Some(ScanError.RequestLimitExceededCase(e))
      case e: ResourceNotFoundException => Some(ScanError.ResourceNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: ScanError) : Throwable = e match {
      case ScanError.InternalServerErrorCase(e) => e
      case ScanError.InvalidEndpointExceptionCase(e) => e
      case ScanError.ProvisionedThroughputExceededExceptionCase(e) => e
      case ScanError.RequestLimitExceededCase(e) => e
      case ScanError.ResourceNotFoundExceptionCase(e) => e
    }
  }
  sealed trait ScanError extends scala.Product with scala.Serializable {
    @inline final def widen: ScanError = this
  }
  object ScanError extends ShapeTag.Companion[ScanError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ScanError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends ScanError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends ScanError
    case class ProvisionedThroughputExceededExceptionCase(provisionedThroughputExceededException: ProvisionedThroughputExceededException) extends ScanError
    case class RequestLimitExceededCase(requestLimitExceeded: RequestLimitExceeded) extends ScanError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends ScanError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[ScanError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[ScanError]("InvalidEndpointException")
    }
    object ProvisionedThroughputExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ProvisionedThroughputExceededExceptionCase] = bijection(ProvisionedThroughputExceededException.schema.addHints(hints), ProvisionedThroughputExceededExceptionCase(_), _.provisionedThroughputExceededException)
      val alt = schema.oneOf[ScanError]("ProvisionedThroughputExceededException")
    }
    object RequestLimitExceededCase {
      val hints : Hints = Hints.empty
      val schema: Schema[RequestLimitExceededCase] = bijection(RequestLimitExceeded.schema.addHints(hints), RequestLimitExceededCase(_), _.requestLimitExceeded)
      val alt = schema.oneOf[ScanError]("RequestLimitExceeded")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[ScanError]("ResourceNotFoundException")
    }

    implicit val schema: UnionSchema[ScanError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      ProvisionedThroughputExceededExceptionCase.alt,
      RequestLimitExceededCase.alt,
      ResourceNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : ProvisionedThroughputExceededExceptionCase => ProvisionedThroughputExceededExceptionCase.alt(c)
      case c : RequestLimitExceededCase => RequestLimitExceededCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
    }
  }
  case class TagResource(input: TagResourceInput) extends DynamoDBOperation[TagResourceInput, DynamoDBGen.TagResourceError, Unit, Nothing, Nothing]
  object TagResource extends Endpoint[DynamoDBOperation, TagResourceInput, DynamoDBGen.TagResourceError, Unit, Nothing, Nothing] with Errorable[TagResourceError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TagResource")
    val input: Schema[TagResourceInput] = TagResourceInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[Unit] = unit.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Associate a set of tags with an Amazon DynamoDB resource. You can then activate these\n            user-defined tags so that they appear on the Billing and Cost Management console for\n            cost allocation tracking. You can call TagResource up to five times per second, per\n            account. </p>\n        <p>For an overview on tagging DynamoDB resources, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Tagging.html\">Tagging for DynamoDB</a>\n            in the <i>Amazon DynamoDB Developer Guide</i>.</p>"),
    )
    def wrap(input: TagResourceInput) = TagResource(input)
    override val errorable: Option[Errorable[TagResourceError]] = Some(this)
    val error: UnionSchema[TagResourceError] = TagResourceError.schema
    def liftError(throwable: Throwable) : Option[TagResourceError] = throwable match {
      case e: InternalServerError => Some(TagResourceError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(TagResourceError.InvalidEndpointExceptionCase(e))
      case e: LimitExceededException => Some(TagResourceError.LimitExceededExceptionCase(e))
      case e: ResourceInUseException => Some(TagResourceError.ResourceInUseExceptionCase(e))
      case e: ResourceNotFoundException => Some(TagResourceError.ResourceNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: TagResourceError) : Throwable = e match {
      case TagResourceError.InternalServerErrorCase(e) => e
      case TagResourceError.InvalidEndpointExceptionCase(e) => e
      case TagResourceError.LimitExceededExceptionCase(e) => e
      case TagResourceError.ResourceInUseExceptionCase(e) => e
      case TagResourceError.ResourceNotFoundExceptionCase(e) => e
    }
  }
  sealed trait TagResourceError extends scala.Product with scala.Serializable {
    @inline final def widen: TagResourceError = this
  }
  object TagResourceError extends ShapeTag.Companion[TagResourceError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TagResourceError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends TagResourceError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends TagResourceError
    case class LimitExceededExceptionCase(limitExceededException: LimitExceededException) extends TagResourceError
    case class ResourceInUseExceptionCase(resourceInUseException: ResourceInUseException) extends TagResourceError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends TagResourceError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[TagResourceError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[TagResourceError]("InvalidEndpointException")
    }
    object LimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[LimitExceededExceptionCase] = bijection(LimitExceededException.schema.addHints(hints), LimitExceededExceptionCase(_), _.limitExceededException)
      val alt = schema.oneOf[TagResourceError]("LimitExceededException")
    }
    object ResourceInUseExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceInUseExceptionCase] = bijection(ResourceInUseException.schema.addHints(hints), ResourceInUseExceptionCase(_), _.resourceInUseException)
      val alt = schema.oneOf[TagResourceError]("ResourceInUseException")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[TagResourceError]("ResourceNotFoundException")
    }

    implicit val schema: UnionSchema[TagResourceError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      LimitExceededExceptionCase.alt,
      ResourceInUseExceptionCase.alt,
      ResourceNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : LimitExceededExceptionCase => LimitExceededExceptionCase.alt(c)
      case c : ResourceInUseExceptionCase => ResourceInUseExceptionCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
    }
  }
  case class TransactGetItems(input: TransactGetItemsInput) extends DynamoDBOperation[TransactGetItemsInput, DynamoDBGen.TransactGetItemsError, TransactGetItemsOutput, Nothing, Nothing]
  object TransactGetItems extends Endpoint[DynamoDBOperation, TransactGetItemsInput, DynamoDBGen.TransactGetItemsError, TransactGetItemsOutput, Nothing, Nothing] with Errorable[TransactGetItemsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TransactGetItems")
    val input: Schema[TransactGetItemsInput] = TransactGetItemsInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[TransactGetItemsOutput] = TransactGetItemsOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>\n            <code>TransactGetItems</code> is a synchronous operation that atomically retrieves\n            multiple items from one or more tables (but not from indexes) in a single account and\n            Region. A <code>TransactGetItems</code> call can contain up to 25\n                <code>TransactGetItem</code> objects, each of which contains a <code>Get</code>\n            structure that specifies an item to retrieve from a table in the account and Region. A\n            call to <code>TransactGetItems</code> cannot retrieve items from tables in more than one\n                Amazon Web Services account or Region. The aggregate size of the items in the\n            transaction cannot exceed 4 MB.</p>\n        <p>DynamoDB rejects the entire <code>TransactGetItems</code> request if any of\n            the following is true:</p>\n        <ul>\n            <li>\n                <p>A conflicting operation is in the process of updating an item to be\n                    read.</p>\n            </li>\n            <li>\n                <p>There is insufficient provisioned capacity for the transaction to be\n                    completed.</p>\n            </li>\n            <li>\n                <p>There is a user error, such as an invalid data format.</p>\n            </li>\n            <li>\n                <p>The aggregate size of the items in the transaction cannot exceed 4 MB.</p>\n            </li>\n         </ul>"),
    )
    def wrap(input: TransactGetItemsInput) = TransactGetItems(input)
    override val errorable: Option[Errorable[TransactGetItemsError]] = Some(this)
    val error: UnionSchema[TransactGetItemsError] = TransactGetItemsError.schema
    def liftError(throwable: Throwable) : Option[TransactGetItemsError] = throwable match {
      case e: InternalServerError => Some(TransactGetItemsError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(TransactGetItemsError.InvalidEndpointExceptionCase(e))
      case e: ProvisionedThroughputExceededException => Some(TransactGetItemsError.ProvisionedThroughputExceededExceptionCase(e))
      case e: RequestLimitExceeded => Some(TransactGetItemsError.RequestLimitExceededCase(e))
      case e: ResourceNotFoundException => Some(TransactGetItemsError.ResourceNotFoundExceptionCase(e))
      case e: TransactionCanceledException => Some(TransactGetItemsError.TransactionCanceledExceptionCase(e))
      case _ => None
    }
    def unliftError(e: TransactGetItemsError) : Throwable = e match {
      case TransactGetItemsError.InternalServerErrorCase(e) => e
      case TransactGetItemsError.InvalidEndpointExceptionCase(e) => e
      case TransactGetItemsError.ProvisionedThroughputExceededExceptionCase(e) => e
      case TransactGetItemsError.RequestLimitExceededCase(e) => e
      case TransactGetItemsError.ResourceNotFoundExceptionCase(e) => e
      case TransactGetItemsError.TransactionCanceledExceptionCase(e) => e
    }
  }
  sealed trait TransactGetItemsError extends scala.Product with scala.Serializable {
    @inline final def widen: TransactGetItemsError = this
  }
  object TransactGetItemsError extends ShapeTag.Companion[TransactGetItemsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TransactGetItemsError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends TransactGetItemsError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends TransactGetItemsError
    case class ProvisionedThroughputExceededExceptionCase(provisionedThroughputExceededException: ProvisionedThroughputExceededException) extends TransactGetItemsError
    case class RequestLimitExceededCase(requestLimitExceeded: RequestLimitExceeded) extends TransactGetItemsError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends TransactGetItemsError
    case class TransactionCanceledExceptionCase(transactionCanceledException: TransactionCanceledException) extends TransactGetItemsError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[TransactGetItemsError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[TransactGetItemsError]("InvalidEndpointException")
    }
    object ProvisionedThroughputExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ProvisionedThroughputExceededExceptionCase] = bijection(ProvisionedThroughputExceededException.schema.addHints(hints), ProvisionedThroughputExceededExceptionCase(_), _.provisionedThroughputExceededException)
      val alt = schema.oneOf[TransactGetItemsError]("ProvisionedThroughputExceededException")
    }
    object RequestLimitExceededCase {
      val hints : Hints = Hints.empty
      val schema: Schema[RequestLimitExceededCase] = bijection(RequestLimitExceeded.schema.addHints(hints), RequestLimitExceededCase(_), _.requestLimitExceeded)
      val alt = schema.oneOf[TransactGetItemsError]("RequestLimitExceeded")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[TransactGetItemsError]("ResourceNotFoundException")
    }
    object TransactionCanceledExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[TransactionCanceledExceptionCase] = bijection(TransactionCanceledException.schema.addHints(hints), TransactionCanceledExceptionCase(_), _.transactionCanceledException)
      val alt = schema.oneOf[TransactGetItemsError]("TransactionCanceledException")
    }

    implicit val schema: UnionSchema[TransactGetItemsError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      ProvisionedThroughputExceededExceptionCase.alt,
      RequestLimitExceededCase.alt,
      ResourceNotFoundExceptionCase.alt,
      TransactionCanceledExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : ProvisionedThroughputExceededExceptionCase => ProvisionedThroughputExceededExceptionCase.alt(c)
      case c : RequestLimitExceededCase => RequestLimitExceededCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
      case c : TransactionCanceledExceptionCase => TransactionCanceledExceptionCase.alt(c)
    }
  }
  case class TransactWriteItems(input: TransactWriteItemsInput) extends DynamoDBOperation[TransactWriteItemsInput, DynamoDBGen.TransactWriteItemsError, TransactWriteItemsOutput, Nothing, Nothing]
  object TransactWriteItems extends Endpoint[DynamoDBOperation, TransactWriteItemsInput, DynamoDBGen.TransactWriteItemsError, TransactWriteItemsOutput, Nothing, Nothing] with Errorable[TransactWriteItemsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TransactWriteItems")
    val input: Schema[TransactWriteItemsInput] = TransactWriteItemsInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[TransactWriteItemsOutput] = TransactWriteItemsOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>\n            <code>TransactWriteItems</code> is a synchronous write operation that groups up to 25\n            action requests. These actions can target items in different tables, but not in\n            different Amazon Web Services accounts or Regions, and no two actions can target the same\n            item. For example, you cannot both <code>ConditionCheck</code> and <code>Update</code>\n            the same item. The aggregate size of the items in the transaction cannot exceed 4\n            MB.</p>\n\n        <p>The actions are completed atomically so that either all of them succeed, or all of\n            them fail. They are defined by the following objects:</p>\n\n        <ul>\n            <li>\n                <p>\n                  <code>Put</code>  —   Initiates a <code>PutItem</code>\n                    operation to write a new item. This structure specifies the primary key of the\n                    item to be written, the name of the table to write it in, an optional condition\n                    expression that must be satisfied for the write to succeed, a list of the item\'s\n                    attributes, and a field indicating whether to retrieve the item\'s attributes if\n                    the condition is not met.</p>\n            </li>\n            <li>\n                <p>\n                  <code>Update</code>  —   Initiates an <code>UpdateItem</code>\n                    operation to update an existing item. This structure specifies the primary key\n                    of the item to be updated, the name of the table where it resides, an optional\n                    condition expression that must be satisfied for the update to succeed, an\n                    expression that defines one or more attributes to be updated, and a field\n                    indicating whether to retrieve the item\'s attributes if the condition is not\n                    met.</p>\n            </li>\n            <li>\n                <p>\n                  <code>Delete</code>  —   Initiates a <code>DeleteItem</code>\n                    operation to delete an existing item. This structure specifies the primary key\n                    of the item to be deleted, the name of the table where it resides, an optional\n                    condition expression that must be satisfied for the deletion to succeed, and a\n                    field indicating whether to retrieve the item\'s attributes if the condition is\n                    not met.</p>\n            </li>\n            <li>\n                <p>\n                  <code>ConditionCheck</code>  —   Applies a condition to an item\n                    that is not being modified by the transaction. This structure specifies the\n                    primary key of the item to be checked, the name of the table where it resides, a\n                    condition expression that must be satisfied for the transaction to succeed, and\n                    a field indicating whether to retrieve the item\'s attributes if the condition is\n                    not met.</p>\n            </li>\n         </ul>\n\n        <p>DynamoDB rejects the entire <code>TransactWriteItems</code> request if any of the\n            following is true:</p>\n        <ul>\n            <li>\n                <p>A condition in one of the condition expressions is not met.</p>\n            </li>\n            <li>\n                <p>An ongoing operation is in the process of updating the same item.</p>\n            </li>\n            <li>\n                <p>There is insufficient provisioned capacity for the transaction to be\n                    completed.</p>\n            </li>\n            <li>\n                <p>An item size becomes too large (bigger than 400 KB), a local secondary index\n                    (LSI) becomes too large, or a similar validation error occurs because of changes\n                    made by the transaction.</p>\n            </li>\n            <li>\n                <p>The aggregate size of the items in the transaction exceeds 4 MB.</p>\n            </li>\n            <li>\n                <p>There is a user error, such as an invalid data format.</p>\n            </li>\n         </ul>"),
    )
    def wrap(input: TransactWriteItemsInput) = TransactWriteItems(input)
    override val errorable: Option[Errorable[TransactWriteItemsError]] = Some(this)
    val error: UnionSchema[TransactWriteItemsError] = TransactWriteItemsError.schema
    def liftError(throwable: Throwable) : Option[TransactWriteItemsError] = throwable match {
      case e: IdempotentParameterMismatchException => Some(TransactWriteItemsError.IdempotentParameterMismatchExceptionCase(e))
      case e: InternalServerError => Some(TransactWriteItemsError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(TransactWriteItemsError.InvalidEndpointExceptionCase(e))
      case e: ProvisionedThroughputExceededException => Some(TransactWriteItemsError.ProvisionedThroughputExceededExceptionCase(e))
      case e: RequestLimitExceeded => Some(TransactWriteItemsError.RequestLimitExceededCase(e))
      case e: ResourceNotFoundException => Some(TransactWriteItemsError.ResourceNotFoundExceptionCase(e))
      case e: TransactionCanceledException => Some(TransactWriteItemsError.TransactionCanceledExceptionCase(e))
      case e: TransactionInProgressException => Some(TransactWriteItemsError.TransactionInProgressExceptionCase(e))
      case _ => None
    }
    def unliftError(e: TransactWriteItemsError) : Throwable = e match {
      case TransactWriteItemsError.IdempotentParameterMismatchExceptionCase(e) => e
      case TransactWriteItemsError.InternalServerErrorCase(e) => e
      case TransactWriteItemsError.InvalidEndpointExceptionCase(e) => e
      case TransactWriteItemsError.ProvisionedThroughputExceededExceptionCase(e) => e
      case TransactWriteItemsError.RequestLimitExceededCase(e) => e
      case TransactWriteItemsError.ResourceNotFoundExceptionCase(e) => e
      case TransactWriteItemsError.TransactionCanceledExceptionCase(e) => e
      case TransactWriteItemsError.TransactionInProgressExceptionCase(e) => e
    }
  }
  sealed trait TransactWriteItemsError extends scala.Product with scala.Serializable {
    @inline final def widen: TransactWriteItemsError = this
  }
  object TransactWriteItemsError extends ShapeTag.Companion[TransactWriteItemsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TransactWriteItemsError")

    val hints : Hints = Hints.empty

    case class IdempotentParameterMismatchExceptionCase(idempotentParameterMismatchException: IdempotentParameterMismatchException) extends TransactWriteItemsError
    case class InternalServerErrorCase(internalServerError: InternalServerError) extends TransactWriteItemsError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends TransactWriteItemsError
    case class ProvisionedThroughputExceededExceptionCase(provisionedThroughputExceededException: ProvisionedThroughputExceededException) extends TransactWriteItemsError
    case class RequestLimitExceededCase(requestLimitExceeded: RequestLimitExceeded) extends TransactWriteItemsError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends TransactWriteItemsError
    case class TransactionCanceledExceptionCase(transactionCanceledException: TransactionCanceledException) extends TransactWriteItemsError
    case class TransactionInProgressExceptionCase(transactionInProgressException: TransactionInProgressException) extends TransactWriteItemsError

    object IdempotentParameterMismatchExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[IdempotentParameterMismatchExceptionCase] = bijection(IdempotentParameterMismatchException.schema.addHints(hints), IdempotentParameterMismatchExceptionCase(_), _.idempotentParameterMismatchException)
      val alt = schema.oneOf[TransactWriteItemsError]("IdempotentParameterMismatchException")
    }
    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[TransactWriteItemsError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[TransactWriteItemsError]("InvalidEndpointException")
    }
    object ProvisionedThroughputExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ProvisionedThroughputExceededExceptionCase] = bijection(ProvisionedThroughputExceededException.schema.addHints(hints), ProvisionedThroughputExceededExceptionCase(_), _.provisionedThroughputExceededException)
      val alt = schema.oneOf[TransactWriteItemsError]("ProvisionedThroughputExceededException")
    }
    object RequestLimitExceededCase {
      val hints : Hints = Hints.empty
      val schema: Schema[RequestLimitExceededCase] = bijection(RequestLimitExceeded.schema.addHints(hints), RequestLimitExceededCase(_), _.requestLimitExceeded)
      val alt = schema.oneOf[TransactWriteItemsError]("RequestLimitExceeded")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[TransactWriteItemsError]("ResourceNotFoundException")
    }
    object TransactionCanceledExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[TransactionCanceledExceptionCase] = bijection(TransactionCanceledException.schema.addHints(hints), TransactionCanceledExceptionCase(_), _.transactionCanceledException)
      val alt = schema.oneOf[TransactWriteItemsError]("TransactionCanceledException")
    }
    object TransactionInProgressExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[TransactionInProgressExceptionCase] = bijection(TransactionInProgressException.schema.addHints(hints), TransactionInProgressExceptionCase(_), _.transactionInProgressException)
      val alt = schema.oneOf[TransactWriteItemsError]("TransactionInProgressException")
    }

    implicit val schema: UnionSchema[TransactWriteItemsError] = union(
      IdempotentParameterMismatchExceptionCase.alt,
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      ProvisionedThroughputExceededExceptionCase.alt,
      RequestLimitExceededCase.alt,
      ResourceNotFoundExceptionCase.alt,
      TransactionCanceledExceptionCase.alt,
      TransactionInProgressExceptionCase.alt,
    ){
      case c : IdempotentParameterMismatchExceptionCase => IdempotentParameterMismatchExceptionCase.alt(c)
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : ProvisionedThroughputExceededExceptionCase => ProvisionedThroughputExceededExceptionCase.alt(c)
      case c : RequestLimitExceededCase => RequestLimitExceededCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
      case c : TransactionCanceledExceptionCase => TransactionCanceledExceptionCase.alt(c)
      case c : TransactionInProgressExceptionCase => TransactionInProgressExceptionCase.alt(c)
    }
  }
  case class UntagResource(input: UntagResourceInput) extends DynamoDBOperation[UntagResourceInput, DynamoDBGen.UntagResourceError, Unit, Nothing, Nothing]
  object UntagResource extends Endpoint[DynamoDBOperation, UntagResourceInput, DynamoDBGen.UntagResourceError, Unit, Nothing, Nothing] with Errorable[UntagResourceError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UntagResource")
    val input: Schema[UntagResourceInput] = UntagResourceInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[Unit] = unit.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Removes the association of tags from an Amazon DynamoDB resource. You can call\n                <code>UntagResource</code> up to five times per second, per account. </p>\n        <p>For an overview on tagging DynamoDB resources, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Tagging.html\">Tagging for DynamoDB</a>\n            in the <i>Amazon DynamoDB Developer Guide</i>.</p>"),
    )
    def wrap(input: UntagResourceInput) = UntagResource(input)
    override val errorable: Option[Errorable[UntagResourceError]] = Some(this)
    val error: UnionSchema[UntagResourceError] = UntagResourceError.schema
    def liftError(throwable: Throwable) : Option[UntagResourceError] = throwable match {
      case e: InternalServerError => Some(UntagResourceError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(UntagResourceError.InvalidEndpointExceptionCase(e))
      case e: LimitExceededException => Some(UntagResourceError.LimitExceededExceptionCase(e))
      case e: ResourceInUseException => Some(UntagResourceError.ResourceInUseExceptionCase(e))
      case e: ResourceNotFoundException => Some(UntagResourceError.ResourceNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: UntagResourceError) : Throwable = e match {
      case UntagResourceError.InternalServerErrorCase(e) => e
      case UntagResourceError.InvalidEndpointExceptionCase(e) => e
      case UntagResourceError.LimitExceededExceptionCase(e) => e
      case UntagResourceError.ResourceInUseExceptionCase(e) => e
      case UntagResourceError.ResourceNotFoundExceptionCase(e) => e
    }
  }
  sealed trait UntagResourceError extends scala.Product with scala.Serializable {
    @inline final def widen: UntagResourceError = this
  }
  object UntagResourceError extends ShapeTag.Companion[UntagResourceError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UntagResourceError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends UntagResourceError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends UntagResourceError
    case class LimitExceededExceptionCase(limitExceededException: LimitExceededException) extends UntagResourceError
    case class ResourceInUseExceptionCase(resourceInUseException: ResourceInUseException) extends UntagResourceError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends UntagResourceError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[UntagResourceError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[UntagResourceError]("InvalidEndpointException")
    }
    object LimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[LimitExceededExceptionCase] = bijection(LimitExceededException.schema.addHints(hints), LimitExceededExceptionCase(_), _.limitExceededException)
      val alt = schema.oneOf[UntagResourceError]("LimitExceededException")
    }
    object ResourceInUseExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceInUseExceptionCase] = bijection(ResourceInUseException.schema.addHints(hints), ResourceInUseExceptionCase(_), _.resourceInUseException)
      val alt = schema.oneOf[UntagResourceError]("ResourceInUseException")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[UntagResourceError]("ResourceNotFoundException")
    }

    implicit val schema: UnionSchema[UntagResourceError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      LimitExceededExceptionCase.alt,
      ResourceInUseExceptionCase.alt,
      ResourceNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : LimitExceededExceptionCase => LimitExceededExceptionCase.alt(c)
      case c : ResourceInUseExceptionCase => ResourceInUseExceptionCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
    }
  }
  case class UpdateContinuousBackups(input: UpdateContinuousBackupsInput) extends DynamoDBOperation[UpdateContinuousBackupsInput, DynamoDBGen.UpdateContinuousBackupsError, UpdateContinuousBackupsOutput, Nothing, Nothing]
  object UpdateContinuousBackups extends Endpoint[DynamoDBOperation, UpdateContinuousBackupsInput, DynamoDBGen.UpdateContinuousBackupsError, UpdateContinuousBackupsOutput, Nothing, Nothing] with Errorable[UpdateContinuousBackupsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateContinuousBackups")
    val input: Schema[UpdateContinuousBackupsInput] = UpdateContinuousBackupsInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[UpdateContinuousBackupsOutput] = UpdateContinuousBackupsOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>\n            <code>UpdateContinuousBackups</code> enables or disables point in time recovery for\n            the specified table. A successful <code>UpdateContinuousBackups</code> call returns the\n            current <code>ContinuousBackupsDescription</code>. Continuous backups are\n                <code>ENABLED</code> on all tables at table creation. If point in time recovery is\n            enabled, <code>PointInTimeRecoveryStatus</code> will be set to ENABLED.</p>\n        <p> Once continuous backups and point in time recovery are enabled, you can restore to\n            any point in time within <code>EarliestRestorableDateTime</code> and\n                <code>LatestRestorableDateTime</code>. </p>\n        <p>\n            <code>LatestRestorableDateTime</code> is typically 5 minutes before the current time.\n            You can restore your table to any point in time during the last 35 days. </p>"),
    )
    def wrap(input: UpdateContinuousBackupsInput) = UpdateContinuousBackups(input)
    override val errorable: Option[Errorable[UpdateContinuousBackupsError]] = Some(this)
    val error: UnionSchema[UpdateContinuousBackupsError] = UpdateContinuousBackupsError.schema
    def liftError(throwable: Throwable) : Option[UpdateContinuousBackupsError] = throwable match {
      case e: ContinuousBackupsUnavailableException => Some(UpdateContinuousBackupsError.ContinuousBackupsUnavailableExceptionCase(e))
      case e: InternalServerError => Some(UpdateContinuousBackupsError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(UpdateContinuousBackupsError.InvalidEndpointExceptionCase(e))
      case e: TableNotFoundException => Some(UpdateContinuousBackupsError.TableNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: UpdateContinuousBackupsError) : Throwable = e match {
      case UpdateContinuousBackupsError.ContinuousBackupsUnavailableExceptionCase(e) => e
      case UpdateContinuousBackupsError.InternalServerErrorCase(e) => e
      case UpdateContinuousBackupsError.InvalidEndpointExceptionCase(e) => e
      case UpdateContinuousBackupsError.TableNotFoundExceptionCase(e) => e
    }
  }
  sealed trait UpdateContinuousBackupsError extends scala.Product with scala.Serializable {
    @inline final def widen: UpdateContinuousBackupsError = this
  }
  object UpdateContinuousBackupsError extends ShapeTag.Companion[UpdateContinuousBackupsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateContinuousBackupsError")

    val hints : Hints = Hints.empty

    case class ContinuousBackupsUnavailableExceptionCase(continuousBackupsUnavailableException: ContinuousBackupsUnavailableException) extends UpdateContinuousBackupsError
    case class InternalServerErrorCase(internalServerError: InternalServerError) extends UpdateContinuousBackupsError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends UpdateContinuousBackupsError
    case class TableNotFoundExceptionCase(tableNotFoundException: TableNotFoundException) extends UpdateContinuousBackupsError

    object ContinuousBackupsUnavailableExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ContinuousBackupsUnavailableExceptionCase] = bijection(ContinuousBackupsUnavailableException.schema.addHints(hints), ContinuousBackupsUnavailableExceptionCase(_), _.continuousBackupsUnavailableException)
      val alt = schema.oneOf[UpdateContinuousBackupsError]("ContinuousBackupsUnavailableException")
    }
    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[UpdateContinuousBackupsError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[UpdateContinuousBackupsError]("InvalidEndpointException")
    }
    object TableNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[TableNotFoundExceptionCase] = bijection(TableNotFoundException.schema.addHints(hints), TableNotFoundExceptionCase(_), _.tableNotFoundException)
      val alt = schema.oneOf[UpdateContinuousBackupsError]("TableNotFoundException")
    }

    implicit val schema: UnionSchema[UpdateContinuousBackupsError] = union(
      ContinuousBackupsUnavailableExceptionCase.alt,
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      TableNotFoundExceptionCase.alt,
    ){
      case c : ContinuousBackupsUnavailableExceptionCase => ContinuousBackupsUnavailableExceptionCase.alt(c)
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : TableNotFoundExceptionCase => TableNotFoundExceptionCase.alt(c)
    }
  }
  case class UpdateContributorInsights(input: UpdateContributorInsightsInput) extends DynamoDBOperation[UpdateContributorInsightsInput, DynamoDBGen.UpdateContributorInsightsError, UpdateContributorInsightsOutput, Nothing, Nothing]
  object UpdateContributorInsights extends Endpoint[DynamoDBOperation, UpdateContributorInsightsInput, DynamoDBGen.UpdateContributorInsightsError, UpdateContributorInsightsOutput, Nothing, Nothing] with Errorable[UpdateContributorInsightsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateContributorInsights")
    val input: Schema[UpdateContributorInsightsInput] = UpdateContributorInsightsInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[UpdateContributorInsightsOutput] = UpdateContributorInsightsOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>Updates the status for contributor insights for a specific table or index. CloudWatch\n            Contributor Insights for DynamoDB graphs display the partition key and (if applicable)\n            sort key of frequently accessed items and frequently throttled items in plaintext. If\n            you require the use of Amazon Web Services Key Management Service (KMS) to encrypt this\n            table’s partition key and sort key data with an Amazon Web Services managed key or\n            customer managed key, you should not enable CloudWatch Contributor Insights for DynamoDB\n            for this table.</p>"),
    )
    def wrap(input: UpdateContributorInsightsInput) = UpdateContributorInsights(input)
    override val errorable: Option[Errorable[UpdateContributorInsightsError]] = Some(this)
    val error: UnionSchema[UpdateContributorInsightsError] = UpdateContributorInsightsError.schema
    def liftError(throwable: Throwable) : Option[UpdateContributorInsightsError] = throwable match {
      case e: InternalServerError => Some(UpdateContributorInsightsError.InternalServerErrorCase(e))
      case e: ResourceNotFoundException => Some(UpdateContributorInsightsError.ResourceNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: UpdateContributorInsightsError) : Throwable = e match {
      case UpdateContributorInsightsError.InternalServerErrorCase(e) => e
      case UpdateContributorInsightsError.ResourceNotFoundExceptionCase(e) => e
    }
  }
  sealed trait UpdateContributorInsightsError extends scala.Product with scala.Serializable {
    @inline final def widen: UpdateContributorInsightsError = this
  }
  object UpdateContributorInsightsError extends ShapeTag.Companion[UpdateContributorInsightsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateContributorInsightsError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends UpdateContributorInsightsError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends UpdateContributorInsightsError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[UpdateContributorInsightsError]("InternalServerError")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[UpdateContributorInsightsError]("ResourceNotFoundException")
    }

    implicit val schema: UnionSchema[UpdateContributorInsightsError] = union(
      InternalServerErrorCase.alt,
      ResourceNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
    }
  }
  case class UpdateGlobalTable(input: UpdateGlobalTableInput) extends DynamoDBOperation[UpdateGlobalTableInput, DynamoDBGen.UpdateGlobalTableError, UpdateGlobalTableOutput, Nothing, Nothing]
  object UpdateGlobalTable extends Endpoint[DynamoDBOperation, UpdateGlobalTableInput, DynamoDBGen.UpdateGlobalTableError, UpdateGlobalTableOutput, Nothing, Nothing] with Errorable[UpdateGlobalTableError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateGlobalTable")
    val input: Schema[UpdateGlobalTableInput] = UpdateGlobalTableInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[UpdateGlobalTableOutput] = UpdateGlobalTableOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Adds or removes replicas in the specified global table. The global table must already\n            exist to be able to use this operation. Any replica to be added must be empty, have the\n            same name as the global table, have the same key schema, have DynamoDB Streams enabled,\n            and have the same provisioned and maximum write capacity units.</p>\n        <note>\n            <p>Although you can use <code>UpdateGlobalTable</code> to add replicas and remove\n                replicas in a single request, for simplicity we recommend that you issue separate\n                requests for adding or removing replicas.</p>\n        </note>\n        <p> If global secondary indexes are specified, then the following conditions must also be\n            met: </p>\n        <ul>\n            <li>\n                <p> The global secondary indexes must have the same name. </p>\n            </li>\n            <li>\n                <p> The global secondary indexes must have the same hash key and sort key (if\n                    present). </p>\n            </li>\n            <li>\n                <p> The global secondary indexes must have the same provisioned and maximum write\n                    capacity units. </p>\n            </li>\n         </ul>"),
    )
    def wrap(input: UpdateGlobalTableInput) = UpdateGlobalTable(input)
    override val errorable: Option[Errorable[UpdateGlobalTableError]] = Some(this)
    val error: UnionSchema[UpdateGlobalTableError] = UpdateGlobalTableError.schema
    def liftError(throwable: Throwable) : Option[UpdateGlobalTableError] = throwable match {
      case e: GlobalTableNotFoundException => Some(UpdateGlobalTableError.GlobalTableNotFoundExceptionCase(e))
      case e: InternalServerError => Some(UpdateGlobalTableError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(UpdateGlobalTableError.InvalidEndpointExceptionCase(e))
      case e: ReplicaAlreadyExistsException => Some(UpdateGlobalTableError.ReplicaAlreadyExistsExceptionCase(e))
      case e: ReplicaNotFoundException => Some(UpdateGlobalTableError.ReplicaNotFoundExceptionCase(e))
      case e: TableNotFoundException => Some(UpdateGlobalTableError.TableNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: UpdateGlobalTableError) : Throwable = e match {
      case UpdateGlobalTableError.GlobalTableNotFoundExceptionCase(e) => e
      case UpdateGlobalTableError.InternalServerErrorCase(e) => e
      case UpdateGlobalTableError.InvalidEndpointExceptionCase(e) => e
      case UpdateGlobalTableError.ReplicaAlreadyExistsExceptionCase(e) => e
      case UpdateGlobalTableError.ReplicaNotFoundExceptionCase(e) => e
      case UpdateGlobalTableError.TableNotFoundExceptionCase(e) => e
    }
  }
  sealed trait UpdateGlobalTableError extends scala.Product with scala.Serializable {
    @inline final def widen: UpdateGlobalTableError = this
  }
  object UpdateGlobalTableError extends ShapeTag.Companion[UpdateGlobalTableError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateGlobalTableError")

    val hints : Hints = Hints.empty

    case class GlobalTableNotFoundExceptionCase(globalTableNotFoundException: GlobalTableNotFoundException) extends UpdateGlobalTableError
    case class InternalServerErrorCase(internalServerError: InternalServerError) extends UpdateGlobalTableError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends UpdateGlobalTableError
    case class ReplicaAlreadyExistsExceptionCase(replicaAlreadyExistsException: ReplicaAlreadyExistsException) extends UpdateGlobalTableError
    case class ReplicaNotFoundExceptionCase(replicaNotFoundException: ReplicaNotFoundException) extends UpdateGlobalTableError
    case class TableNotFoundExceptionCase(tableNotFoundException: TableNotFoundException) extends UpdateGlobalTableError

    object GlobalTableNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[GlobalTableNotFoundExceptionCase] = bijection(GlobalTableNotFoundException.schema.addHints(hints), GlobalTableNotFoundExceptionCase(_), _.globalTableNotFoundException)
      val alt = schema.oneOf[UpdateGlobalTableError]("GlobalTableNotFoundException")
    }
    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[UpdateGlobalTableError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[UpdateGlobalTableError]("InvalidEndpointException")
    }
    object ReplicaAlreadyExistsExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ReplicaAlreadyExistsExceptionCase] = bijection(ReplicaAlreadyExistsException.schema.addHints(hints), ReplicaAlreadyExistsExceptionCase(_), _.replicaAlreadyExistsException)
      val alt = schema.oneOf[UpdateGlobalTableError]("ReplicaAlreadyExistsException")
    }
    object ReplicaNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ReplicaNotFoundExceptionCase] = bijection(ReplicaNotFoundException.schema.addHints(hints), ReplicaNotFoundExceptionCase(_), _.replicaNotFoundException)
      val alt = schema.oneOf[UpdateGlobalTableError]("ReplicaNotFoundException")
    }
    object TableNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[TableNotFoundExceptionCase] = bijection(TableNotFoundException.schema.addHints(hints), TableNotFoundExceptionCase(_), _.tableNotFoundException)
      val alt = schema.oneOf[UpdateGlobalTableError]("TableNotFoundException")
    }

    implicit val schema: UnionSchema[UpdateGlobalTableError] = union(
      GlobalTableNotFoundExceptionCase.alt,
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      ReplicaAlreadyExistsExceptionCase.alt,
      ReplicaNotFoundExceptionCase.alt,
      TableNotFoundExceptionCase.alt,
    ){
      case c : GlobalTableNotFoundExceptionCase => GlobalTableNotFoundExceptionCase.alt(c)
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : ReplicaAlreadyExistsExceptionCase => ReplicaAlreadyExistsExceptionCase.alt(c)
      case c : ReplicaNotFoundExceptionCase => ReplicaNotFoundExceptionCase.alt(c)
      case c : TableNotFoundExceptionCase => TableNotFoundExceptionCase.alt(c)
    }
  }
  case class UpdateGlobalTableSettings(input: UpdateGlobalTableSettingsInput) extends DynamoDBOperation[UpdateGlobalTableSettingsInput, DynamoDBGen.UpdateGlobalTableSettingsError, UpdateGlobalTableSettingsOutput, Nothing, Nothing]
  object UpdateGlobalTableSettings extends Endpoint[DynamoDBOperation, UpdateGlobalTableSettingsInput, DynamoDBGen.UpdateGlobalTableSettingsError, UpdateGlobalTableSettingsOutput, Nothing, Nothing] with Errorable[UpdateGlobalTableSettingsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateGlobalTableSettings")
    val input: Schema[UpdateGlobalTableSettingsInput] = UpdateGlobalTableSettingsInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[UpdateGlobalTableSettingsOutput] = UpdateGlobalTableSettingsOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Updates settings for a global table.</p>"),
    )
    def wrap(input: UpdateGlobalTableSettingsInput) = UpdateGlobalTableSettings(input)
    override val errorable: Option[Errorable[UpdateGlobalTableSettingsError]] = Some(this)
    val error: UnionSchema[UpdateGlobalTableSettingsError] = UpdateGlobalTableSettingsError.schema
    def liftError(throwable: Throwable) : Option[UpdateGlobalTableSettingsError] = throwable match {
      case e: GlobalTableNotFoundException => Some(UpdateGlobalTableSettingsError.GlobalTableNotFoundExceptionCase(e))
      case e: IndexNotFoundException => Some(UpdateGlobalTableSettingsError.IndexNotFoundExceptionCase(e))
      case e: InternalServerError => Some(UpdateGlobalTableSettingsError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(UpdateGlobalTableSettingsError.InvalidEndpointExceptionCase(e))
      case e: LimitExceededException => Some(UpdateGlobalTableSettingsError.LimitExceededExceptionCase(e))
      case e: ReplicaNotFoundException => Some(UpdateGlobalTableSettingsError.ReplicaNotFoundExceptionCase(e))
      case e: ResourceInUseException => Some(UpdateGlobalTableSettingsError.ResourceInUseExceptionCase(e))
      case _ => None
    }
    def unliftError(e: UpdateGlobalTableSettingsError) : Throwable = e match {
      case UpdateGlobalTableSettingsError.GlobalTableNotFoundExceptionCase(e) => e
      case UpdateGlobalTableSettingsError.IndexNotFoundExceptionCase(e) => e
      case UpdateGlobalTableSettingsError.InternalServerErrorCase(e) => e
      case UpdateGlobalTableSettingsError.InvalidEndpointExceptionCase(e) => e
      case UpdateGlobalTableSettingsError.LimitExceededExceptionCase(e) => e
      case UpdateGlobalTableSettingsError.ReplicaNotFoundExceptionCase(e) => e
      case UpdateGlobalTableSettingsError.ResourceInUseExceptionCase(e) => e
    }
  }
  sealed trait UpdateGlobalTableSettingsError extends scala.Product with scala.Serializable {
    @inline final def widen: UpdateGlobalTableSettingsError = this
  }
  object UpdateGlobalTableSettingsError extends ShapeTag.Companion[UpdateGlobalTableSettingsError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateGlobalTableSettingsError")

    val hints : Hints = Hints.empty

    case class GlobalTableNotFoundExceptionCase(globalTableNotFoundException: GlobalTableNotFoundException) extends UpdateGlobalTableSettingsError
    case class IndexNotFoundExceptionCase(indexNotFoundException: IndexNotFoundException) extends UpdateGlobalTableSettingsError
    case class InternalServerErrorCase(internalServerError: InternalServerError) extends UpdateGlobalTableSettingsError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends UpdateGlobalTableSettingsError
    case class LimitExceededExceptionCase(limitExceededException: LimitExceededException) extends UpdateGlobalTableSettingsError
    case class ReplicaNotFoundExceptionCase(replicaNotFoundException: ReplicaNotFoundException) extends UpdateGlobalTableSettingsError
    case class ResourceInUseExceptionCase(resourceInUseException: ResourceInUseException) extends UpdateGlobalTableSettingsError

    object GlobalTableNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[GlobalTableNotFoundExceptionCase] = bijection(GlobalTableNotFoundException.schema.addHints(hints), GlobalTableNotFoundExceptionCase(_), _.globalTableNotFoundException)
      val alt = schema.oneOf[UpdateGlobalTableSettingsError]("GlobalTableNotFoundException")
    }
    object IndexNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[IndexNotFoundExceptionCase] = bijection(IndexNotFoundException.schema.addHints(hints), IndexNotFoundExceptionCase(_), _.indexNotFoundException)
      val alt = schema.oneOf[UpdateGlobalTableSettingsError]("IndexNotFoundException")
    }
    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[UpdateGlobalTableSettingsError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[UpdateGlobalTableSettingsError]("InvalidEndpointException")
    }
    object LimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[LimitExceededExceptionCase] = bijection(LimitExceededException.schema.addHints(hints), LimitExceededExceptionCase(_), _.limitExceededException)
      val alt = schema.oneOf[UpdateGlobalTableSettingsError]("LimitExceededException")
    }
    object ReplicaNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ReplicaNotFoundExceptionCase] = bijection(ReplicaNotFoundException.schema.addHints(hints), ReplicaNotFoundExceptionCase(_), _.replicaNotFoundException)
      val alt = schema.oneOf[UpdateGlobalTableSettingsError]("ReplicaNotFoundException")
    }
    object ResourceInUseExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceInUseExceptionCase] = bijection(ResourceInUseException.schema.addHints(hints), ResourceInUseExceptionCase(_), _.resourceInUseException)
      val alt = schema.oneOf[UpdateGlobalTableSettingsError]("ResourceInUseException")
    }

    implicit val schema: UnionSchema[UpdateGlobalTableSettingsError] = union(
      GlobalTableNotFoundExceptionCase.alt,
      IndexNotFoundExceptionCase.alt,
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      LimitExceededExceptionCase.alt,
      ReplicaNotFoundExceptionCase.alt,
      ResourceInUseExceptionCase.alt,
    ){
      case c : GlobalTableNotFoundExceptionCase => GlobalTableNotFoundExceptionCase.alt(c)
      case c : IndexNotFoundExceptionCase => IndexNotFoundExceptionCase.alt(c)
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : LimitExceededExceptionCase => LimitExceededExceptionCase.alt(c)
      case c : ReplicaNotFoundExceptionCase => ReplicaNotFoundExceptionCase.alt(c)
      case c : ResourceInUseExceptionCase => ResourceInUseExceptionCase.alt(c)
    }
  }
  case class UpdateItem(input: UpdateItemInput) extends DynamoDBOperation[UpdateItemInput, DynamoDBGen.UpdateItemError, UpdateItemOutput, Nothing, Nothing]
  object UpdateItem extends Endpoint[DynamoDBOperation, UpdateItemInput, DynamoDBGen.UpdateItemError, UpdateItemOutput, Nothing, Nothing] with Errorable[UpdateItemError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateItem")
    val input: Schema[UpdateItemInput] = UpdateItemInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[UpdateItemOutput] = UpdateItemOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Edits an existing item\'s attributes, or adds a new item to the table if it does not\n            already exist. You can put, delete, or add attribute values. You can also perform a\n            conditional update on an existing item (insert a new attribute name-value pair if it\n            doesn\'t exist, or replace an existing name-value pair if it has certain expected\n            attribute values).</p>\n        <p>You can also return the item\'s attribute values in the same <code>UpdateItem</code>\n            operation using the <code>ReturnValues</code> parameter.</p>"),
    )
    def wrap(input: UpdateItemInput) = UpdateItem(input)
    override val errorable: Option[Errorable[UpdateItemError]] = Some(this)
    val error: UnionSchema[UpdateItemError] = UpdateItemError.schema
    def liftError(throwable: Throwable) : Option[UpdateItemError] = throwable match {
      case e: ConditionalCheckFailedException => Some(UpdateItemError.ConditionalCheckFailedExceptionCase(e))
      case e: InternalServerError => Some(UpdateItemError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(UpdateItemError.InvalidEndpointExceptionCase(e))
      case e: ItemCollectionSizeLimitExceededException => Some(UpdateItemError.ItemCollectionSizeLimitExceededExceptionCase(e))
      case e: ProvisionedThroughputExceededException => Some(UpdateItemError.ProvisionedThroughputExceededExceptionCase(e))
      case e: RequestLimitExceeded => Some(UpdateItemError.RequestLimitExceededCase(e))
      case e: ResourceNotFoundException => Some(UpdateItemError.ResourceNotFoundExceptionCase(e))
      case e: TransactionConflictException => Some(UpdateItemError.TransactionConflictExceptionCase(e))
      case _ => None
    }
    def unliftError(e: UpdateItemError) : Throwable = e match {
      case UpdateItemError.ConditionalCheckFailedExceptionCase(e) => e
      case UpdateItemError.InternalServerErrorCase(e) => e
      case UpdateItemError.InvalidEndpointExceptionCase(e) => e
      case UpdateItemError.ItemCollectionSizeLimitExceededExceptionCase(e) => e
      case UpdateItemError.ProvisionedThroughputExceededExceptionCase(e) => e
      case UpdateItemError.RequestLimitExceededCase(e) => e
      case UpdateItemError.ResourceNotFoundExceptionCase(e) => e
      case UpdateItemError.TransactionConflictExceptionCase(e) => e
    }
  }
  sealed trait UpdateItemError extends scala.Product with scala.Serializable {
    @inline final def widen: UpdateItemError = this
  }
  object UpdateItemError extends ShapeTag.Companion[UpdateItemError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateItemError")

    val hints : Hints = Hints.empty

    case class ConditionalCheckFailedExceptionCase(conditionalCheckFailedException: ConditionalCheckFailedException) extends UpdateItemError
    case class InternalServerErrorCase(internalServerError: InternalServerError) extends UpdateItemError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends UpdateItemError
    case class ItemCollectionSizeLimitExceededExceptionCase(itemCollectionSizeLimitExceededException: ItemCollectionSizeLimitExceededException) extends UpdateItemError
    case class ProvisionedThroughputExceededExceptionCase(provisionedThroughputExceededException: ProvisionedThroughputExceededException) extends UpdateItemError
    case class RequestLimitExceededCase(requestLimitExceeded: RequestLimitExceeded) extends UpdateItemError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends UpdateItemError
    case class TransactionConflictExceptionCase(transactionConflictException: TransactionConflictException) extends UpdateItemError

    object ConditionalCheckFailedExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ConditionalCheckFailedExceptionCase] = bijection(ConditionalCheckFailedException.schema.addHints(hints), ConditionalCheckFailedExceptionCase(_), _.conditionalCheckFailedException)
      val alt = schema.oneOf[UpdateItemError]("ConditionalCheckFailedException")
    }
    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[UpdateItemError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[UpdateItemError]("InvalidEndpointException")
    }
    object ItemCollectionSizeLimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ItemCollectionSizeLimitExceededExceptionCase] = bijection(ItemCollectionSizeLimitExceededException.schema.addHints(hints), ItemCollectionSizeLimitExceededExceptionCase(_), _.itemCollectionSizeLimitExceededException)
      val alt = schema.oneOf[UpdateItemError]("ItemCollectionSizeLimitExceededException")
    }
    object ProvisionedThroughputExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ProvisionedThroughputExceededExceptionCase] = bijection(ProvisionedThroughputExceededException.schema.addHints(hints), ProvisionedThroughputExceededExceptionCase(_), _.provisionedThroughputExceededException)
      val alt = schema.oneOf[UpdateItemError]("ProvisionedThroughputExceededException")
    }
    object RequestLimitExceededCase {
      val hints : Hints = Hints.empty
      val schema: Schema[RequestLimitExceededCase] = bijection(RequestLimitExceeded.schema.addHints(hints), RequestLimitExceededCase(_), _.requestLimitExceeded)
      val alt = schema.oneOf[UpdateItemError]("RequestLimitExceeded")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[UpdateItemError]("ResourceNotFoundException")
    }
    object TransactionConflictExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[TransactionConflictExceptionCase] = bijection(TransactionConflictException.schema.addHints(hints), TransactionConflictExceptionCase(_), _.transactionConflictException)
      val alt = schema.oneOf[UpdateItemError]("TransactionConflictException")
    }

    implicit val schema: UnionSchema[UpdateItemError] = union(
      ConditionalCheckFailedExceptionCase.alt,
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      ItemCollectionSizeLimitExceededExceptionCase.alt,
      ProvisionedThroughputExceededExceptionCase.alt,
      RequestLimitExceededCase.alt,
      ResourceNotFoundExceptionCase.alt,
      TransactionConflictExceptionCase.alt,
    ){
      case c : ConditionalCheckFailedExceptionCase => ConditionalCheckFailedExceptionCase.alt(c)
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : ItemCollectionSizeLimitExceededExceptionCase => ItemCollectionSizeLimitExceededExceptionCase.alt(c)
      case c : ProvisionedThroughputExceededExceptionCase => ProvisionedThroughputExceededExceptionCase.alt(c)
      case c : RequestLimitExceededCase => RequestLimitExceededCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
      case c : TransactionConflictExceptionCase => TransactionConflictExceptionCase.alt(c)
    }
  }
  case class UpdateTable(input: UpdateTableInput) extends DynamoDBOperation[UpdateTableInput, DynamoDBGen.UpdateTableError, UpdateTableOutput, Nothing, Nothing]
  object UpdateTable extends Endpoint[DynamoDBOperation, UpdateTableInput, DynamoDBGen.UpdateTableError, UpdateTableOutput, Nothing, Nothing] with Errorable[UpdateTableError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateTable")
    val input: Schema[UpdateTableInput] = UpdateTableInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[UpdateTableOutput] = UpdateTableOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>Modifies the provisioned throughput settings, global secondary indexes, or DynamoDB\n            Streams settings for a given table.</p>\n        <p>You can only perform one of the following operations at once:</p>\n        <ul>\n            <li>\n                <p>Modify the provisioned throughput settings of the table.</p>\n            </li>\n            <li>\n                <p>Remove a global secondary index from the table.</p>\n            </li>\n            <li>\n                <p>Create a new global secondary index on the table. After the index begins\n                    backfilling, you can use <code>UpdateTable</code> to perform other\n                    operations.</p>\n            </li>\n         </ul>\n        <p>\n            <code>UpdateTable</code> is an asynchronous operation; while it is executing, the table\n            status changes from <code>ACTIVE</code> to <code>UPDATING</code>. While it is\n                <code>UPDATING</code>, you cannot issue another <code>UpdateTable</code> request.\n            When the table returns to the <code>ACTIVE</code> state, the <code>UpdateTable</code>\n            operation is complete.</p>"),
    )
    def wrap(input: UpdateTableInput) = UpdateTable(input)
    override val errorable: Option[Errorable[UpdateTableError]] = Some(this)
    val error: UnionSchema[UpdateTableError] = UpdateTableError.schema
    def liftError(throwable: Throwable) : Option[UpdateTableError] = throwable match {
      case e: InternalServerError => Some(UpdateTableError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(UpdateTableError.InvalidEndpointExceptionCase(e))
      case e: LimitExceededException => Some(UpdateTableError.LimitExceededExceptionCase(e))
      case e: ResourceInUseException => Some(UpdateTableError.ResourceInUseExceptionCase(e))
      case e: ResourceNotFoundException => Some(UpdateTableError.ResourceNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: UpdateTableError) : Throwable = e match {
      case UpdateTableError.InternalServerErrorCase(e) => e
      case UpdateTableError.InvalidEndpointExceptionCase(e) => e
      case UpdateTableError.LimitExceededExceptionCase(e) => e
      case UpdateTableError.ResourceInUseExceptionCase(e) => e
      case UpdateTableError.ResourceNotFoundExceptionCase(e) => e
    }
  }
  sealed trait UpdateTableError extends scala.Product with scala.Serializable {
    @inline final def widen: UpdateTableError = this
  }
  object UpdateTableError extends ShapeTag.Companion[UpdateTableError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateTableError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends UpdateTableError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends UpdateTableError
    case class LimitExceededExceptionCase(limitExceededException: LimitExceededException) extends UpdateTableError
    case class ResourceInUseExceptionCase(resourceInUseException: ResourceInUseException) extends UpdateTableError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends UpdateTableError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[UpdateTableError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[UpdateTableError]("InvalidEndpointException")
    }
    object LimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[LimitExceededExceptionCase] = bijection(LimitExceededException.schema.addHints(hints), LimitExceededExceptionCase(_), _.limitExceededException)
      val alt = schema.oneOf[UpdateTableError]("LimitExceededException")
    }
    object ResourceInUseExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceInUseExceptionCase] = bijection(ResourceInUseException.schema.addHints(hints), ResourceInUseExceptionCase(_), _.resourceInUseException)
      val alt = schema.oneOf[UpdateTableError]("ResourceInUseException")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[UpdateTableError]("ResourceNotFoundException")
    }

    implicit val schema: UnionSchema[UpdateTableError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      LimitExceededExceptionCase.alt,
      ResourceInUseExceptionCase.alt,
      ResourceNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : LimitExceededExceptionCase => LimitExceededExceptionCase.alt(c)
      case c : ResourceInUseExceptionCase => ResourceInUseExceptionCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
    }
  }
  case class UpdateTableReplicaAutoScaling(input: UpdateTableReplicaAutoScalingInput) extends DynamoDBOperation[UpdateTableReplicaAutoScalingInput, DynamoDBGen.UpdateTableReplicaAutoScalingError, UpdateTableReplicaAutoScalingOutput, Nothing, Nothing]
  object UpdateTableReplicaAutoScaling extends Endpoint[DynamoDBOperation, UpdateTableReplicaAutoScalingInput, DynamoDBGen.UpdateTableReplicaAutoScalingError, UpdateTableReplicaAutoScalingOutput, Nothing, Nothing] with Errorable[UpdateTableReplicaAutoScalingError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateTableReplicaAutoScaling")
    val input: Schema[UpdateTableReplicaAutoScalingInput] = UpdateTableReplicaAutoScalingInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[UpdateTableReplicaAutoScalingOutput] = UpdateTableReplicaAutoScalingOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      smithy.api.Documentation("<p>Updates auto scaling settings on your global tables at once.</p>\n        <note>\n            <p>This operation only applies to <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/globaltables.V2.html\">Version\n                    2019.11.21</a> of global tables.</p>\n        </note>"),
    )
    def wrap(input: UpdateTableReplicaAutoScalingInput) = UpdateTableReplicaAutoScaling(input)
    override val errorable: Option[Errorable[UpdateTableReplicaAutoScalingError]] = Some(this)
    val error: UnionSchema[UpdateTableReplicaAutoScalingError] = UpdateTableReplicaAutoScalingError.schema
    def liftError(throwable: Throwable) : Option[UpdateTableReplicaAutoScalingError] = throwable match {
      case e: InternalServerError => Some(UpdateTableReplicaAutoScalingError.InternalServerErrorCase(e))
      case e: LimitExceededException => Some(UpdateTableReplicaAutoScalingError.LimitExceededExceptionCase(e))
      case e: ResourceInUseException => Some(UpdateTableReplicaAutoScalingError.ResourceInUseExceptionCase(e))
      case e: ResourceNotFoundException => Some(UpdateTableReplicaAutoScalingError.ResourceNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: UpdateTableReplicaAutoScalingError) : Throwable = e match {
      case UpdateTableReplicaAutoScalingError.InternalServerErrorCase(e) => e
      case UpdateTableReplicaAutoScalingError.LimitExceededExceptionCase(e) => e
      case UpdateTableReplicaAutoScalingError.ResourceInUseExceptionCase(e) => e
      case UpdateTableReplicaAutoScalingError.ResourceNotFoundExceptionCase(e) => e
    }
  }
  sealed trait UpdateTableReplicaAutoScalingError extends scala.Product with scala.Serializable {
    @inline final def widen: UpdateTableReplicaAutoScalingError = this
  }
  object UpdateTableReplicaAutoScalingError extends ShapeTag.Companion[UpdateTableReplicaAutoScalingError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateTableReplicaAutoScalingError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends UpdateTableReplicaAutoScalingError
    case class LimitExceededExceptionCase(limitExceededException: LimitExceededException) extends UpdateTableReplicaAutoScalingError
    case class ResourceInUseExceptionCase(resourceInUseException: ResourceInUseException) extends UpdateTableReplicaAutoScalingError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends UpdateTableReplicaAutoScalingError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[UpdateTableReplicaAutoScalingError]("InternalServerError")
    }
    object LimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[LimitExceededExceptionCase] = bijection(LimitExceededException.schema.addHints(hints), LimitExceededExceptionCase(_), _.limitExceededException)
      val alt = schema.oneOf[UpdateTableReplicaAutoScalingError]("LimitExceededException")
    }
    object ResourceInUseExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceInUseExceptionCase] = bijection(ResourceInUseException.schema.addHints(hints), ResourceInUseExceptionCase(_), _.resourceInUseException)
      val alt = schema.oneOf[UpdateTableReplicaAutoScalingError]("ResourceInUseException")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[UpdateTableReplicaAutoScalingError]("ResourceNotFoundException")
    }

    implicit val schema: UnionSchema[UpdateTableReplicaAutoScalingError] = union(
      InternalServerErrorCase.alt,
      LimitExceededExceptionCase.alt,
      ResourceInUseExceptionCase.alt,
      ResourceNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : LimitExceededExceptionCase => LimitExceededExceptionCase.alt(c)
      case c : ResourceInUseExceptionCase => ResourceInUseExceptionCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
    }
  }
  case class UpdateTimeToLive(input: UpdateTimeToLiveInput) extends DynamoDBOperation[UpdateTimeToLiveInput, DynamoDBGen.UpdateTimeToLiveError, UpdateTimeToLiveOutput, Nothing, Nothing]
  object UpdateTimeToLive extends Endpoint[DynamoDBOperation, UpdateTimeToLiveInput, DynamoDBGen.UpdateTimeToLiveError, UpdateTimeToLiveOutput, Nothing, Nothing] with Errorable[UpdateTimeToLiveError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateTimeToLive")
    val input: Schema[UpdateTimeToLiveInput] = UpdateTimeToLiveInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[UpdateTimeToLiveOutput] = UpdateTimeToLiveOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      aws.api.ClientDiscoveredEndpoint(required = false),
      smithy.api.Documentation("<p>The <code>UpdateTimeToLive</code> method enables or disables Time to Live (TTL) for\n            the specified table. A successful <code>UpdateTimeToLive</code> call returns the current\n                <code>TimeToLiveSpecification</code>. It can take up to one hour for the change to\n            fully process. Any additional <code>UpdateTimeToLive</code> calls for the same table\n            during this one hour duration result in a <code>ValidationException</code>. </p>\n        <p>TTL compares the current time in epoch time format to the time stored in the TTL\n            attribute of an item. If the epoch time value stored in the attribute is less than the\n            current time, the item is marked as expired and subsequently deleted.</p>\n        <note>\n            <p> The epoch time format is the number of seconds elapsed since 12:00:00 AM January\n                1, 1970 UTC. </p>\n        </note>\n        <p>DynamoDB deletes expired items on a best-effort basis to ensure availability of\n            throughput for other data operations. </p>\n        <important>\n            <p>DynamoDB typically deletes expired items within two days of expiration. The exact\n                duration within which an item gets deleted after expiration is specific to the\n                nature of the workload. Items that have expired and not been deleted will still show\n                up in reads, queries, and scans.</p>\n        </important>\n        <p>As items are deleted, they are removed from any local secondary index and global\n            secondary index immediately in the same eventually consistent way as a standard delete\n            operation.</p>\n        <p>For more information, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/TTL.html\">Time To Live</a> in the\n            Amazon DynamoDB Developer Guide. </p>"),
    )
    def wrap(input: UpdateTimeToLiveInput) = UpdateTimeToLive(input)
    override val errorable: Option[Errorable[UpdateTimeToLiveError]] = Some(this)
    val error: UnionSchema[UpdateTimeToLiveError] = UpdateTimeToLiveError.schema
    def liftError(throwable: Throwable) : Option[UpdateTimeToLiveError] = throwable match {
      case e: InternalServerError => Some(UpdateTimeToLiveError.InternalServerErrorCase(e))
      case e: InvalidEndpointException => Some(UpdateTimeToLiveError.InvalidEndpointExceptionCase(e))
      case e: LimitExceededException => Some(UpdateTimeToLiveError.LimitExceededExceptionCase(e))
      case e: ResourceInUseException => Some(UpdateTimeToLiveError.ResourceInUseExceptionCase(e))
      case e: ResourceNotFoundException => Some(UpdateTimeToLiveError.ResourceNotFoundExceptionCase(e))
      case _ => None
    }
    def unliftError(e: UpdateTimeToLiveError) : Throwable = e match {
      case UpdateTimeToLiveError.InternalServerErrorCase(e) => e
      case UpdateTimeToLiveError.InvalidEndpointExceptionCase(e) => e
      case UpdateTimeToLiveError.LimitExceededExceptionCase(e) => e
      case UpdateTimeToLiveError.ResourceInUseExceptionCase(e) => e
      case UpdateTimeToLiveError.ResourceNotFoundExceptionCase(e) => e
    }
  }
  sealed trait UpdateTimeToLiveError extends scala.Product with scala.Serializable {
    @inline final def widen: UpdateTimeToLiveError = this
  }
  object UpdateTimeToLiveError extends ShapeTag.Companion[UpdateTimeToLiveError] {
    val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateTimeToLiveError")

    val hints : Hints = Hints.empty

    case class InternalServerErrorCase(internalServerError: InternalServerError) extends UpdateTimeToLiveError
    case class InvalidEndpointExceptionCase(invalidEndpointException: InvalidEndpointException) extends UpdateTimeToLiveError
    case class LimitExceededExceptionCase(limitExceededException: LimitExceededException) extends UpdateTimeToLiveError
    case class ResourceInUseExceptionCase(resourceInUseException: ResourceInUseException) extends UpdateTimeToLiveError
    case class ResourceNotFoundExceptionCase(resourceNotFoundException: ResourceNotFoundException) extends UpdateTimeToLiveError

    object InternalServerErrorCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InternalServerErrorCase] = bijection(InternalServerError.schema.addHints(hints), InternalServerErrorCase(_), _.internalServerError)
      val alt = schema.oneOf[UpdateTimeToLiveError]("InternalServerError")
    }
    object InvalidEndpointExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[InvalidEndpointExceptionCase] = bijection(InvalidEndpointException.schema.addHints(hints), InvalidEndpointExceptionCase(_), _.invalidEndpointException)
      val alt = schema.oneOf[UpdateTimeToLiveError]("InvalidEndpointException")
    }
    object LimitExceededExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[LimitExceededExceptionCase] = bijection(LimitExceededException.schema.addHints(hints), LimitExceededExceptionCase(_), _.limitExceededException)
      val alt = schema.oneOf[UpdateTimeToLiveError]("LimitExceededException")
    }
    object ResourceInUseExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceInUseExceptionCase] = bijection(ResourceInUseException.schema.addHints(hints), ResourceInUseExceptionCase(_), _.resourceInUseException)
      val alt = schema.oneOf[UpdateTimeToLiveError]("ResourceInUseException")
    }
    object ResourceNotFoundExceptionCase {
      val hints : Hints = Hints.empty
      val schema: Schema[ResourceNotFoundExceptionCase] = bijection(ResourceNotFoundException.schema.addHints(hints), ResourceNotFoundExceptionCase(_), _.resourceNotFoundException)
      val alt = schema.oneOf[UpdateTimeToLiveError]("ResourceNotFoundException")
    }

    implicit val schema: UnionSchema[UpdateTimeToLiveError] = union(
      InternalServerErrorCase.alt,
      InvalidEndpointExceptionCase.alt,
      LimitExceededExceptionCase.alt,
      ResourceInUseExceptionCase.alt,
      ResourceNotFoundExceptionCase.alt,
    ){
      case c : InternalServerErrorCase => InternalServerErrorCase.alt(c)
      case c : InvalidEndpointExceptionCase => InvalidEndpointExceptionCase.alt(c)
      case c : LimitExceededExceptionCase => LimitExceededExceptionCase.alt(c)
      case c : ResourceInUseExceptionCase => ResourceInUseExceptionCase.alt(c)
      case c : ResourceNotFoundExceptionCase => ResourceNotFoundExceptionCase.alt(c)
    }
  }
}

sealed trait DynamoDBOperation[Input, Err, Output, StreamedInput, StreamedOutput]
