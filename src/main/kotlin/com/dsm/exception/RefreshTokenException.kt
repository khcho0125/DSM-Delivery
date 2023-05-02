package com.dsm.exception

/**
 *
 * 리프레쉬 토큰에 관한 예외를 발생시키는 RefreshTokenException
 *
 * @author Chokyunghyeon
 * @date 2023/04/13
 **/
object RefreshTokenException {

    class NotFound(override val message: String? = null)
        : DomainException.NotFound(message, RefreshTokenErrorCode.NOT_FOUND)

    class ValidToken(override val message: String? = null)
        : DomainException.Unauthorized(message, RefreshTokenErrorCode.VALID_TOKEN)
}

enum class RefreshTokenErrorCode(
    override val sequence: Int,
    override val defaultMessage: String
) : ErrorCode {

    NOT_FOUND(1, "RefreshToken Not Found"),
    VALID_TOKEN(1, "Token is not valid or has expired"),

    ;

    override val header: String = "TOKEN"
}