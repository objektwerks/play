package route

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._

class RoutesTest extends PlaySpec with GuiceOneAppPerTest {
  "GET /" should {
    "via routes" in {
      val request = FakeRequest(GET, "/")
      val home = route(app, request).get
      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Todos")
    }
  }
}