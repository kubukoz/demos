import bsp.BuildServer

import bsp.BuildServerCapabilities
import bsp.BuildServerOperation.BuildInitialize
import bsp.BuildServerOperation.BuildShutdown
import bsp.CompileProvider
import bsp.InitializeBuildResult
import bsp.LanguageId
import cats.effect.IO
import cats.effect.IOApp
import fs2.io.file.Files
import fs2.io.file.Path
import jsonrpclib.CallId
import jsonrpclib.Codec
import jsonrpclib.fs2.CancelTemplate
import jsonrpclib.fs2.FS2Channel
import jsonrpclib.fs2.lsp
import bsp.BuildServerOperation.WorkspaceBuildTargets
import bsp.WorkspaceBuildTargetsResult
import bsp.BuildTarget
import bsp.BuildTargetIdentifier
import bsp.URI
import bsp.BuildTargetTag
import bsp.BuildTargetCapabilities
import bsp.BuildServerOperation.BuildTargetSources
import bsp.SourcesResult
import bsp.SourceItem
import bsp.SourceItemKind
import bsp.SourcesItem
import java.nio.file.Paths
import bsp.BuildServerOperation.BuildTargetDependencySources
import bsp.DependencySourcesResult
import bsp.BuildServerOperation.BuildTargetCompile
import bsp.CompileResult
import bsp.StatusCode

object SampleServer extends IOApp.Simple {
  val cancelEndpoint = CancelTemplate.make[CallId]("$/cancel", identity, identity)

  def server(log: String => IO[Unit]) =
    BSPBuilder
      .create(BuildServer)
      .withHandler(BuildInitialize) { input =>
        log(s"omg! received a valid request with inputs $input") *>
          IO {
            InitializeBuildResult(
              displayName = "jk-sample-server",
              "1.0.0",
              bspVersion = "2.2.0-",
              capabilities = BuildServerCapabilities(
                compileProvider = Some(
                  CompileProvider(languageIds = List(LanguageId("scala")))
                ),
                dependencySourcesProvider = true,
              ),
            )
          }

      }
      .withHandler(BuildShutdown) { _ =>
        log("received a shutdown request") *>
          IO.unit
      }
      .withHandler(WorkspaceBuildTargets) { _ =>
        log("received a targets request") *>
          IO(
            WorkspaceBuildTargetsResult(
              List(
                BuildTarget(
                  id = BuildTargetIdentifier(
                    URI("proj://hello")
                  ),
                  tags = List(BuildTargetTag.LIBRARY),
                  languageIds = List(LanguageId("scala")),
                  dependencies = Nil,
                  capabilities = BuildTargetCapabilities(),
                  displayName = Some("jk-hello"),
                  baseDirectory = Some(
                    URI(Paths.get("./").toAbsolutePath().toUri().toString())
                  ),
                )
              )
            )
          )
      }
      .withHandler(BuildTargetSources) { _ =>
        log("received a sources request") *>
          IO(
            SourcesResult(
              List(
                SourcesItem(
                  target = BuildTargetIdentifier(
                    URI("proj://hello")
                  ),
                  sources = List(
                    SourceItem(
                      uri = URI(Paths.get("./hello").toAbsolutePath().toUri().toString()),
                      kind = SourceItemKind.DIRECTORY,
                      generated = false,
                    )
                  ),
                ),
                SourcesItem(
                  target = BuildTargetIdentifier(
                    URI("proj://hello")
                  ),
                  sources = List(
                    SourceItem(
                      uri = URI(Paths.get("./hello2").toAbsolutePath().toUri().toString()),
                      kind = SourceItemKind.DIRECTORY,
                      generated = false,
                    )
                  ),
                ),
              )
            )
          )
      }
      .withHandler(BuildTargetDependencySources) { params =>
        log(s"received dep sources params: $params") *>
          IO {
            DependencySourcesResult(Nil)
          }
      }
      .withHandler(BuildTargetCompile) { params =>
        log(s"received compile params: $params") *>
          IO {
            CompileResult(
              statusCode = StatusCode.OK
            )
          }
      }

  def run: IO[Unit] = {
    val impl = server(msg =>
      fs2.Stream(msg + "\n").through(Files[IO].writeUtf8(Path("log.txt"))).compile.drain
    )

    FS2Channel[IO](cancelTemplate = Some(cancelEndpoint))
      .flatMap(impl.bind)
      .flatMap(channel =>
        fs2
          .Stream
          .eval(IO.never) // running the server forever
          .concurrently(
            fs2
              .io
              .stdin[IO](512)
              .through(lsp.decodeMessages)
              // .broadcastThrough(_.map(_.toString).through(Files[IO].writeUtf8(Path("log.txt"))))
              .through(channel.inputOrBounce)
          )
          .concurrently(channel.output.through(lsp.encodeMessages).through(fs2.io.stdout[IO]))
      )
      .compile
      .drain
      .guarantee(IO.consoleForIO.errorln("Terminating server"))
  }

}
