package controllers

import javax.inject.{Inject, Singleton}

import play.api.Logging
import play.api.mvc._

import models._

@Singleton
class TodoController @Inject()(messagesAction: MessagesActionBuilder,
                               messagesController: MessagesControllerComponents)
  extends MessagesAbstractController(messagesController)
  with Logging {
  def add() = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    logger.info(s"*** TodoController request: $request")
    Todo.todoForm.bindFromRequest().fold(
      formWithErrors => {
        logger.info(s"*** TodoController errors: $formWithErrors")
        BadRequest(views.html.index(formWithErrors, Todo.list))
      },
      todo => {
        logger.info(s"*** TodoController success: $todo")
        Todo.add(todo)
        Redirect(routes.IndexController.index())
      }
    )
  }
}