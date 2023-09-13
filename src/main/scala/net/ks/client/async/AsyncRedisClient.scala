package net.ks.client.async

import akka.actor.ActorSystem
import net.ks.dto.config.RedisConfigDto
import redis.RedisClient

class AsyncRedisClient(cacheConfig: RedisConfigDto)(implicit _system: ActorSystem) {
  val asyncRedisClient = RedisClient(host = cacheConfig.host,
    port = cacheConfig.port,
    db = Some(cacheConfig.db))
}
