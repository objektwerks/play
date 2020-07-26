package controllers

import javax.inject.{Inject, Singleton}

import play.api.Logging
import play.api.mvc._

import models._

@Singleton
class TodoController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with Logging {
  def add() = Action { implicit request: Request[AnyContent] =>
    logger.info(s"*** Todo controller request: $request")
    Redirect(controllers.Index.index())
  }
}