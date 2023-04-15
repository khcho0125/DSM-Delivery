package com.dsm.exception

/**
 *
 * 학생 인증에 관한 예외를 발생시키는 AuthenticateStudentException
 *
 * @author Chokyunghyeon
 * @date 2023/04/04
 **/
object AuthenticateStudentException {

    class AlreadyUsed(override val message: String? = "Already Registered Student")
        : DomainException.Conflict(message)

    class UnknownName(override val message: String? = "Unknown Student Name")
        : DomainException.Unauthorized(message)

    class UnknownNumber(override val message: String? = "Unknown Student Number")
        : DomainException.Unauthorized(message)

}