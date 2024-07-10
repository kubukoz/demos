//> using dep software.amazon.awssdk:sqs:2.26.17
////> using dep ch.qos.logback:logback-classic:1.5.6
//> using scala 3.3.0

import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.ListQueuesRequest
import scala.jdk.CollectionConverters._

import software.amazon.awssdk.core.interceptor.ExecutionAttributes
import software.amazon.awssdk.core.interceptor.ExecutionInterceptor
import org.slf4j.LoggerFactory
import java.net.URI
import software.amazon.awssdk.http.apache.ApacheHttpClient
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration
import software.amazon.awssdk.core.interceptor.Context.BeforeMarshalling
import software.amazon.awssdk.core.interceptor.Context.AfterExecution
import software.amazon.awssdk.core.interceptor.Context.ModifyHttpRequest
import software.amazon.awssdk.http.SdkHttpRequest
import software.amazon.awssdk.core.interceptor.Context.BeforeUnmarshalling
import software.amazon.awssdk.services.sqs.model.QueueAttributeName

object ListQueues extends App {

  val sqsClient: SqsClient = SqsClient
    .builder()
    .endpointOverride(URI.create("http://localhost:4566"))
    .httpClient(ApacheHttpClient.builder().build())
    .build()

  val listQueuesRequest = ListQueuesRequest.builder().build()
  val listQueuesResponse = sqsClient.listQueues(listQueuesRequest)

  val queueUrls = listQueuesResponse.queueUrls().asScala

  if (queueUrls.nonEmpty) {
    println("Queue URLs:")
    queueUrls.foreach(println)
    queueUrls.foreach { q =>
      println(
        sqsClient
          .getQueueAttributes(
            _.queueUrl(q)
              .attributeNames(QueueAttributeName.FIFO_QUEUE, QueueAttributeName.VISIBILITY_TIMEOUT)
          )
          .attributes()
      )

    }
  } else {
    println("No queues found.")
  }

  sqsClient.close()
}
