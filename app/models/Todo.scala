package models

final case class Todo(task: String, done: Boolean = false)

object Todo {
  import play.api.data.Form
  import play.api.data.Forms._

  val todoForm = Form(
    mapping(
      "task" -> nonEmptyText,
      "done"  -> boolean
    )(Todo.apply)(Todo.unapply)
  )
}