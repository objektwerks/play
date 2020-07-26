package controllers

import javax.inject.{Inject, Singleton}

import play.api.Logging
import play.api.mvc._

import models._

@Singleton
class Index @Inject()(val controllerComponents: ControllerComponents) extends BaseController with Logging {
  def index() = Action { implicit request: Request[AnyContent] =>
    logger.info(s"*** Index controller request: $request")
    Ok(views.html.index(Todo.list))
  }
}