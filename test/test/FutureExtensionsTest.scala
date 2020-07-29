package test

import org.scalatest.funsuite.AnyFunSuite

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