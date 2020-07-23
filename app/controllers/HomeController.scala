package controllers

import javax.inject._

import play.api.Logging
import play.api.mvc._

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with Logging {
  def index() = Action { implicit request: Request[AnyContent] =>
    logger.info(s"*** index request: $request")
    Ok(views.html.index())
  }
}