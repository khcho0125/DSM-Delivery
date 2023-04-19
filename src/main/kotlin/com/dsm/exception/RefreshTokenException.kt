package com.dsm.exception

/**
 *
 * 리프레쉬 토큰에 관한 예외를 발생시키는 RefreshTokenException
 *
 * @author Chokyunghyeon
 * @date 2023/04/13
 **/
object RefreshTokenException {

    class NotFound(override val message: String? = "RefreshToken Not Found")
        : DomainException.NotFound(message)

}