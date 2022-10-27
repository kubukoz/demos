namespace hello
use smithy4s.api#simpleRestJson

@simpleRestJson
service ReportService {
  operations: [Report]
}

@http(method: "POST", uri: "/api", code: 200)
operation Report {
    input: CameraQuery,
}

@mediaType("application/x-www-form-urlencoded;charset=utf-8")
string FormPayload

structure CameraQuery {
    @required
    @httpPayload
    payload: FormPayload
}
