package net.ks.utils

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

trait Awaiter {
  def await[T](future: Future[T]) = Await.result(future, withDuration)

  def withDuration: Duration
}
