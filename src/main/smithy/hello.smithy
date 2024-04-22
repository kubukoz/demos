$version: "2"

namespace hello

use alloy#simpleRestJson

@simpleRestJson
service WeatherService {
    operations: [PostWeather]
}

@http(method: "POST", uri: "/weather")
@readonly
operation PostWeather {
    input := {
        @required
        @httpPayload
        city: String = "strange town"
    }
    output := {
        @httpPayload
        weather: String = "good weather"
    }
}
