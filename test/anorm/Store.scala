package anorm

import com.typesafe.config.Config

import java.sql.DriverManager

object Store {
  def apply(conf: Config): Store = new Store(conf)
}

class Store(conf: Config) {
  implicit val connection = DriverManager.getConnection(conf.getString("h2.url"))

  import Todo._

  def addTodo(todo: Todo): Option[Long] = 
    SQL("insert into todo(task) values ({task})")
      .on("task" -> todo.task)
      .executeInsert()

  def updateTodo(todo: Todo): Int = 
    SQL(s"update todo set task = '${todo.task}' where id = ${todo.id}")
      .executeUpdate()

  def listTodos(): List[Todo] =
    SQL("SELECT id, task FROM todo")
      .as(todoRowParser.*)
}