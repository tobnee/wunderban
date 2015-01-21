package wunderapp

import anorm._
import play.api.db.DB

object TaskStatus {
  import play.api.Play.current

  def all(): Map[Long, Status] = {
    DB.withConnection { implicit c =>
      SQL("select * from TaskSateMapping").map{row =>
        (row[Long]("id"), row[String]("state"))
      }.list()
        .map{ case(id, state) => (id, Status(state))}
        .toMap
    }
  }

  def update(id: Long, status: Status): Unit = {
    DB.withConnection { implicit c =>
      val params: Seq[NamedParameter] = Seq("id" -> id, "state" -> status.value)
      SQL("update TaskSateMapping set state = {state} where id = {id}")
        .on(params: _*).executeUpdate() match {
        case 0 =>
          SQL("insert into TaskSateMapping(id, state) values ({id}, {state})")
          .on(params: _*).executeInsert()
        case _ => ()
      }
    }
  }
}
