package models

final case class Todo(task: String, done: Boolean = false)

object Todo {
  import play.api.data.Form
  import play.api.data.Forms._

  private val todos = List.empty[Todo]

  val todoForm = Form(
    mapping(
      "task" -> nonEmptyText,
      "done"  -> boolean
    )(Todo.apply)(Todo.unapply)
  )

  def add(todo: Todo): List[Todo] = todos :+ todo

  def list(): List[Todo] = todos
}