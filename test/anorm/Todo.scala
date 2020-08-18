package anorm

case class Todo(id: Long = 0, task: String)

object Todo {
  val todoRowParser = Macro.namedParser[Todo]
}