package net.ks

import net.ks.client.async.AsyncSentinelRedisClient
import net.ks.dto.config.GlobalRedisConfigDto

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

object RedisSentinelProcessor extends App {
  implicit val akkaSystem = akka.actor.ActorSystem()
  val sentinelConfigs = new GlobalRedisConfigDto().sentinelConfig

  val asyncSentinelRedisClient = new AsyncSentinelRedisClient(sentinelConfigs).asyncSentinelClient.redisClient

  val futurePong = asyncSentinelRedisClient.ping()
  println("Ping sent!")
  futurePong.map(pong => {
    println(s"Redis replied with a $pong")
  })
  Await.result(futurePong, 5 seconds)

  /*futurePong.onComplete {
    case Success(resultText) =>
      println(s"Received ByteString: $resultText")
    case Failure(exception) =>
      println(s"Error: ${exception.getMessage}")
  }*/

  akkaSystem.terminate()
}
