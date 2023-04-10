$version: "2"

namespace demo

@alloy#simpleRestJson
service ProductService {
    operations: [GetProduct, GetAllProducts]
    errors: [NotFoundError]
}

@http(uri: "/products/{id}", method: "GET")
@readonly
operation GetProduct {
    input := {
        @required
        @httpLabel
        id: Integer
    }
    output: Product
}

@error("client")
@httpError(404)
structure NotFoundError {
    @required
    message: String
}

@http(uri: "/products", method: "GET")
@readonly
operation GetAllProducts {
    input := {
        @httpQuery("limit")
        limit: Integer
        @httpQuery("skip")
        skip: Integer
    }
    output := {
        @required
        products: Products
        @required
        total: Integer
        @required
        skip: Integer
        @required
        limit: Integer
    }
}

list Products {
    member: Product
}

structure Product {
    @required
    id: Integer
    @required
    title: String
    @required
    price: Integer
    @required
    description: String
    thumbnail: String
    images: Images = []
}

list Images {
    member: String
}
