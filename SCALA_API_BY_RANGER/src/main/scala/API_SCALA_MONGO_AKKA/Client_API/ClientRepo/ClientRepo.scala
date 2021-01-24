package API_SCALA_MONGO_AKKA.Client_API.ClientRepo

import API_SCALA_MONGO_AKKA.Client_API.ClientCaseClass.Client
import ch.rasc.bsoncodec.math.{BigDecimalStringCodec, BigIntegerStringCodec}
import org.bson.codecs.configuration.CodecProvider
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.bson.codecs.{DEFAULT_CODEC_REGISTRY, Macros}
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}

object ClientRepo {

  private val codecProvider: CodecProvider = Macros.createCodecProvider(classOf[Client])
  private val customCodec = fromProviders(codecProvider)

  private val javaCodec = org.bson.codecs.configuration.CodecRegistries.fromCodecs(new BigDecimalStringCodec(), new BigIntegerStringCodec())

  private val codecReg = fromRegistries(customCodec, javaCodec, DEFAULT_CODEC_REGISTRY)

  private val DB: MongoDatabase = MongoClient().getDatabase("ClientDataBase").withCodecRegistry(codecReg)
  val clientDetails: MongoCollection[Client] = DB.getCollection("ClientCollection")
}
