package repositories

import models.Todo

object TodoRepository {
  private val todos = List.empty[Todo]

  def add(todo: Todo): List[Todo] = todos :+ todo

  def list(): List[Todo] = todos
}