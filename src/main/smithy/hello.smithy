$version: "2"

namespace hello

@mixin
structure HasContext {
  context: String = "default"
}

structure Input with [HasContext] {}
