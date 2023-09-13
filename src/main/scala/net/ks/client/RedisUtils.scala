package net.ks.client

import net.ks.config.Conf.cacheConf

object RedisUtils {
  val masterHost = getTextAttribute("redis.hosts.master")
  val masterHostName = "test.cluster"
  val slaveHost1 = getTextAttribute("redis.hosts.slave-1")
  val slaveHost2 = getTextAttribute("redis.hosts.slave-2")
  val sentinelPort = getDigitAttribute("redis.ports.sentinel")
  val redisPort = getDigitAttribute("redis.ports.redis")
  val db = getDigitAttribute("redis.db")
  val connectionTimeout = getDigitAttribute("redis.connection-timeout-sec")
  val sentinelHosts = Seq((masterHost, sentinelPort), (slaveHost1, sentinelPort), (slaveHost2, sentinelPort))

  def getTextAttribute(path: String): String = {
    cacheConf.getString(path)
  }

  def getDigitAttribute(path: String): Int = {
    cacheConf.getInt(path)
  }
}
