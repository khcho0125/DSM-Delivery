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
    open val code: ErrorCode
) : Throwable(message) {

    fun toResponse(): ExceptionResponse = ExceptionResponse(
        message = message ?: code.defaultMessage,
        code = code.serial()
    )

    class NotFound(
        override val message: String? = null,
        override val code: ErrorCode = DomainErrorCode.NOT_FOUND
    ) : DomainException(message, code)

    class Unauthorized(
        override val message: String? = null,
        override val code: ErrorCode = DomainErrorCode.UNAUTHORIZED
    ) : DomainException(message, code)

    class BadRequest(
        override val message: String? = null,
        override val code: ErrorCode = DomainErrorCode.BAD_REQUEST
    ) : DomainException(message, code)

    class Conflict(
        override val message: String? = null,
        override val code: ErrorCode = DomainErrorCode.CONFLICT
    ) : DomainException(message, code)

    class InternalServerError(
        override val message: String? = null,
        override val code: ErrorCode = DomainErrorCode.INTERNAL_SERVER_ERROR
    ) : DomainException(message, code)
}

@Serializable
data class ExceptionResponse(
    val message: String,
    val code: String
)

enum class DomainErrorCode(
    override val sequence: Int,
    override val defaultMessage: String,
    override val status: HttpStatusCode
) : ErrorCode {

    NOT_FOUND(1, "Not Found", HttpStatusCode.NotFound),
    UNAUTHORIZED(2, "Unauthorized", HttpStatusCode.Unauthorized),
    BAD_REQUEST(3, "Bad Request", HttpStatusCode.BadRequest),
    CONFLICT(4, "Conflict", HttpStatusCode.Conflict),
    INTERNAL_SERVER_ERROR(5, "Internal Server Error", HttpStatusCode.InternalServerError)

    ;

    override val header: String = "COMMON"
}
