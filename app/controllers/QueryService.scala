package controllers

import javax.inject.Inject

import api.parser.QueryRequest._
import play.api.cache.CacheApi
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.{Json, _}
import play.api.mvc.{Action, BodyParsers, Controller}
import messages._
import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.concurrent.duration._

/**
 * Created by rahul on 23/09/15.
 */

class QueryService  @Inject() (cache: CacheApi) extends Controller {

  val cacheDuration = 1.day

  implicit val AggrigateReader: Reads[Aggregate] = (
    (JsPath \ "sum").read[Array[String]] and
    (JsPath \ "avg").read[Array[String]] and
    (JsPath \ "count").read[Array[String]]
    )(Aggregate.apply _)

  implicit val QueryParamReader: Reads[QueryParam] = (
    (JsPath \"aggregate").read[Aggregate] and
      (JsPath \ "filter").read[Array[String]] and
      (JsPath \ "select").read[String] and
      (JsPath \ "groupby").read[String] and
      (JsPath \ "orderby").read[Array[String]] and
      (JsPath \ "starttime").read[String] and
      (JsPath \ "endtime").read[String]
    )(QueryParam.apply _)

  /** API call POST   /api/query */

  def options(path:String) = Action { Ok("")}

  def query = Action(BodyParsers.parse.json) { request =>{

      val requestParamResult = request.body.validate[QueryParam]
      requestParamResult.fold(
        errors => {
          BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors)))
        },
        requestparam => {
          val result = thriftMarshaller(requestparam)
          val resultschema = Seq("name","value")//result.getSchema.asScala.toSeq

          val resultdata = result.getResults.asScala.toSeq.map(data=> {
            data.asScala.toSeq
            }
          )

          val returnData = mutable.ArrayBuffer[Map[String,String]]()

          resultdata.map(data=>{

            var localmap = Map[String,String]()

            for((x,i) <- resultschema.view.zipWithIndex){

              val _localmap = resultschema(i)->data(i)
              localmap += _localmap
            }

          /* val dataMap = Map(resultschema(0)->data(0),
                          resultschema(1)->data(1),
                          resultschema(2)->data(2))*/

            returnData += localmap

          })

          Ok(Json.toJson(returnData.toSeq))
        }

      )
    }
  }


}
