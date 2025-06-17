$version: "2"

namespace hello

use alloy#simpleRestJson

@simpleRestJson
service WeatherService {
    operations: [
        GetWeather
        PostCity
    ]
}

@http(method: "GET", uri: "/weather/{city}")
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
    }
}

@http(method: "POST", uri: "/cities")
operation PostCity {
    input := {
        @required
        city: String
    }

    output := {
        @required
        message: String
    }
}
