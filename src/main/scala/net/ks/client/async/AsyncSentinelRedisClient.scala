package net.ks.client.async

import akka.actor.ActorSystem
import net.ks.dto.config.SentinelConfigDto
import redis.SentinelMonitoredRedisClient

class AsyncSentinelRedisClient(cacheConfig: SentinelConfigDto)(implicit _system: ActorSystem) {
  /*val masterHost = cacheConfig.getString("redis.hosts.master")
  val slaveHost1 = cacheConfig.getString("redis.hosts.slave-1")
  val slaveHost2 = cacheConfig.getString("redis.hosts.slave-2")
  val sentinelPort = cacheConfig.getInt("redis.ports.sentinel")
  val db = cacheConfig.getInt("redis.db")
  val sentinelHosts = Seq((masterHost, sentinelPort), (slaveHost1, sentinelPort), (slaveHost2, sentinelPort))*/
  val asyncSentinelClient = SentinelMonitoredRedisClient(sentinels = cacheConfig.sentinelHostsAndPorts,
    master = cacheConfig.masterHostName, db = Some(cacheConfig.db))
  val asyncRedisClient = asyncSentinelClient.redisClient
}
