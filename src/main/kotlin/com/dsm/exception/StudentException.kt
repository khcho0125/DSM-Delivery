package com.dsm.exception

/**
 *
 * 학생에 관한 예외를 발생시키는 StudentException
 *
 * @author Chokyunghyeon
 * @date 2023/03/30
 **/
object StudentException {

    class NotFound(override val message: String? = "Student Not Found")
        : DomainException.NotFound(message)

    class IncorrectPassword(override val message: String? = "Password Not Matched")
        : DomainException.Unauthorized(message)

}
