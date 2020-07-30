package test

import akka.actor.ActorSystem
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.concurrent._

class FutureExtensionsTest extends AnyFunSuite with Matchers {
  import scala.annotation.tailrec
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._
  import scala.language.postfixOps
  import scala.util.{Failure, Success}

  private val config = ConfigFactory.load("test.conf")
  private val futureExtensionsDispatcherName = Some("future-extensions-dispatcher")

  test("future with timeout > success") {
    withTimeout(config = config,
                dispatcherName = futureExtensionsDispatcherName,
                akkaTimeout = 3 seconds,
                futureSleep = 2 seconds).onComplete {
      case Success(result) => result shouldBe true
      case Failure(_) => fail
    }
  }

  test("future with timeout > failure") {
    withTimeout(config = config,
                dispatcherName = futureExtensionsDispatcherName,
                akkaTimeout = 2 seconds,
                futureSleep = 3 seconds).onComplete {
      case Success(_) => fail
      case Failure(error) => error.getMessage.nonEmpty shouldBe true
    }
  }

  test("future factorial > success") {
    import akka.actor.ActorSystem
    import scala.concurrent._
    import FutureExtensions._

    implicit val system = ActorSystem("test", config)
    implicit val dispatcher = system.dispatchers.lookup("future-extensions-dispatcher")
    implicit val timeout = 3 seconds

    Future {
      factorial(9)
    }.withTimeout(new TimeoutException("Future timed out!")).onComplete {
      case Success(result) => result shouldBe 362880
      case Failure(_) => fail
    }
  }

  private def withTimeout(config: Config,
                          dispatcherName: Option[String],
                          akkaTimeout: FiniteDuration,
                          futureSleep: FiniteDuration): Future[Boolean] = {
    import FutureExtensions._

    implicit val system = ActorSystem("test", config)
    implicit val dispatcher = dispatcherName match {
      case Some(name) => system.dispatchers.lookup(name)
      case None => system.dispatcher
    }
    implicit val timeout = akkaTimeout

    lazy val future = Future {
      Thread.sleep(futureSleep.toMillis)
      true
    }
    future.withTimeout(new TimeoutException("Future timed out!"))
  }

  @tailrec
  private def factorial(n: Long, acc: Long = 1): Long = n match {
    case i if i < 1 => acc
    case _ => factorial(n - 1, acc * n)
  }
}