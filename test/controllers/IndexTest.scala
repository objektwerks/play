package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

class IndexTest extends PlaySpec with GuiceOneAppPerTest with Injecting {
  val contentTextHtml = "text/html"
  val content = "Hello, Play!"
  
  "HomeController GET" should {
    "render the index page from a new instance of controller" in {
      val controller = new Index(stubControllerComponents())
      val home = controller.index().apply(FakeRequest(GET, "/"))
      status(home) mustBe OK
      contentType(home) mustBe Some(contentTextHtml)
      contentAsString(home) must include (content)
    }

    "render the index page from the application" in {
      val controller = inject[Index]
      val home = controller.index().apply(FakeRequest(GET, "/"))
      status(home) mustBe OK
      contentType(home) mustBe Some(contentTextHtml)
      contentAsString(home) must include (content)
    }

    "render the index page from the router" in {
      val request = FakeRequest(GET, "/")
      val home = route(app, request).get
      status(home) mustBe OK
      contentType(home) mustBe Some(contentTextHtml)
      contentAsString(home) must include (content)
    }
  }
}