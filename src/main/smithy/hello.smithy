$version: "2"

namespace hello

use alloy#simpleRestJson

@simpleRestJson
service WeatherService {
    operations: [GetWeather]
}

@http(method: "GET", uri: "/weather/{city}", code: 200)
@readonly
operation GetWeather {
    input := {
        @required
        @httpLabel
        city: String
    }
    output := {
        @required
        weather: String
        @required
        @httpResponseCode
        code: Integer
    }
}
