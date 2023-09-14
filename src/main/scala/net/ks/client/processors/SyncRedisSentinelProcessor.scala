package net.ks.client.processors

import akka.actor.ActorSystem
import net.ks.client.sync.SyncSentinelRedisClient
import net.ks.dto.config.GlobalRedisConfigDto
import net.ks.utils.Awaiter
import redis.RedisDispatcher
import redis.api.connection.Ping

import scala.concurrent.duration.{Duration, DurationInt}
import scala.concurrent.{Await, Future}

object SyncRedisSentinelProcessor extends App with Awaiter {
  implicit val akkaSystem: ActorSystem = akka.actor.ActorSystem()
  implicit val  redisDispatcher: RedisDispatcher = RedisDispatcher("blocking-dispatcher")
  override def withDuration: Duration = 5 seconds

  //implicit val customDispatcher: MessageDispatcher = akkaSystem.dispatchers.lookup("blocking-dispatcher")
  val sentinelConfigs = new GlobalRedisConfigDto().sentinelConfig

  val syncSentinelRedisClient = new SyncSentinelRedisClient(sentinelConfigs).syncRedisClient

  val futurePong = await(syncSentinelRedisClient.send(Ping))
  /*println("Ping sent!")
  futurePong.map(pong => {
    println(s"Redis replied with a $pong")
  })
  val pang = Await.result(futurePong, 5 seconds)*/
  println(s"result: $futurePong")

  //takes from source and put in
  //syncSentinelRedisClient.brpoplpush()

  akkaSystem.terminate()

}
