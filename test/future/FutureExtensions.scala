package future

import akka.actor.ActorSystem
import akka.pattern.after

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

object FutureExtensions {
  implicit class Extensions[T](future: Future[T]) {
    def withTimeout(timeoutException: => Throwable)
                   (implicit system: ActorSystem,
                    ec: ExecutionContext,
                    timeout: FiniteDuration): Future[T] = {
      Future.firstCompletedOf(
        Seq(
          future,
          after(timeout, system.scheduler)(Future.failed(timeoutException))
        )
      )
    }
  }
}