package net.ks.client.sync

import akka.actor.ActorSystem
import net.ks.dto.config.SentinelConfigDto
import redis.SentinelMonitoredRedisBlockingClient

class SyncSentinelRedisClient(cacheConfig: SentinelConfigDto)(implicit _system: ActorSystem) {
  val syncSentinelClient = SentinelMonitoredRedisBlockingClient(sentinels = cacheConfig.sentinelHostsAndPorts,
    master = cacheConfig.masterHostName, db = Some(cacheConfig.db))
  val syncRedisClient = syncSentinelClient.redisClient
}
