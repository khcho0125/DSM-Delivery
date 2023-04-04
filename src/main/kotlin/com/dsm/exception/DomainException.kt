package com.dsm.exception

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

/**
 *
 * 예외 처리를 담당하는 DomainException
 *
 * @author Chokyunghyeon
 * @date 2023/03/30
 **/
sealed class DomainException(
    override val message: String?,
    val status: HttpStatusCode
) : Throwable(message) {

    fun toResponse(): ExceptionResponse = ExceptionResponse(
        message = message ?: status.description,
        status = status.value
    )

    open class NotFound(override val message: String? = null)
        : DomainException(message, HttpStatusCode.NotFound)

    open class Unauthorized(override val message: String? = null)
        : DomainException(message, HttpStatusCode.Unauthorized)

    open class BadRequest(override val message: String? = null)
        : DomainException(message, HttpStatusCode.BadRequest)

    open class Conflict(override val message: String? = null)
        : DomainException(message, HttpStatusCode.Conflict)
}

@Serializable
data class ExceptionResponse(
    val message: String,
    val status: Int
)
