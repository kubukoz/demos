import smithy4s.schema.Schema

object DynamoHello extends App {

  println(
    Schema
      .string
      .validated(
        smithy
          .api
          .Pattern("^[\\u0020-\\uD7FF\\uE000-\\uFFFD\\uD800\\uDC00-\\uDBFF\\uDFFF\\r\\n\\t]*$")
      )
      .shapeId
  )
}
