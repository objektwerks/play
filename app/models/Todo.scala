package models

final case class Todo(task: String)

object Todo {
  import play.api.data.Form
  import play.api.data.Forms._

  private val todos = Set.empty[Todo]

  val todoForm = Form(
    mapping(
      "task" -> nonEmptyText
    )(Todo.apply)(Todo.unapply)
  )

  def add(todo: Todo): Set[Todo] = todos + todo

  def remove(todo: Todo): Set[Todo] = todos - todo

  def list(): Set[Todo] = todos
}