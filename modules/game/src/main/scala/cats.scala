package demo

import cats.effect.unsafe.IORuntime
import scala.concurrent.ExecutionContext
import scala.scalanative.concurrent.QueueExecutionContextImpl
import cats.effect.unsafe.IORuntimeConfig
import cats.effect.unsafe.Scheduler
import scala.concurrent.duration.FiniteDuration
import scala.scalanative.concurrent.NativeExecutionContext
import scala.collection.mutable.Queue
import scala.collection.mutable.PriorityQueue
import java.util.UUID

trait TaskRunner {
  def runPendingTasks(): Unit
}

def mkCatsRuntime(): (IORuntime, TaskRunner) = {
  val tasks = Queue.empty[Runnable]
  type Nanos = Long
  val scheduled = Queue.empty[(Nanos, Runnable)]

  def nowNanos(): Long = pdapiBindings.pd_getCurrentTimeMilliseconds().toLong * 1000000L

  val runner =
    new TaskRunner {
      def runPendingTasks(): Unit = {
        val now = nowNanos()
        val scheduledDue = scheduled.dequeueAll((targetNanos, _) => targetNanos <= now)
        // if scheduledDue.nonEmpty then info(s"Found ${scheduledDue.size} scheduled tasks due to run")
        // else if scheduledDue.isEmpty then info(
        //   s"No scheduled tasks due to run. Now is ${now}, next task is at ${scheduled.headOption.map(_._1)} (in ns: ${scheduled.headOption.map(_._1 - now)})"
        // )

        scheduledDue.foreach { case (_, task) =>
          // info(s"Running due scheduled task")
          task.run()
        }

        val pendingDue = tasks.dequeueAll(_ => true)

        // if pendingDue.nonEmpty then info(s"Found ${pendingDue.size} pending tasks to run")

        pendingDue.foreach { task =>
          // info("Running pending task")
          task.run()
        }
      }
    }

  val compute: ExecutionContext =
    new {

      def execute(runnable: Runnable): Unit =
        // info("execute called on compute execution context")
        tasks.enqueue(runnable)

      def reportFailure(cause: Throwable): Unit =
        // info(s"reportFailure called with cause: ${cause.getMessage}")
        cause.printStackTrace()
    }

  val blocking = compute

  val scheduler: Scheduler =
    new Scheduler {
      def monotonicNanos(): Long = nowNanos()

      def nowMillis(): Long = {
        import scalanative.unsafe.*
        val ms = stackalloc[CUnsignedInt]()
        val seconds = pdapiBindings.pd_getSecondsSinceEpoch(ms)
        seconds.toLong * 1000L + (!ms).toLong
      }

      def sleep(delay: FiniteDuration, task: Runnable): Runnable = {
        val scheduledAtNanos: Nanos = monotonicNanos()
        val targetNanos: Nanos = scheduledAtNanos + delay.toNanos
        // info(
        //   s"Scheduling task to run in ${delay.toMillis} ms (at targetNanos: $targetNanos, which is in ${targetNanos - monotonicNanos()} ns from now)"
        // )

        scheduled.enqueue((targetNanos, task))

        () =>
          // info(s"Cancelling scheduled task $task")
          scheduled.dequeueAll { case (_, t) => t eq task }
      }
    }

  (
    IORuntime.apply(
      compute = compute,
      blocking =
        blocking,
      scheduler = scheduler,
      shutdown = () => (),
      config = IORuntimeConfig(),
    ),
    runner,
  )

}
