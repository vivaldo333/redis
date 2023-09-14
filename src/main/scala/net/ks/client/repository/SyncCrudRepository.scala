package net.ks.client.repository

import scala.concurrent.Future

trait SyncCrudRepository[K, V] {
  def getById(id: K): Option[V]
  def save(data: Tuple2[K, V]): Boolean
  def deleteById(id: K): Boolean
  def exists(id: K): Boolean
}
