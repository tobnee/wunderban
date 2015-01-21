package wunderapp

trait WunderlistJsonFormat {

  import play.api.libs.json._
  import play.api.libs.functional.syntax._

  implicit val listReads: Reads[ListInfo] = (
    (JsPath \ "id").read[Long] and
      (JsPath \ "list_type").read[String] and
      (JsPath \ "title").read[String] and
      (JsPath \ "type").read[String]
    )(ListInfo.apply _)

  implicit val taskReads = Json.reads[Task]

}
