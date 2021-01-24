package API_SCALA_MONGO_AKKA.Client_API

case class Client(client_name:String, url:String, client_value:Int, client_option:Option[Boolean])
case class Clients(clients:Seq[Client])

// {"client_name": "abc", "url": "abc.com", "client_value": 43, "client_option": true}