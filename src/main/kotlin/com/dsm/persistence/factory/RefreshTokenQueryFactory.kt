package com.dsm.persistence.factory

import com.dsm.persistence.entity.RefreshToken
import com.dsm.persistence.repository.RefreshTokenRepository
import com.dsm.plugins.database.RedisDatabaseConnector.redisCommands
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.lettuce.core.ExperimentalLettuceCoroutinesApi
import io.lettuce.core.api.async.multi

/**
 *
 * 리프레쉬 토큰에 대한 질의를 요청하는 RefreshTokenQueryFactory
 *
 * @author Chokyunghyeon
 * @date 2023/04/09
 **/
@OptIn(ExperimentalLettuceCoroutinesApi::class)
class RefreshTokenQueryFactory : RefreshTokenRepository {

    private fun redisKey(token: String): String =
        "refresh-token:$token"

    override suspend fun insert(refreshToken: RefreshToken): RefreshToken {
        val refreshTokenAsJson: String = jacksonObjectMapper()
            .writeValueAsString(refreshToken)
        val key: String = redisKey(refreshToken.token)

        redisCommands.multi {
            psetex(key, refreshToken.expiredMillis, refreshTokenAsJson)
        }

        return refreshToken
    }

    override suspend fun findByToken(token: String): RefreshToken? =
        redisCommands.get(redisKey(token)).get()?.let(jacksonObjectMapper()::readValue)

}