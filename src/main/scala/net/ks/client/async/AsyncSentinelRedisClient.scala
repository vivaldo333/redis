package net.ks.client.async

import akka.actor.ActorSystem
import net.ks.client.repository.AsyncCrudRepository
import net.ks.config.JsonDtoProtocol._
import net.ks.dto.EventDto
import net.ks.dto.config.SentinelConfigDto
import redis.{RedisClient, SentinelMonitoredRedisClient}
import spray.json._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AsyncSentinelRedisClient(cacheConfig: SentinelConfigDto)(implicit _system: ActorSystem)
  extends AsyncCrudRepository[String, EventDto] {
  val asyncSentinelClient: SentinelMonitoredRedisClient = SentinelMonitoredRedisClient(sentinels = cacheConfig.sentinelHostsAndPorts,
    master = cacheConfig.masterHostName, db = Some(cacheConfig.db))
  val asyncRedisClient: RedisClient = asyncSentinelClient.redisClient

  override def getById(id: String): Future[Option[EventDto]] = {
    asyncRedisClient.get(id).map(_.map(toEventJson))
  }

  override def save(data: (String, EventDto)): Future[Boolean] = {
    asyncRedisClient.set(data._1, data._2.toJson.compactPrint)
  }

  override def deleteById(id: String): Future[Long] = {
    asyncRedisClient.del(id)
  }

  override def exists(id: String): Future[Boolean] = {
    asyncRedisClient.exists(id)
  }
}
