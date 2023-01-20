$version: "2"

namespace hello

use smithy.api#streaming
use alloy#untagged

service WeatherStreamService {
  operations: [GetWeather]
}

@streaming
@untagged
union Request {
  input: RequestInput
}

structure RequestInput {
  @required city: String
}


@streaming
@untagged
union Response {
  output: ResponseOutput
}

structure ResponseOutput {
  @required weather: String
}

operation GetWeather {
  input := {
    @required request: Request
    @required
    @httpQuery("other")
    other: String
  },
  output := {
    @required response: Response
  }
}
