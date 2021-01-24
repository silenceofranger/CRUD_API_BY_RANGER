package API_SCALA_MONGO_AKKA.Client_API

//import akka.stream.impl.Stages.DefaultAttributes.limit
import org.mongodb.scala.Completed
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Indexes.ascending
import org.mongodb.scala.model.{Filters, Updates}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration

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
    val data = ClientRepo.clientDetails.find()
    Await.result(data.toFuture(),Duration.Inf)
    data.foreach(cl=>{
    Await.result(ClientRepo.clientDetails.deleteOne(equal("url",url)).toFuture,Duration.Inf)})
    val newData = ClientRepo.clientDetails.find()
    Await.result(newData.toFuture(),Duration.Inf)
    newData.toFuture()
  }

  def findByValue(value: Int): Future[Seq[Client]] = {
    val data = ClientRepo.clientDetails.find(Filters.equal("client_value",value)).toFuture()
    Await.result(data,Duration.Inf)
    data
  }

  def paging(pageNumber: Int, messagesPerPage: Int): Future[Seq[Client]] = {
    val data = ClientRepo.clientDetails.find().skip((pageNumber - 1) * messagesPerPage)
    .limit(messagesPerPage).sort(ascending("client_name")).toFuture()
    Await.result(data,Duration.Inf)
    data
  }

}
