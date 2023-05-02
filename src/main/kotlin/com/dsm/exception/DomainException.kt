package com.dsm.exception

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

    open class NotFound(
        override val message: String? = null,
        override val code: ErrorCode = DomainErrorCode.NOT_FOUND
    ) : DomainException(message, code)

    open class Unauthorized(
        override val message: String? = null,
        override val code: ErrorCode = DomainErrorCode.UNAUTHORIZED
    ) : DomainException(message, code)

    open class BadRequest(
        override val message: String? = null,
        override val code: ErrorCode = DomainErrorCode.BAD_REQUEST
    ) : DomainException(message, code)

    open class Conflict(
        override val message: String? = null,
        override val code: ErrorCode = DomainErrorCode.CONFLICT
    ) : DomainException(message, code)

    open class InternalServerError(
        override val message: String? = null,
        override val code: ErrorCode = DomainErrorCode.INTERNAL_SERVER_ERROR
    ) : DomainException(message, code)
}

@Serializable
data class ExceptionResponse(
    val message: String,
    val code: String
) {

    constructor(exception: DomainException) : this(
        message = exception.message ?: exception.code.defaultMessage,
        code = exception.code.serial()
    )
}

enum class DomainErrorCode(
    override val sequence: Int,
    override val defaultMessage: String
) : ErrorCode {

    NOT_FOUND(1, "Not Found"),
    UNAUTHORIZED(2, "Unauthorized"),
    BAD_REQUEST(3, "Bad Request"),
    CONFLICT(4, "Conflict"),
    INTERNAL_SERVER_ERROR(5, "Internal Server Error")

    ;

    override val header: String = "COMMON"
}
