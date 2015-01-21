package controllers

import play.api.Play.current
import play.api.mvc._
import wunderapp.{TaskStatus, Wunderlist}

object Application extends Controller {

  lazy val apiToken =
    play.api.Play.current.configuration.getString("wunderlist.token")
      .getOrElse(throw new IllegalStateException("no config key wunderlist.token"))

  implicit val context =
    play.api.libs.concurrent.Execution.Implicits.defaultContext

  def lists = Action.async {
    Wunderlist
      .lists(apiToken)
      .map(list => Ok(views.html.lists(list)))
  }

  def tasks(id: Long) = Action.async {
    Wunderlist
      .task(apiToken, id)
      .map(list => Ok(views.html.tasks(id.toString, list)))
  }

  def board(id: Long) = Action.async {
    Wunderlist.taskWithStatus(apiToken, id).map { tasks =>
      val (todo, rest) = tasks.partition(_.status == wunderapp.ToDo)
      val (inProg, done) = tasks.partition(_.status == wunderapp.InProgress)
      Ok(views.html.board(id.toString, todo.map(_.task), inProg.map(_.task)))
    }
  }

  def updateTaskStatus() = Action(parse.json) { request =>
    val body = request.body
    val id = (body \ "id").as[Long]
    val status = wunderapp.Status((body \ "status").as[String])
    TaskStatus.update(id, status)
    Ok
  }

}