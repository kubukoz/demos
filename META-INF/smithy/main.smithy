$version: "2.0"

namespace dev.scala.smithy

use alloy#simpleRestJson

@simpleRestJson
service SimpleService {
    version: "1.0.0"
    operations: [SimpleOperation]
}

@http(method: "POST", uri: "/operation", code: 200)
operation SimpleOperation {
    input: InputData,
    errors: [ServiceUnavailable]
}

structure InputData {
    @required
    value: String,
}

@error("client")
@httpError(400)
structure BadRequest {
    code: Integer = 400
    message: String = "The request contains bad syntax or cannot be fulfilled."
}

@error("server")
@httpError(503)
structure ServiceUnavailable {
    code: Integer = 503
    message: String = "The server is currently unavailable."
}
