package net.ks.dto.config

case class RedisConfigDto(host: String,
                          port: Int = 6379,
                          db: Int = 7,
                          connectionTimeout: Int = 3)
