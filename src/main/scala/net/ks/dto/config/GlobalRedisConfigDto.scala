package net.ks.dto.config

import net.ks.client.RedisUtils._

class GlobalRedisConfigDto {
  val redisConfig = RedisConfigDto(masterHost, redisPort, db, connectionTimeout)
  val sentinelConfig = SentinelConfigDto(sentinelHosts, masterHostName, db)
}
