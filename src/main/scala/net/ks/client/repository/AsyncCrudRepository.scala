package net.ks.client.repository

import scala.concurrent.Future

trait AsyncCrudRepository[K, V] {
  def getById(id: K): Future[Option[V]]
  def save(data: Tuple2[K, V]): Future[Boolean]
  def deleteById(id: K): Future[Long]
  def exists(id: K): Future[Boolean]
}
