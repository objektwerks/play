package models

final case class Todo(task: String)

object Todo {
  import play.api.data.Form
  import play.api.data.Forms._

  private var todos = Set.empty[Todo]

  val todoForm = Form(
    mapping(
      "task" -> nonEmptyText
    )(Todo.apply)(Todo.unapply)
  )

  def add(todo: Todo): Unit = {
    todos = todos + todo
  }

  def remove(todo: Todo): Unit = {
    todos = todos - todo
  }

  def list: Set[Todo] = todos
}