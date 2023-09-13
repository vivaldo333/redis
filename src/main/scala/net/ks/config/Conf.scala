package net.ks.config

import com.typesafe.config.{Config, ConfigFactory}

object Conf {
  lazy val config: Config = ConfigFactory.load
  lazy val cacheConf: Config = config.getConfig("app.cache")

  //val serverHost = config.getString("myapp.server.host")
  //val serverPort = config.getInt("myapp.server.port")
}
