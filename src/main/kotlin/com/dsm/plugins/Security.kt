package com.dsm.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.dsm.domain.student.token.JwtGenerator
import com.dsm.exception.DomainException
import com.dsm.exception.RefreshTokenException
import com.dsm.persistence.repository.StudentRepository
import com.dsm.plugins.database.dbQuery
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.auth.principal
import io.ktor.server.config.ApplicationConfig
import org.koin.ktor.ext.getKoin
import kotlin.properties.Delegates

/**
 *
 * 보안 구성을 담당하는 Security
 *
 * @author Chokyunghyeon
 * @date 2023/03/16
 **/
fun Application.configureSecurity() {
    SecurityProperties.init(environment.config)
    val studentRepository: StudentRepository = getKoin().get()

    authentication {
        jwt {
            realm = SecurityProperties.realm

            verifier(
                JWT
                    .require(Algorithm.HMAC256(SecurityProperties.secret))
                    .withAudience(SecurityProperties.audience)
                    .withIssuer(SecurityProperties.issuer)
                    .withJWTId(JwtGenerator.JWT_ACCESS)
                    .withSubject(JwtGenerator.JWT_SUBJECT)
                    .build()
            )

            validate { credential ->
                dbQuery {
                    val studentId: Int = credential.payload.getClaim(JwtGenerator.JWT_STUDENT_ID).asInt()
                        ?: return@dbQuery null

                    return@dbQuery if (studentRepository.existsById(studentId)) {
                        JWTPrincipal(credential.payload)
                    } else {
                        null
                    }
                }
            }

            challenge { _, _ ->
                throw RefreshTokenException.ValidToken()
            }
        }
    }
}

fun ApplicationCall.currentUserId(): Int {
    val principal: JWTPrincipal = principal<JWTPrincipal>()
        ?: throw DomainException.InternalServerError("JWT Principal Undefined")

    return principal.payload.getClaim(JwtGenerator.JWT_STUDENT_ID).asInt()
}

object SecurityProperties {

    lateinit var realm: String
    lateinit var secret: String
    lateinit var audience: String
    lateinit var issuer: String

    var refreshExpiredMillis: Long by Delegates.notNull()
        private set
    var accessExpiredMillis: Long by Delegates.notNull()
        private set

    private const val millisecondPerSecond: Long = 1_000

    fun init(config: ApplicationConfig) {
        realm = config.property(Prefix.JWT_REALM).getString()
        secret = config.property(Prefix.JWT_SECRET).getString()
        audience = config.property(Prefix.JWT_AUDIENCE).getString()
        issuer = config.property(Prefix.JWT_ISSUER).getString()
        refreshExpiredMillis = config.property(Prefix.REFRESH_TOKEN_EXPIRED_TIME)
            .getString().toLong() * millisecondPerSecond
        accessExpiredMillis = config.property(Prefix.ACCESS_TOKEN_EXPIRED_TIME)
            .getString().toLong() * millisecondPerSecond
    }

    private object Prefix {
        const val JWT: String = "jwt"
        const val JWT_AUDIENCE: String = "$JWT.audience"
        const val JWT_SECRET: String = "$JWT.secret"
        const val JWT_REALM: String = "$JWT.realm"
        const val JWT_ISSUER: String = "$JWT.issuer"
        const val REFRESH_TOKEN_EXPIRED_TIME: String = "$JWT.token.refresh-expired"
        const val ACCESS_TOKEN_EXPIRED_TIME: String = "$JWT.token.access-expired"
    }
}
