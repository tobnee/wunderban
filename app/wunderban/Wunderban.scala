package wunderban

import play.api.libs.json.JsValue
import play.api.libs.ws.WS

import scala.concurrent.{ExecutionContext, Future}

object Wunderban extends WunderbanJsonReads  {

  implicit val app = play.api.Play.current

  def list(apiToken: String)(implicit ec: ExecutionContext) = {
    WS.url("https://a.wunderlist.com/api/v1/lists")
      .withHeaders("X-Access-Token" -> apiToken,
                   "X-Client-ID" -> "498d3ffc44ddfa2f275b")
      .get()
      .map { response =>
      response.json.as[Seq[ListInfo]]
    }
  }

  def tasks(apiToken: String, id: Long)(implicit ec: ExecutionContext)  = {
    WS.url("https://a.wunderlist.com/api/v1/tasks")
      .withHeaders("X-Access-Token" -> apiToken,
        "X-Client-ID" -> "498d3ffc44ddfa2f275b")
      .withQueryString("list_id" -> id.toString)
      .get()
      .map { response =>
      response.json.as[Seq[JsValue]]
    }
  }

}
