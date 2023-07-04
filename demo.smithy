$version: "2"

namespace hello

use alloy#simpleRestJson

@simpleRestJson
service WeatherService {
    operations: [
        GetWeather
        NoOutput
        OptionalOutput
    ]
}

@http(method: "GET", uri: "/weather")
@readonly
operation GetWeather {
    output := {
        @required
        record: RecordsNestedUnion
    }
}

union RecordsNestedUnion {
    id: String
    myDetails: StringOrIntOrStruct
}

union StringOrIntOrStruct {
    name: String
    age: Integer
    address: Address
}

structure Address {
    @required
    street: String
}

@http(method: "GET", uri: "/no-output")
@readonly
operation NoOutput {
    input := {
        @required
        @httpQuery("id")
        id: Integer
    }
}

@http(method: "GET", uri: "/optional-output")
@readonly
operation OptionalOutput {
    output := {
        @httpPayload
        bod: String
    }
}
