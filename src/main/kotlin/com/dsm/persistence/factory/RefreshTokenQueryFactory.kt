package com.dsm.persistence.factory

import com.dsm.persistence.entity.RefreshToken
import com.dsm.persistence.repository.RefreshTokenRepository
import com.dsm.plugins.database.RedisDatabaseConnector.redisCommands
import io.lettuce.core.ExperimentalLettuceCoroutinesApi
import io.lettuce.core.TransactionResult
import io.lettuce.core.api.async.multi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

/**
 *
 * 리프레쉬 토큰에 대한 질의를 요청하는 RefreshTokenQueryFactory
 *
 * @author Chokyunghyeon
 * @date 2023/04/09
 **/
@OptIn(ExperimentalLettuceCoroutinesApi::class)
class RefreshTokenQueryFactory : RefreshTokenRepository {

    private fun key(token: String): String =
        "refresh-token:$token"

    override suspend fun insert(refreshToken: RefreshToken): RefreshToken {
        val refreshTokenAsJson: String = Json
            .encodeToJsonElement(refreshToken).toString()
        val key = key(refreshToken.token)

        val result: TransactionResult = redisCommands.multi {
            psetex(key, refreshToken.expiredMillis, refreshTokenAsJson)
            get(key)
        }

        return result.first().toString().let(Json::decodeFromString)
    }

    override suspend fun findByToken(token: String): RefreshToken? =
        redisCommands.get(key(token)).get()?.let(Json::decodeFromString)

}