package com.dsm.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.dsm.domain.auth.token.JwtGenerator
import com.dsm.exception.ExceptionResponse
import com.dsm.persistence.repository.StudentRepository
import com.dsm.plugins.database.dbQuery
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.response.respond
import org.koin.ktor.ext.getKoin
import java.util.UUID

/**
 *
 * 보안 구성을 담당하는 Security
 *
 * @author Chokyunghyeon
 * @date 2023/03/16
 **/
fun Application.configureSecurity() {
    val securityProperties: SecurityProperties = getKoin().get()
    val studentRepository: StudentRepository = getKoin().get()

    authentication {
        jwt {
            realm = securityProperties.realm

            verifier(
                JWT
                    .require(Algorithm.HMAC256(securityProperties.secret))
                    .withAudience(securityProperties.audience)
                    .withIssuer(securityProperties.issuer)
                    .withJWTId(JwtGenerator.JWT_ACCESS)
                    .withSubject(JwtGenerator.JWT_SUBJECT)
                    .build()
            )

            validate { credential ->
                dbQuery {
                    val studentId: String = credential.payload.getClaim(JwtGenerator.JWT_STUDENT_ID).asString()
                        ?: return@dbQuery null

                    return@dbQuery if (studentRepository.existsById(studentId.let(UUID::fromString))) {
                        null
                    } else {
                        JWTPrincipal(credential.payload)
                    }
                }
            }

            challenge { _, _ ->
                call.respond(
                    message = ExceptionResponse(
                        message = "Token is not valid or has expired",
                        status = HttpStatusCode.Unauthorized.value
                    ),
                    status = HttpStatusCode.Unauthorized
                )
            }
        }
    }
}

class SecurityProperties(config: ApplicationConfig) {

    val realm: String = config.property(JWT_REALM).getString()
    val secret: String = config.property(JWT_SECRET).getString()
    val audience: String = config.property(JWT_AUDIENCE).getString()
    val issuer: String = config.property(JWT_ISSUER).getString()

    val refreshExpiredMillis: Long = config.property(REFRESH_TOKEN_EXPIRED_TIME)
        .getString().toLong() * millisecondPerSecond
    val accessExpiredMillis: Long = config.property(ACCESS_TOKEN_EXPIRED_TIME)
        .getString().toLong() * millisecondPerSecond

    companion object {
        const val JWT_AUDIENCE: String = "jwt.audience"
        const val JWT_SECRET: String = "jwt.secret"
        const val JWT_REALM: String = "jwt.realm"
        const val JWT_ISSUER: String = "jwt.issuer"
        const val REFRESH_TOKEN_EXPIRED_TIME: String = "jwt.token.refresh-expired"
        const val ACCESS_TOKEN_EXPIRED_TIME: String = "jwt.token.access-expired"

        const val millisecondPerSecond: Long = 1000
    }
}
