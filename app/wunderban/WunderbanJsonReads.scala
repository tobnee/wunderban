package wunderban

import play.api.libs.json._
import play.api.libs.functional.syntax._

trait WunderbanJsonReads {

  implicit val taskInfoRead: Reads[ListInfo] = (
     (JsPath \ "id").read[Long] and
     (JsPath \ "title").read[String]
    )(ListInfo.apply _)


}
