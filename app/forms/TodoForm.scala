package forms

import play.api.data.Form
import play.api.data.Forms._

final case class TodoData(task: String, done: Boolean)

object TodoForm {
  val todoForm = Form(
    mapping(
      "task" -> nonEmptyText,
      "done"  -> boolean
    )(TodoData.apply)(TodoData.unapply)
  )
}