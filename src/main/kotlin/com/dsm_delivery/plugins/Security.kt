package com.dsm_delivery.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.response.*
import org.koin.java.KoinJavaComponent.get

/**
 *
 * 보안 구성을 담당하는 Security
 *
 * @author Chokyunghyeon
 * @date 2023/03/16
 **/
class SecurityProperties(config: ApplicationConfig) {

    val realm: String = config.property(JWT_REALM).getString()
    val secret: String = config.property(JWT_SECRET).getString()
    val audience: String = config.property(JWT_AUDIENCE).getString()
    val issuer: String = config.property(JWT_ISSUER).getString()

    val refreshExpired: Long = config.property(REFRESH_TOKEN_EXPIRED_TIME).getString().toLong()
    val accessExpired: Long = config.property(ACCESS_TOKEN_EXPIRED_TIME).getString().toLong()

    private companion object {
        const val JWT_AUDIENCE: String = "jwt.audience"
        const val JWT_SECRET: String = "jwt.secret"
        const val JWT_REALM: String = "jwt.realm"
        const val JWT_ISSUER: String = "jwt.issuer"
        const val REFRESH_TOKEN_EXPIRED_TIME = "jwt.token.refresh-expired"
        const val ACCESS_TOKEN_EXPIRED_TIME = "jwt.token.access-expired"
    }
}

fun Application.configureSecurity() {

    val securityProperties: SecurityProperties = get(SecurityProperties::class.java)

    authentication {
        jwt {
            realm = securityProperties.realm

            verifier(
                JWT
                    .require(Algorithm.HMAC256(securityProperties.secret))
                    .withAudience(securityProperties.audience)
                    .withIssuer(securityProperties.issuer)
                    .build()
            )

            validate { credential ->
                if (credential.payload.getClaim("id").asString() != null) JWTPrincipal(credential.payload) else null
            }

            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}
