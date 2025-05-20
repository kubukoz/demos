package smithy4s.codegen.internals

import software.amazon.smithy.build.PluginContext
import software.amazon.smithy.build.SmithyBuildPlugin

class Smithy4s extends SmithyBuildPlugin {
  def getName(): String = "smithy4s"

  def execute(context: PluginContext): Unit = {
    val cfg = Smithy4sConfig.fromNode(context.getSettings()).toTry.get

    val generated = CodegenImpl.generate(
      context.getModel(),
      allowedNS = cfg.allowedNS,
      excludedNS = cfg.excludedNS,
    )

    generated.foreach { case (namespacePath, result) =>
      context
        .getFileManifest()
        .writeFile((namespacePath / (result.name + ".scala")).toNIO, result.content)
    }
  }

}
