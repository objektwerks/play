package test

import akka.actor.ActorSystem
import akka.pattern.after
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent._
import scala.concurrent.duration._

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

  def test(akkaTimeout: FiniteDuration, futureSleep: FiniteDuration): Future[Boolean] = {
    implicit val system = ActorSystem("future-extensions")
    implicit val dispatcher = system.dispatcher
    implicit val timeout = akkaTimeout

    lazy val future = Future {
      Thread.sleep(futureSleep.toMillis)
      true
    }
    future.withTimeout(new TimeoutException("Future timed out!"))
  }
}