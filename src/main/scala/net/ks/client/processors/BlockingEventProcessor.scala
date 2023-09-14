package net.ks.client.processors

import akka.actor.ActorSystem
import net.ks.client.processors.DataHelper.getEvent
import net.ks.client.sync.SyncSentinelRedisClient
import net.ks.dto.EventDto
import net.ks.dto.config.GlobalRedisConfigDto
import redis.RedisDispatcher

import scala.concurrent.duration.Duration

object BlockingEventProcessor extends App {
  implicit val akkaSystem: ActorSystem = akka.actor.ActorSystem()
  implicit val redisDispatcher: RedisDispatcher = RedisDispatcher("blocking-dispatcher")

  //override def withDuration: Duration = 5 seconds

  val sentinelConfigs = new GlobalRedisConfigDto().sentinelConfig
  val syncSentinelRedisClient = new SyncSentinelRedisClient(sentinelConfigs)

  val eventId = "103"
  val event = getEvent(eventId)
  val isSaved = syncSentinelRedisClient.save((eventId, event))
  println(s"event was saved: $isSaved")

  val dbEvent = syncSentinelRedisClient.getById(eventId).orNull
  println(s"retrieved event: $dbEvent")

  val isDeleted = syncSentinelRedisClient.deleteById(eventId)
  println(s"event was deleted: $isDeleted")

  val isPresent = syncSentinelRedisClient.exists(eventId)
  println(s"event is present: $isPresent")

  akkaSystem.terminate()
}
