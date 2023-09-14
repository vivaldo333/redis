package net.ks.client.processors

import akka.actor.ActorSystem
import net.ks.client.async.AsyncSentinelRedisClient
import net.ks.config.JsonDtoProtocol._
import net.ks.dto.EventDto
import net.ks.dto.config.GlobalRedisConfigDto
import net.ks.utils.FileUtils.getFileData
import spray.json._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

object AsyncRedisSentinelProcessor extends App {
  implicit val akkaSystem: ActorSystem = akka.actor.ActorSystem()
  val sentinelConfigs = new GlobalRedisConfigDto().sentinelConfig

  val asyncSentinelRedisClient = new AsyncSentinelRedisClient(sentinelConfigs).asyncSentinelClient.redisClient

  val futurePong = asyncSentinelRedisClient.ping()
  println("Ping sent!")
  futurePong.map(pong => {
    println(s"Redis replied with a $pong")
  })
  Await.result(futurePong, 5 seconds)

  /*futurePong.onComplete {
    case Success(resultText) =>
      println(s"Received ByteString: $resultText")
    case Failure(exception) =>
      println(s"Error: ${exception.getMessage}")
  }*/

  // Read the JSON file into a String
  //val jsonString = scala.io.Source.fromFile(delimiter + "events" + delimiter + "event.json").mkString
  val filePath = "/events/event.json"
  val jsonString = getFileData(filePath)
  println(s"event json: $jsonString")

  // Parse the JSON string into an EventDto object
  val eventDto: EventDto = jsonString.parseJson.convertTo[EventDto]
  println(s"event: $eventDto")

  val docId = s"${eventDto.subscriberId}${eventDto.eventId}${eventDto.cid}"
  val isStored = asyncSentinelRedisClient.set(docId, eventDto.toJson.compactPrint)
  val storedEventResult = Await.result(isStored, 5 seconds)
  println(s"event was stored: $storedEventResult")

  val futureEvent = asyncSentinelRedisClient.get(docId)
    .map(_.map(_.utf8String.parseJson.convertTo[EventDto]))
  val event = Await.result(futureEvent, 5 seconds)
  println(s"retrieved event: $event")
  //val retrievedEvent: EventDto = event.parseJson.convertTo[EventDto]

  val futureDeleteResult = asyncSentinelRedisClient.del(docId)
  val deleteResult = Await.result(futureDeleteResult, 5 seconds)
  println(s"removing key $docId result: $deleteResult")

  akkaSystem.terminate()
}
