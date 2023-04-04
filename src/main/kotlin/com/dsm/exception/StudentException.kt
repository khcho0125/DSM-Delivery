package com.dsm.exception

/**
 *
 * 학생에 관한 예외를 발생시키는 StudentException
 *
 * @author Chokyunghyeon
 * @date 2023/03/30
 **/
object StudentException {

    class NotFound(override val message: String? = DefaultMessage.NOTFOUND)
        : DomainException.NotFound(message)

    class IncorrectPassword(override val message: String? = DefaultMessage.INCORRECT_PASSWORD)
        : DomainException.Unauthorized(message)



    private object DefaultMessage {
        const val NOTFOUND: String = "Student Not Found"
        const val INCORRECT_PASSWORD: String = "Password Not Matched"
    }
}
