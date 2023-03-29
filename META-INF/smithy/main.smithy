$version: "2"

namespace demo

@error("server")
@httpError(500)
structure DynamoError {
  @required name: String,
  age: Integer
}

@alloy#simpleRestJson
service DemoService {
  operations: [DemoOp]
}

@http(method: "GET", uri: "/demo")
@readonly
operation DemoOp {
  errors: [DynamoError]
}
