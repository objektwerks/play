package ws

import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite

import play.api.libs.ws.WSClient

import scala.concurrent._
import scala.concurrent.duration._
import scala.language.postfixOps

class WSTest extends PlaySpec with GuiceOneServerPerSuite with ScalaFutures {
  val wsClient = app.injector.instanceOf(classOf[WSClient])

  "ws call" must {
    "get joke" in {
      val response = wsClient.url("http://api.icndb.com/jokes/random/").get()
      val body = Await.result(response, 3 seconds).body
      println(body)
      assert(body.nonEmpty)
    }
  }
}