package object wunderapp {

  case class ListInfo(id: Long, listType: String, title: String, `type`: String)

  case class Task(id: Long, title: String, completed: Boolean, starred: Boolean)

  sealed abstract class Status(val value: String)
  case object Done extends Status("done")
  case object InProgress extends Status("inprogress")
  case object ToDo extends Status("todo")

  object Status {
    def apply(value: String) = value.toLowerCase match {
      case Done.value => Done
      case InProgress.value => InProgress
      case ToDo.value => ToDo
    }
  }

  case class WunderbanTask(status: Status, task: Task)

}
