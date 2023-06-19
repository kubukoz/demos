$version: "2"

namespace hello

use alloy#simpleRestJson

@simpleRestJson
service WeatherService {
    operations: [GetWeather]
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
