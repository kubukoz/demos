namespace hello

use smithy4s.api#simpleRestJson

@simpleRestJson
service WeatherService {
  operations: [GetWeather]
}

@http(method: "GET", uri: "/weather/{city}")
@readonly
operation GetWeather {
  input: GetWeatherInput,
  output: GetWeatherOutput
}

structure GetWeatherInput {
  @required
  @httpLabel
  city: String
}

structure GetWeatherOutput {
  @required
  weather: String
}
