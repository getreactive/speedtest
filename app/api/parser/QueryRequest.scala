package api.parser

import java.util

import api.thrift.{JResults, JQuery}
import messages.QueryParam
import org.apache.thrift.protocol.{TBinaryProtocol, TProtocol}
import org.apache.thrift.transport.{TSocket, TTransport}
import scala.collection.JavaConversions._

/**
 * Created by rahul on 23/09/15.
 */
object QueryRequest {

  /** A thriftMarshaller who uses our JSON query.
    *
    *
    * @param queryparam the QueryParam Query parameter
    *
    */

  def thriftMarshaller(queryparam:QueryParam) = {

    //val transport : TTransport = new TSocket("localhost",9090)
    val transport : TTransport = new TSocket("nitrodb-prod-m.cloudapp.net",9090)
    transport.open()
    val protocol: TProtocol = new TBinaryProtocol(transport)
    val client:api.thrift.QueryService.Client = new api.thrift.QueryService.Client(protocol)
    val query = new JQuery()
    val li = new util.ArrayList[String]()
    val filterdata = new util.ArrayList[api.thrift.JFilter]()
    val agg = new api.thrift.JAggregate()
    val sumlist = new util.ArrayList[String]()
    val countlist = new util.ArrayList[String]()
    val avglist = new util.ArrayList[String]()

    val sumAggrList = queryparam.aggrigate.sum
    val countAggrList = queryparam.aggrigate.count
    val avgAggrList = queryparam.aggrigate.avg

    val selectDim = queryparam.select
    val orderbyDim = queryparam.orderby
    val groupbyDim = queryparam.groupby

    if(sumAggrList != 0) {
      for ((x, i) <- sumAggrList.view.zipWithIndex) {
        sumlist.add(x)
      }
      agg.setSum(sumlist)
    }

    if(countAggrList != 0) {
      for ((x, i) <- countAggrList.view.zipWithIndex) {
        countlist.add(x)
      }
      agg.setCount(countlist)
    }

    if(avgAggrList != 0) {
      for ((x, i) <- avgAggrList.view.zipWithIndex) {
        avglist.add(x)
      }
      agg.setAvg(avglist)
    }

    li.add("host")
    query.setOrderby(orderbyDim.toList)
    query.setSelect(selectDim)
    query.setGroupby(groupbyDim)
    query.setStarttime("2015-09-01 00:00:00")
    query.setEndtime("2015-09-02 00:00:00")
    query.setFilter(filterdata)
    query.setAggregate(agg)
    val result  = client.getresults(query)
    transport.close()
    //println("requestparam.aggrigate ",result.getSchema)
    //println("requestparam.aggrigate ",result.getResults)

    result
  }
}
