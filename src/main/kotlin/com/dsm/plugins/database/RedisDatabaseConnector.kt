package com.dsm.plugins.database

import io.ktor.events.Events
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.application.ApplicationStopped
import io.ktor.server.config.ApplicationConfig
import io.lettuce.core.RedisClient
import io.lettuce.core.RedisURI
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.async.RedisAsyncCommands
import kotlinx.coroutines.DisposableHandle
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 * 레디스의 연결을 담당하는 RedisDatabaseConnector
 *
 * @author Chokyunghyeon
 * @date 2023/04/10
 **/
object RedisDatabaseConnector {

    private const val REDIS_PREFIX: String = "redis"
    private const val REDIS_HOST: String = "$REDIS_PREFIX.host"
    private const val REDIS_PORT: String = "$REDIS_PREFIX.port"
    private const val REDIS_PASSWORD: String = "$REDIS_PREFIX.password"

    private const val CONNECT_MESSAGE: String = "Redis connected"
    private const val CLOSE_MESSAGE: String = "Redis disconnected"

    private lateinit var redisConnection: StatefulRedisConnection<String, String>
    private lateinit var redisClient: RedisClient
    lateinit var redisCommands: RedisAsyncCommands<String, String>

    val json: Json = Json { encodeDefaults = true }

    val logger: Logger = LoggerFactory.getLogger(RedisDatabaseConnector::class.java)

    fun Events.connectRedis() : DisposableHandle = subscribe(ApplicationStarted) {
        val config: ApplicationConfig = it.environment.config

        val redisUri: RedisURI = RedisURI.builder()
            .withHost(config.property(REDIS_HOST).getString())
            .withPort(config.property(REDIS_PORT).getString().toInt())
            .withPassword(config.property(REDIS_PASSWORD).getString().toCharArray())
            .build()

        redisClient = RedisClient.create(redisUri)
        redisConnection = redisClient.connect()
        redisCommands = redisConnection.async()

        logger.info(CONNECT_MESSAGE)
    }

    fun Events.disconnectRedis() : DisposableHandle = subscribe(ApplicationStopped) {
        redisConnection.close()
        redisClient.shutdown()
        logger.info(CLOSE_MESSAGE)
    }
}