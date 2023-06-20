$version: "2"

namespace demo

use smithy4s.meta#refinement

@trait
@refinement(targetType: "foo.bar.AssignmentsDecoded")
structure protobufEncoded {}

@protobufEncoded
string Assignments

operation MyOperation {
    input := {
        @httpHeader("X-Assignments")
        assignments: Assignments
    }
}

service MyService {
    operations: [MyOperation]
}
