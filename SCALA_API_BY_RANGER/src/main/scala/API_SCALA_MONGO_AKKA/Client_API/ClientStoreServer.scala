package API_SCALA_MONGO_AKKA.Client_API

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.{complete, get}
import akka.http.scaladsl.server.{Directives, Route}
import com.typesafe.scalalogging.LazyLogging

import scala.util.{Failure, Success}

object ClientStoreServer extends LazyLogging with App{

  implicit val system = ActorSystem("Akka-HTTP-REST-Server")

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

  val findClientByValueRoute=clientAPI.findClientByValue()

  val routes:Route=Directives.concat(findClientByValueRoute,deleteClientRoute,updateClientUrlRoute,getClientsRoute,createRoute)

  val host = "127.0.0.1"
  val port = 8080
  val httpServerFuture = Http().bindAndHandle(routes, host, port)

  httpServerFuture.onComplete {
    case Success(binding) =>
      logger.info(s"AKKA-HTTP IS ONLINE AT ${binding.localAddress}.")
    case Failure(exception) =>
      logger.info(s"AKKA-HTTP FAILED TO START")
  }
}
