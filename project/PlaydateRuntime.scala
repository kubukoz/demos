import sbt._
import sbt.VirtualAxis
import sbt.internal.ProjectMatrix
import scala.scalanative.sbtplugin.ScalaNativePlugin

sealed abstract class PlaydateRuntime(val suffix: String, val dirSuffix: String)
  extends VirtualAxis.WeakAxis {
  override val idSuffix = suffix
  override val directorySuffix = dirSuffix
}

object PlaydateRuntime {
  case object Device extends PlaydateRuntime("Device", "device")
  case object Simulator extends PlaydateRuntime("Simulator", "simulator")

  val all: Seq[PlaydateRuntime] = Seq(Device, Simulator)

  implicit class ProjectMatrixPlaydateOps(val pm: ProjectMatrix) extends AnyVal {

    def playdateRow(runtime: PlaydateRuntime)(process: Project => Project): ProjectMatrix =
      pm.customRow(
        scalaVersions = Seq("3.8.3"),
        axisValues = Seq(VirtualAxis.native, runtime),
        process = _.enablePlugins(ScalaNativePlugin).configure(process),
      )

  }
}
