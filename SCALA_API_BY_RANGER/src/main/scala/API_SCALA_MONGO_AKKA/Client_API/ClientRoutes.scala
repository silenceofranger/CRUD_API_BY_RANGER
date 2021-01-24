package API_SCALA_MONGO_AKKA.Client_API

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.typesafe.scalalogging.LazyLogging


class ClientRoutes extends JsonSupport with LazyLogging{

  def createClientRoute():Route={
    path("create-client"){
      post{
        entity(as[Client]){ client=>
          logger.info(s"Creating Client= $client")
          onSuccess(ClientRepoFunctions.insertClient(client)){ commClient=>
            complete(StatusCodes.Created,s"Created client= $client")
          }
        }
      }
    }
  }

  def getAllClients():Route={
    path("getAllClients"){
      get{
        logger.info("Fetching all clients.")
        onSuccess(ClientRepoFunctions.getCollection){ clients=>
          complete(clients)
        }
      }
    }
  }

  def updateClients():Route={
    path("updateClientField"){
      parameters("name".withDefault(""),"url".withDefault("")){(name,url)=>
        patch{
            logger.info("UPDATING BOOKS")
            onSuccess(ClientRepoFunctions.updateField(name, url)) { updatedField=>
              complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, s"$updatedField documents are updated."))
            }
          }
        }
      }
  }

  def deleteClientByField():Route={
    path("deleteClient"){
      delete{
        parameters("url".withDefault("")){url=>
          onSuccess(ClientRepoFunctions.deleteClient(url)){ deleteClients=>
            complete(deleteClients)
          }
        }
      }
    }
  }

  def findClientByValue():Route={
    path("findClientByValue"){
      get{
        parameters("value".withDefault(0)){value=>
          onSuccess(ClientRepoFunctions.findByValue(value)){ foundClients=>
            complete(foundClients)
          }
        }
      }
    }
  }

  def pagination():Route = {
    path("paging"){
      get {
        parameter("pageNumber".withDefault(0),"rowsPerPage".withDefault(0)){(x,y) =>
          onSuccess(ClientRepoFunctions.paging(x,y)){ foundClients =>
            complete(foundClients)
          }
        }
      }
    }
  }
}
