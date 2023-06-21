package com.dsm.domain.student.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.dsm.persistence.entity.RefreshToken
import com.dsm.persistence.repository.RefreshTokenRepository
import com.dsm.plugins.SecurityProperties
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.Date

/**
 *
 * 보안 토큰을 생성하는 GenerateToken
 *
 * @author Chokyunghyeon
 * @date 2023/03/20
 **/
@Serializable
data class TokenResult(
    val accessToken: String,
    val refreshToken: String,
    @Contextual
    val accessTokenExpired: LocalDateTime
)

class JwtGenerator(
    private val refreshTokenRepository: RefreshTokenRepository
) : TokenProvider {

    private suspend fun generateRefreshToken(studentId: Int): String {
        val token: String = JWT.create()
            .withSubject(JWT_SUBJECT)
            .withJWTId(JWT_REFRESH)
            .withAudience(SecurityProperties.audience)
            .withIssuer(SecurityProperties.issuer)
            .withExpiresAt(Date(System.currentTimeMillis() + SecurityProperties.refreshExpiredMillis))
            .sign(Algorithm.HMAC256(SecurityProperties.secret))

        refreshTokenRepository.insert(
            RefreshToken(
                token = token,
                studentId = studentId,
                expiredMillis = SecurityProperties.refreshExpiredMillis
            )
        )

        return token
    }

    private fun generateAccessToken(studentId: Int): String {
        return JWT.create()
            .withSubject(JWT_SUBJECT)
            .withJWTId(JWT_ACCESS)
            .withAudience(SecurityProperties.audience)
            .withIssuer(SecurityProperties.issuer)
            .withClaim(JWT_STUDENT_ID, studentId)
            .withExpiresAt(Date(System.currentTimeMillis() + SecurityProperties.accessExpiredMillis))
            .sign(Algorithm.HMAC256(SecurityProperties.secret))
    }

    override suspend fun generateToken(studentId: Int): TokenResult {
        val accessToken: String = generateAccessToken(studentId)
        val refreshToken: String = generateRefreshToken(studentId)

        return TokenResult(
            accessToken = accessToken,
            refreshToken = refreshToken,
            accessTokenExpired = LocalDateTime.now()
                .plusNanos(SecurityProperties.accessExpiredMillis).withNano(0)
        )
    }

    companion object {
        const val JWT_SUBJECT: String = "Authentication"
        const val JWT_REFRESH: String = "Refresh"
        const val JWT_ACCESS: String = "Access"
        const val JWT_STUDENT_ID: String = "student-id"
    }
}
