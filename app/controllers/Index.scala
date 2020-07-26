package controllers

import javax.inject.{Inject, Singleton}

import play.api.Logging
import play.api.mvc._

import models._

@Singleton
class Index @Inject()(val messagesAction: MessagesActionBuilder,  val controllerComponents: ControllerComponents)
  extends BaseController with Logging {
  def index() = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    logger.info(s"*** Index controller request: $request")
    Ok(views.html.index(Todo.todoForm, Todo.list))
  }
}