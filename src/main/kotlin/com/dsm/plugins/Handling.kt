package com.dsm.plugins

import com.dsm.exception.DomainException
import com.dsm.exception.ExceptionContainer
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

/**
 *
 * 예외 핸들링을 담당하는 Handling
 *
 * @author Chokyunghyeon
 * @date 2023/03/30
 **/
fun Application.configureHandling() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when (cause) {
                is DomainException -> {
                    call.respond(
                        message = cause.toResponse(),
                        status = cause.status
                    )
                }
                else -> {
                    call.respond(
                        message = HttpStatusCode.InternalServerError.run {
                            ExceptionContainer(
                                message = description,
                                status = value
                            )
                        },
                        status = HttpStatusCode.InternalServerError
                    )
                }
            }
        }
    }
}
