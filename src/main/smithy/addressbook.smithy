$version: "2"

namespace tutorial

@trait(selector: "member")
integer protobufIndex

@trait(selector: "structure")
structure protobuf {}

@protobuf
structure Person {
    @required
    @protobufIndex(1)
    name: String
    @required
    @protobufIndex(2)
    id: Integer
    @protobufIndex(3)
    email: String
    @required
    @protobufIndex(4)
    phones: Phones
}

list Phones {
    member: Phone
}

@protobuf
structure Phone {
    @required
    @protobufIndex(1)
    number: String
}
