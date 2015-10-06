package messages

/**
 * Created by rahul on 23/09/15.
 */


/** Sample Query JSON .
  *  {
      "aggregate": {
        "sum": ["m1"],
        "avg": [],
        "count": []
      },
      "filter": [],
      "select": "u_geo_country",
      "groupby": "u_geo_country",
      "orderby": ["u_geo_country"],
      "starttime": "2015-08-01 00:00:00",
      "endtime": "2015-08-08 00:00:00"
    }
  *
  */

case class Aggregate(sum:Array[String], avg:Array[String], count:Array[String])

case class QueryParam(aggrigate:Aggregate,
                      filter:Array[String],
                      select:String,
                      groupby:String,
                      orderby:Array[String],
                      starttiem:String,
                      endtime:String)

class Messages {

}
