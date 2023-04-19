package com.dsm.exception

import io.ktor.http.HttpStatusCode

/**
 *
 * 리프레쉬 토큰에 관한 예외를 발생시키는 RefreshTokenException
 *
 * @author Chokyunghyeon
 * @date 2023/04/13
 **/
object RefreshTokenException {

    class NotFound(override val message: String? = "RefreshToken Not Found")
        : DomainException(message, RefreshTokenErrorCode.NOT_FOUND)

}

enum class RefreshTokenErrorCode(
    override val sequence: Int,
    override val defaultMessage: String,
    override val status: HttpStatusCode
) : ErrorCode {

    NOT_FOUND(1, "RefreshToken Not Found", HttpStatusCode.NotFound)

    ;

    override val header: String = "TOKEN"
}