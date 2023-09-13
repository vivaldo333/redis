package net.ks.dto.config

case class SentinelConfigDto(sentinelHostsAndPorts: Seq[(String, Int)],
                             masterHostName: String,
                             db: Int)
