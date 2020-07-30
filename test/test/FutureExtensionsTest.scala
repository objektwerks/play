package test

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class FutureExtensionsTest extends AnyFunSuite with Matchers {
  import scala.annotation.tailrec
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._
  import scala.language.postfixOps
  import scala.util.{Failure, Success}

  test("future with timeout > success") {
    FutureExtensions.test(akkaTimeout = 3 seconds, futureSleep = 2 seconds).onComplete {
      case Success(result) => result shouldBe true
      case Failure(_) => fail
    }
  }

  test("future with timeout > failure") {
    FutureExtensions.test(akkaTimeout = 2 seconds, futureSleep = 3 seconds).onComplete {
      case Success(_) => fail
      case Failure(error) => error.getMessage.nonEmpty shouldBe true
    }
  }

  test("future factorial > success") {
    import akka.actor.ActorSystem
    import scala.concurrent._
    import FutureExtensions._

    implicit val system = ActorSystem("test")
    implicit val dispatcher = system.dispatcher
    implicit val timeout = 3 seconds

    Future {
      factorial(9)
    }.withTimeout(new TimeoutException("Future timed out!")).onComplete {
      case Success(result) => result shouldBe 362880
      case Failure(_) => fail
    }
  }

  @tailrec
  private def factorial(n: Long, acc: Long = 1): Long = n match {
    case i if i < 1 => acc
    case _ => factorial(n - 1, acc * n)
  }
}