package net.ks.client.sync

import akka.actor.ActorSystem
import net.ks.client.repository.SyncCrudRepository
import net.ks.config.JsonDtoProtocol.{eventDtoFormat, toEventJson}
import net.ks.dto.EventDto
import net.ks.dto.config.SentinelConfigDto
import net.ks.utils.Awaiter
import redis.api.keys.{Del, Exists}
import redis.api.strings.{Get, Set}
import redis.{RedisBlockingClient, RedisDispatcher, SentinelMonitoredRedisBlockingClient}
import spray.json._

import scala.concurrent.duration.{Duration, DurationInt}

class SyncSentinelRedisClient(cacheConfig: SentinelConfigDto)
                             (implicit _system: ActorSystem, redisDispatcher: RedisDispatcher)
  extends SyncCrudRepository[String, EventDto] with Awaiter {
  override def withDuration: Duration = 5 seconds

  val syncSentinelClient: SentinelMonitoredRedisBlockingClient =
    SentinelMonitoredRedisBlockingClient(sentinels = cacheConfig.sentinelHostsAndPorts,
      master = cacheConfig.masterHostName, db = Some(cacheConfig.db))
  val syncRedisClient: RedisBlockingClient = syncSentinelClient.redisClient

  override def getById(id: String): Option[EventDto] = {
    await(syncRedisClient.send(Get(id))).map(toEventJson)
  }

  override def save(data: (String, EventDto)): Boolean = {
    await(syncRedisClient.send(Set(key = data._1, value = data._2.toJson.compactPrint)))
  }

  override def deleteById(id: String): Boolean = {
    await(syncRedisClient.send(Del(Seq(id)))) > 0
  }

  override def exists(id: String): Boolean = {
    await(syncRedisClient.send(Exists(id)))
  }
}
