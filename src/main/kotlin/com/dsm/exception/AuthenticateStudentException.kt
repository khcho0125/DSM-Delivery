package com.dsm.exception

/**
 *
 * 학생 인증에 관한 예외를 발생시키는 AuthenticateStudentException
 *
 * @author Chokyunghyeon
 * @date 2023/04/04
 **/
object AuthenticateStudentException {

    class AlreadyUsed(override val message: String? = DefaultMessage.ALREADY_USED)
        : DomainException.Conflict(message)

    class UnknownName(override val message: String? = DefaultMessage.UNKNOWN_NAME)
        : DomainException.Unauthorized(message)

    class UnknownNumber(override val message: String? = DefaultMessage.UNKNOWN_NUMBER)
        : DomainException.Unauthorized(message)

    private object DefaultMessage {
        const val ALREADY_USED: String = "Already Registered Student"
        const val UNKNOWN_NAME: String = "Unknown Student Name"
        const val UNKNOWN_NUMBER: String = "Unknown Student Number"
    }
}