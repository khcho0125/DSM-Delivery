package com.dsm.plugins

import com.dsm.exception.DomainException
import com.dsm.exception.ExceptionResponse
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
                is DomainException -> call.respond(
                    message = ExceptionResponse(cause),
                    status = getHttpStatusCode(cause)
                )
                else -> DomainException.InternalServerError().run {
                    call.respond(
                        message = ExceptionResponse(this),
                        status = getHttpStatusCode(this)
                    )
                }
            }
        }
    }
}

private fun getHttpStatusCode(exception: DomainException): HttpStatusCode = when(exception) {
    is DomainException.BadRequest -> HttpStatusCode.BadRequest
    is DomainException.Unauthorized -> HttpStatusCode.Unauthorized
    is DomainException.NotFound -> HttpStatusCode.NotFound
    is DomainException.Conflict -> HttpStatusCode.Conflict
    else -> HttpStatusCode.InternalServerError
}