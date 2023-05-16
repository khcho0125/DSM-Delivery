package com.dsm.domain.student.usecase

import com.dsm.domain.student.token.TokenProvider
import com.dsm.domain.student.token.TokenResult
import com.dsm.exception.RefreshTokenException
import com.dsm.persistence.entity.RefreshToken
import com.dsm.persistence.repository.RefreshTokenRepository
import com.dsm.plugins.database.dbQuery

/**
 *
 * 토큰 재발급을 담당하는 ReissueToken
 *
 * @author Chokyunghyeon
 * @date 2023/04/09
 **/
class ReissueToken(
    private val tokenProvider: TokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    suspend operator fun invoke(token: String): TokenResult = dbQuery {
        val refreshToken: RefreshToken = refreshTokenRepository.findByToken(token)
            ?: throw RefreshTokenException.NotFound()

        return@dbQuery tokenProvider.generateToken(refreshToken.studentId)
    }
}