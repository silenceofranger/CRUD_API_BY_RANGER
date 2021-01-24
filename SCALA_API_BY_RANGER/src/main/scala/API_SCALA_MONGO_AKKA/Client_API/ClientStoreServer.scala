package API_SCALA_MONGO_AKKA.Client_API

import API_SCALA_MONGO_AKKA.Client_API.ClientsAPI.ClientRoutes
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.{complete, get}
import akka.http.scaladsl.server.{Directives, Route}
import com.typesafe.scalalogging.LazyLogging

import scala.io.StdIn
import scala.util.{Failure, Success}

object ClientStoreServer extends LazyLogging with App{

  implicit val system = ActorSystem("my-system")

  implicit val ec = system.dispatcher

  val clientAPI = new ClientRoutes()

  val createRoute=clientAPI.createClientRoute()
//  http://127.0.0.1:8080/getAllClients
  val getClientsRoute=clientAPI.getAllClients()
//  http://127.0.0.1:8080/create-client
// {"client_name": "abc", "url": "abc.com", "client_value": 43, "client_option": true}
  val updateClientUrlRoute=clientAPI.updateClients()
//  http://127.0.0.1:8080/updateClientField?name=abc&url=bad.com
  val deleteClientRoute=clientAPI.deleteClientByField()
//  http://127.0.0.1:8080/deleteClient?url=abc.com
  val findClientByValueRoute=clientAPI.findClientByValue()
//  http://127.0.0.1:8080/findClientByValue?value=43
  val pagingRoute = clientAPI.pagination()
//  http://127.0.0.1:8080/paging?pageNumber=2&rowsPerPage=2


  val routes:Route=Directives.concat(pagingRoute,findClientByValueRoute,deleteClientRoute,updateClientUrlRoute,getClientsRoute,createRoute)
  val bindingFuture = Http().newServerAt("127.0.0.1", 8080).bind(routes)
  println(s" SERVER IS ONLINE AT http://127.0.0.1:8080/ \n PRESS ENTER TO STOP!")
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_=> system.terminate())

}
