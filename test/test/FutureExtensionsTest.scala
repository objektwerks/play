package test

import org.scalatest.funsuite.AnyFunSuite

object FutureExtensions {
  import akka.actor.ActorSystem
  import akka.pattern.after
  import scala.concurrent.{ExecutionContext, Future}
  import scala.concurrent._
  import scala.concurrent.duration._

  implicit class Extensions[T](future: Future[T]) {
    def withTimeout(futureFailed: => Throwable)
                   (implicit system: ActorSystem, ec: ExecutionContext, timeout: FiniteDuration): Future[T] = {
      Future.firstCompletedOf(
        Seq(
          future,
          after(timeout, system.scheduler)(Future.failed(futureFailed))
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

class FutureExtensionsTest extends AnyFunSuite {
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._
  import scala.language.postfixOps
  import scala.util.{Failure, Success}

  test("future with timeout > success") {
    FutureExtensions.test(akkaTimeout = 3 seconds, futureSleep = 2 seconds).onComplete {
      case Success(result) => assert(result)
      case Failure(_) => fail
    }
  }

  test("future with timeout > failure") {
    FutureExtensions.test(akkaTimeout = 2 seconds, futureSleep = 3 seconds).onComplete {
      case Success(_) => fail
      case Failure(error) => assert(error.getMessage.nonEmpty)
    }
  }
}