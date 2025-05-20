$version: "2"

namespace com.kubukoz

use alloy#simpleRestJson

@simpleRestJson
service WeatherService {
    operations: [
        GetWeather
    ]
}

/// nice
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
        weather: Weather
    }
}

union Weather {
    good: Good
    bad: Bad
}

structure Good {
    @required
    goodness: String
}

structure Bad {
    veryBad: Boolean = true
}
