$version: "2"

namespace demosmithy

use alloy#simpleRestJson

structure Foo {
    @required
    name: String
}

@simpleRestJson
service GreetService {
    operations: [
        Greet
    ]
}

@http(method: "POST", uri: "/greet")
operation Greet {
    input := {
        @required
        name: String
    }

    output := {
        @required
        greeting: String
    }
}
