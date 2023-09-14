package net.ks.client.processors

import akka.actor.ActorSystem
import redis.{RedisClient, SentinelClient, SentinelMonitoredRedisClient}

import java.util.concurrent.TimeUnit
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object ProcessorTest extends App {
  implicit val akkaSystem: ActorSystem = akka.actor.ActorSystem()
  //SDPlite-dgt3-DB.kyivstar.ua 10.49.197.173:26379
  //SDPlite-lv3-DB.kyivstar.ua 10.49.197.172:26379
  //SDPlite-whg3-DB.kyivstar.ua 10.49.0.85:26379
  val masterHost = "10.49.197.173"
  val redisPort = 6379
  val sentinelPort = 26379
  val db = Some(7)
  val connectionTimeout = Some(Duration(5, TimeUnit.SECONDS))
  val masterHostName = "test.cluster"
  val sentinels = Seq((masterHost, sentinelPort), ("10.49.197.155", sentinelPort), ("10.49.197.172", sentinelPort))
  //val syncSentinelClient = SentinelMonitoredRedisBlockingClient(sentinels = sentinels, master = "SDPlite-dgt3-DB.kyivstar.ua", db = db)
  val asyncSentinelClient = SentinelMonitoredRedisClient(sentinels = sentinels, master = masterHostName, db = db)
  val asyncRedisClient = asyncSentinelClient.redisClient
  //val syncRedisClient = syncSentinelClient.redisClient
  val sentinelClient = SentinelClient(masterHost, sentinelPort)
  //val asyncRedisClient = RedisClient(host = masterHost, port = redisPort, db = db)
  //val syncRedisClient = RedisBlockingClient(host = masterHost, port = redisPort, db = db)

  /*val resultPush = syncRedisClient.brpoplpush("workList", "doSomeWork").map({
    case Some(byteString) =>
      println(s"Received ByteString: $byteString")
    case None =>
      println("Received None")
  })
  val result: Unit = Await.result(resultPush, 5.seconds)

  val result2 = syncRedisClient.brpop(Seq("workList", "otherKeyWithWork"))
    .map(r => println("pop result: " + r.getOrElse("Nothing")))
  Await.result(result2, 5.seconds)
  syncRedisClient.blpop(Seq("workList", "otherKeyWithWork"))*/

  val futurePong = asyncRedisClient.ping()
  println("Ping sent!")
  futurePong.map(pong => {
    println(s"Redis replied with a $pong")
  })
  Await.result(futurePong, 10 seconds)

  //val futureResult = doSomething(asyncRedisClient)

  //Await.result(futureResult, 5 seconds)

  akkaSystem.terminate()

  def doSomething(redis: RedisClient): Future[Boolean] = {
    // launch command set and del in parallel
    val s = redis.set("redis", "is awesome")
    val d = redis.del("i")
    for {
      set <- s
      del <- d
      incr <- redis.incr("i")
      iBefore <- redis.get("i")
      incrBy20 <- redis.incrby("i", 20)
      iAfter <- redis.get("i")
    } yield {
      println("SET redis \"is awesome\"")
      println("DEL i")
      println("INCR i")
      println("INCRBY i 20")
      val ibefore = iBefore.map(_.utf8String)
      val iafter = iAfter.map(_.utf8String)
      println(s"i was $ibefore, now is $iafter")
      iafter == "20"
    }
  }
}