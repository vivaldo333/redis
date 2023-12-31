# scala redis

(redis_version:7.0.11)
redis_mode:sentinel
os:Linux 3.10.0-1160.81.1.el7.x86_64 x86_64

DB-s: 0-15
Master Node:   10.49.197.173
Slaves node 1: 10.49.197.155
Slaves node 2: 10.49.197.172
Redis Port: 6379
Sentinel Port: 26379

### SpringBoot - Redis clients:

- Lettuce
- Jedis
- Spring Data Redis
- Redisson
- Ledis
- JRediSearch

### Scala - Redis clients:

- Lettuce
- Jedis
- Scala Redis +
- Rediscala
- Spray Redis
- Akka Persistence Redis
- Redisson

[redis clients](https://redis.io/resources/clients/#java)

### Scala Redis client

- **Jedis**
  Redis Java client
- **Redisson**
  Redisson - Easy Redis Java client with features of In-Memory Data Grid. Over 50 Redis based Java objects and services:
  Set, Multimap, SortedSet, Map, List, Queue, Deque, Semaphore, Lock, AtomicLong, Map Reduce, Publish / Subscribe, Bloom
  filter, Spring Cache, Tomcat, Scheduler, JCache API, Hibernate, MyBatis, RPC, local cache ...
- **lettuce**
  Advanced Java Redis client for thread-safe sync, async, and reactive usage. Supports Cluster, Sentinel, Pipelining,
  and codecs.
- **JRedis**
  Java Client and Connectors for Redis
- **vertx-redis-client**
  Redis client for Vert.x
- **redis-protocol**
  Java client and server implementation of Redis
- **java-redis-client**
  Low level Redis client (but you won't need more than this)
- **RJC**
  Redis Java Client
- **viredis**
  Java client for Redis Cache
- **aredis**
- **JDBC-Redis**

### Scala Redis client

- **finagle**
  A fault tolerant, protocol-agnostic RPC system
- **Redis4Cats**
  Redis client built on top of Cats Effect, Fs2 and Lettuce
- **scredis**
  Non-blocking, ultra-fast Scala Redis client built on top of Akka IO.
- **laserdisc**
  A Future-free Fs2 native pure FP Redis client
- **monix-connect**
  A set of connectors for Monix.
- **LettuceF**
  Scala FP wrapper for Lettuce with Cats Effect
- **redicl**
- **redis client**
- **rediscala**
  Non-blocking, Reactive Redis driver for Scala (with Sentinel support)
- **Brando**
  A Redis client written with Akka's IO package
- **sedis**
  a thin scala wrapper for jedis [jedis](https://github.com/xetorthio/jedis)
- **redis-client-scala-netty**
  Scala redis client built with netty framework
- **RedisClient**
  A no nonsense Redis Client using pure scala
- **scala-redis**
  Scala redis client built with netty framework
- **spark-redis**