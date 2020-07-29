package test

import org.scalatest.funsuite.AnyFunSuite

import play.api.libs.concurrent.Futures._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}
import scala.concurrent.duration.{DurationInt, FiniteDuration}
import scala.languageFeature.postfixOps

class TimeoutFutureTest extends AnyFunSuite {
  def square(value: Int, timeout: FiniteDuration): Future[Int] = {
    val timeoutFuture = Futures.timeout(value, timeout)
    val resultFuture = Future {
      value * value
    }
    Future.firstCompletedOf(List(resultFuture, timeoutFuture))
  }

  test("future") {
    square(value = 3, timeout = 1 second)
  }
}