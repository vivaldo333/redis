package net.ks.client.processors

import akka.actor.ActorSystem
import net.ks.client.async.AsyncSentinelRedisClient
import net.ks.client.processors.DataHelper.getEvent
import net.ks.dto.config.GlobalRedisConfigDto
import net.ks.utils.Awaiter

import scala.concurrent.duration.{Duration, DurationInt}

object AsyncEventProcessor extends App with Awaiter {

  implicit val akkaSystem: ActorSystem = akka.actor.ActorSystem()
  override def withDuration: Duration = 5 seconds

  val sentinelConfigs = new GlobalRedisConfigDto().sentinelConfig
  val asyncSentinelRedisClient = new AsyncSentinelRedisClient(sentinelConfigs)

  val eventId = "105"
  val event = getEvent(eventId)
  val isSaved = await(asyncSentinelRedisClient.save((eventId, event)))
  println(s"event was saved: $isSaved")

  val dbEvent = await(asyncSentinelRedisClient.getById(eventId)).orNull
  println(s"retrieved event: $dbEvent")

  val isDeleted = await(asyncSentinelRedisClient.deleteById(eventId))
  println(s"event was deleted: $isDeleted")

  val isPresent = await(asyncSentinelRedisClient.exists(eventId))
  println(s"event is present: $isPresent")

  akkaSystem.terminate()
}
