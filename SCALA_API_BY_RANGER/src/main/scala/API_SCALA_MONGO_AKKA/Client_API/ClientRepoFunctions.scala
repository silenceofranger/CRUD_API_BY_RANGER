package API_SCALA_MONGO_AKKA.Client_API

import org.mongodb.scala.Completed
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.{Filters, Updates}
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.{Duration}

object ClientRepoFunctions {

  def getCollection: Future[Seq[Client]] = {
    val data=ClientRepo.clientDetails.find().toFuture()
    Await.result(data,Duration.Inf)
    data
  }

  def insertClient(cl:Client): Future[Completed] ={
    val commClient=ClientRepo.clientDetails.insertOne(cl).toFuture()
    Await.result(commClient,Duration.Inf)
    commClient
  }

  def updateField(name: String, url: String):Future[Long] = {
    val count=ClientRepo.clientDetails.count(equal("client_name", s"$name"))
    val data = ClientRepo.clientDetails.updateMany(equal("client_name", s"$name"),
      Updates.set("client_url", url)).toFuture()
    Await.result(data,Duration.Inf)
    count.toFuture()
  }

  def deleteClient(url: String): Future[Seq[Client]] = {
    val data = ClientRepo.clientDetails.find(equal("client_url",url))
    Await.result(data.toFuture(),Duration.Inf)
    data.foreach(cl=>{
    Await.result(ClientRepo.clientDetails.deleteOne(equal("client_name",cl.client_name)).toFuture,Duration.Inf)})
    data.toFuture()
  }

  def findByValue(value: Int): Future[Seq[Client]] = {
    val data =ClientRepo.clientDetails.find(Filters.lte("client_value",value)).toFuture()
    Await.result(data,Duration.Inf)
    data
  }

}
