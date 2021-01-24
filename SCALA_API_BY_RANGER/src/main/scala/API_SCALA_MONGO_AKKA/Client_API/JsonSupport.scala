package API_SCALA_MONGO_AKKA.Client_API

import API_SCALA_MONGO_AKKA.Client_API.ClientCaseClass.{Client, Clients}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  import spray.json._

  implicit val printer = PrettyPrinter
  implicit val clientFormat = jsonFormat4(Client)
  implicit val clientsFormat = jsonFormat1(Clients)

}
