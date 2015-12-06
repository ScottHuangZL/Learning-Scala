package models

case class Task(id: Int, name: String)

object Task {
  private var taskList: List[Task] = List(Task(1, "Scott Huang"))

  def all: List[Task] = {
    taskList
  }

  def add(taskName: String) = {
    val newId: Int = try {
      taskList.last.id + 1
    } catch {
      case e: NoSuchElementException => 1
      case _: Throwable => 1
    }
    taskList = taskList ++ List(Task(newId, taskName))
  }

  def delete(taskId: Int) = {
    taskList = taskList.filterNot(task => task.id == taskId)
  }
}