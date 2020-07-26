package controllers

import javax.inject.{Inject, Singleton}

import play.api.Logging
import play.api.mvc._

@Singleton
class TodoController @Inject()(val messagesAction: MessagesActionBuilder,
                               val messagesController: MessagesControllerComponents)
  extends MessagesAbstractController(messagesController)
  with Logging {
  def add() = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    logger.info(s"*** TodoController request: $request")
    Redirect(routes.IndexController.index())
  }
}