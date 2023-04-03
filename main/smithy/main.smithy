$version: "2"
namespace demo


@alloy#simpleRestJson
service ProductService {
  operations: [GetProduct],
  errors: [NotFoundError]
}


@http(uri: "/products/{id}", method: "GET")
@readonly
operation GetProduct {
  input := {
    @required @httpLabel id: Integer
  },
  output := {
    @required id: Integer
    @required title: String
    @required description: String
  }
}

@error("client")
@httpError(404)
structure NotFoundError {
  @required message: String
}
