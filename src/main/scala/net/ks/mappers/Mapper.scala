package net.ks.mappers

private[ks] trait Mapper[S, T] {
  def map(request: S): T
}
