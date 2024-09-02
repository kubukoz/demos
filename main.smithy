$version: "2"

namespace hello

structure MyHelloOld {
    @required
    f1: String
    @required
    f2: Integer
    f3: String
    // @smithy4s.meta#since("0.19.2")
    // f5: Boolean
    f4: Integer
}
