package net.ks.client.sync

import akka.actor.ActorSystem
import net.ks.dto.config.RedisConfigDto
import redis.RedisBlockingClient

class SyncRedisClient(cacheConfigs: RedisConfigDto)(implicit _system: ActorSystem) {
  val syncRedisClient = RedisBlockingClient(host = cacheConfigs.host, port = cacheConfigs.port, db = Some(cacheConfigs.db))
}
