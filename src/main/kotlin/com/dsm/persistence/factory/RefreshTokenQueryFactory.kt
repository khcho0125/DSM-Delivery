package com.dsm.persistence.factory

import com.dsm.persistence.entity.RefreshToken
import com.dsm.persistence.repository.RefreshTokenRepository
import com.dsm.plugins.database.RedisDatabaseConnector.redisCommands
import io.lettuce.core.RedisFuture
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import java.time.Duration

/**
 *
 * 리프레쉬 토큰에 대한 질의를 요청하는 RefreshTokenQueryFactory
 *
 * @author Chokyunghyeon
 * @date 2023/04/09
 **/
class RefreshTokenQueryFactory : RefreshTokenRepository {

    private fun RedisFuture<String>.toEntity(): RefreshToken =
        get().let(Json::decodeFromString)

    override suspend fun insert(refreshToken: RefreshToken): RefreshToken {
        val refreshTokenAsJson: String = Json
            .encodeToJsonElement(refreshToken).toString()
        val key = "$REFRESH_TOKEN_HEAD:${refreshToken.token}"

        redisCommands.run {
            set(key, refreshTokenAsJson)
            expire(key, Duration.ofMillis(refreshToken.expired))
        }

        return redisCommands.get(key)!!.toEntity()
    }

    companion object {
        private const val REFRESH_TOKEN_HEAD = "refresh-token"
    }

}