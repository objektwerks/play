package controllers

import javax.inject.{Inject, Singleton}

import play.api.Logging
import play.api.mvc._

import models._

@Singleton
class IndexController @Inject()(val messagesAction: MessagesActionBuilder,
                                val cc: MessagesControllerComponents)
  extends MessagesAbstractController(cc)
  with Logging {
  def index() = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    logger.info(s"*** IndexController request: $request")
    Ok(views.html.index(Todo.todoForm, Todo.list))
  }
}