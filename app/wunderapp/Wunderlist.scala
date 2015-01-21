package wunderapp

import play.api.Application
import play.api.db.DB
import play.api.libs.ws.{WSRequestHolder, WS}

import scala.concurrent.{Future, ExecutionContext}

object Wunderlist extends WunderlistJsonFormat {

  def lists(apiToken: String)(implicit ec: ExecutionContext, app: Application): Future[List[ListInfo]] = {
    client("https://a.wunderlist.com/api/v1/lists", apiToken)
      .get()
      .map(_.json.as[List[ListInfo]])
  }

  def task(apiToken: String, taskId: Long)(implicit ec: ExecutionContext, app: Application): Future[List[Task]] = {
    client("https://a.wunderlist.com/api/v1/tasks", apiToken)
      .withQueryString("list_id" -> taskId.toString)
      .get()
      .map(_.json.as[List[Task]])
  }

  def taskWithStatus(apiToken: String, taskId: Long)(implicit ec: ExecutionContext, app: Application): Future[List[WunderbanTask]] = {
    task(apiToken, taskId).map { tasks =>
      lazy val status = TaskStatus.all().withDefaultValue(wunderapp.ToDo)
      tasks.map(task => WunderbanTask(status(task.id), task))
    }
  }

  def client(url: String, apiToken: String)(implicit app: Application): WSRequestHolder = {
    WS.url(url)
      .withHeaders(
        "X-Client-ID" -> "498d3ffc44ddfa2f275b",
        "X-Access-Token" -> apiToken
      )
  }

}
