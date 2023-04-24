import scala.scalanative.build.BuildTarget

import scala.scalanative.build.Mode

val root = crossProject(JVMPlatform, NativePlatform)
  .in(file("."))
  .settings(
    scalaVersion := "3.2.2"
  )
  .nativeSettings(nativeConfig ~= (_.withTargetTriple("wasm32-unknown-unknown-wasm")))
