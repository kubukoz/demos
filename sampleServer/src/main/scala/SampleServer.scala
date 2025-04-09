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
import bsp.scala_.ScalaBuildServerOperation.BuildTargetScalacOptions
import bsp.scala_.ScalacOptionsResult
import bsp.scala_.ScalaBuildServerOperation.BuildTargetScalaMainClasses
import bsp.scala_.ScalaMainClassesResult
import bsp.scala_.ScalaBuildServerOperation.BuildTargetScalaTestClasses
import bsp.scala_.ScalaTestClassesResult
import bsp.BuildServerOperation.BuildTargetCleanCache
import bsp.CleanCacheResult
import bsp.scala_.ScalacOptionsItem
import bsp.DependencySourcesItem
import bsp.BuildTargetData
import smithy4s.Document
import bsp.scala_.ScalaPlatform
import bsp.scala_.ScalaBuildTarget
import bsp.BuildServerOperation.OnBuildExit

object SampleServer extends IOApp.Simple {
  val cancelEndpoint = CancelTemplate.make[CallId]("$/cancel", identity, identity)

  val targetId = BuildTargetIdentifier(URI("proj://hello"))

  def server(log: String => IO[Unit]) =
    BSPBuilder
      .create(BuildServer)
      .withHandler(BuildInitialize) { input =>
        log(s"received a valid request with inputs $input") *>
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
        log("received a shutdown request")
      }
      .withHandler(OnBuildExit)(_ =>
        // this doesn't actually get called. Metals bug? (per spec)
        log("received a build exit notification") *>
          IO(sys.exit(0))
      )
      .withHandler(WorkspaceBuildTargets) { _ =>
        log("received a targets request") *>
          IO(
            WorkspaceBuildTargetsResult(
              List(
                BuildTarget(
                  id = targetId,
                  tags = List(BuildTargetTag.LIBRARY),
                  languageIds = List(LanguageId("scala")),
                  dependencies = Nil,
                  capabilities = BuildTargetCapabilities(
                    canCompile = Some(true),
                    canRun = Some(true),
                    canTest = Some(true),
                    canDebug = Some(true),
                  ),
                  displayName = Some("jk-hello"),
                  baseDirectory = Some(
                    URI(Paths.get("./").toAbsolutePath().toUri().toString())
                  ),
                  data = Some(
                    BuildTargetData(
                      Document.encode(
                        ScalaBuildTarget(
                          scalaOrganization = "org.scala-lang",
                          scalaVersion = "3.7.0-RC1",
                          scalaBinaryVersion = "3.7",
                          platform = ScalaPlatform.JVM,
                          jars = Nil,
                          jvmBuildTarget = None,
                        )
                      )
                    )
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
                  target = targetId,
                  sources = List(
                    SourceItem(
                      uri = URI(Paths.get("./hello").toAbsolutePath().toUri().toString()),
                      kind = SourceItemKind.DIRECTORY,
                      generated = false,
                    )
                  ),
                ),
                SourcesItem(
                  target = targetId,
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
            DependencySourcesResult(
              List(
                DependencySourcesItem(
                  target = targetId,
                  sources = Nil,
                )
              )
            )
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
      .withHandler(BuildTargetScalacOptions) { params =>
        log(s"received scalac options params: $params") *>
          IO {
            ScalacOptionsResult(
              List(
                ScalacOptionsItem(
                  target = targetId,
                  options = Nil,
                  classpath = Nil,
                  classDirectory = Paths.get("./out").toAbsolutePath().toUri().toString(),
                )
              )
            )
          }
      }
      .withHandler(BuildTargetScalaMainClasses) { params =>
        log(s"received main classes params: $params") *>
          IO {
            ScalaMainClassesResult(Nil)
          }
      }
      .withHandler(BuildTargetScalaTestClasses) { params =>
        log(s"received test classes params: $params") *>
          IO {
            ScalaTestClassesResult(Nil)
          }
      }
      .withHandler(BuildTargetCleanCache) { params =>
        log(s"received clean cache params: $params") *>
          IO(CleanCacheResult(cleaned = true, message = Some("cleaned cache")))
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
