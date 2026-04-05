package demo

import cats.effect.unsafe.IORuntime
import scala.concurrent.ExecutionContext
import scala.scalanative.concurrent.QueueExecutionContextImpl
import cats.effect.unsafe.IORuntimeConfig
import cats.effect.unsafe.Scheduler
import scala.concurrent.duration.FiniteDuration
import scala.scalanative.concurrent.NativeExecutionContext
import scala.collection.mutable.Queue

trait TaskRunner {
  def runPendingTasks(): Unit
}

def mkCatsRuntime(): (IORuntime, TaskRunner) = {
  val tasks = Queue.empty[Runnable]
  val runner =
    new TaskRunner {
      def runPendingTasks(): Unit =
        tasks.dequeueAll(_ => true).foreach { task =>
          info("Running pending task")
          task.run()
        }
    }

  val compute: ExecutionContext =
    new {

      def execute(runnable: Runnable): Unit = {
        info("execute called on compute execution context")
        tasks.enqueue(runnable)
      }

      def reportFailure(cause: Throwable): Unit = {
        info(s"reportFailure called with cause: ${cause.getMessage}")
        ???
      }
    }
  val blocking =
    new ExecutionContext {
      def execute(runnable: Runnable): Unit = {
        info("execute called on blocking execution context")
        ???
      }
      def reportFailure(cause: Throwable): Unit = {
        info(s"reportFailure called with cause: ${cause.getMessage}")
        ???
      }
    }

  val scheduler: Scheduler =
    new Scheduler {
      def monotonicNanos(): Long = {
        info("monotonicNanos called")
        ???
      }

      def nowMillis(): Long = {
        info("nowMillis called")
        ???
      }

      def sleep(delay: FiniteDuration, task: Runnable): Runnable = {
        info(s"sleep called with delay: ${delay.toMillis} ms")
        ???
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
