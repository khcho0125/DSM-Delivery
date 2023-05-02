package com.dsm.exception

/**
 *
 * 학생 인증에 관한 예외를 발생시키는 AuthenticateStudentException
 *
 * @author Chokyunghyeon
 * @date 2023/04/04
 **/
object AuthenticateStudentException {

    class AlreadyUsed(override val message: String? = null)
        : DomainException.Conflict(message, AuthenticateStudentErrorCode.ALREADY_USED)

    class UnknownName(override val message: String? = null)
        : DomainException.Unauthorized(message, AuthenticateStudentErrorCode.UNKNOWN_NAME)

    class UnknownNumber(override val message: String? = null)
        : DomainException.Unauthorized(message, AuthenticateStudentErrorCode.UNKNOWN_NUMBER)

}

enum class AuthenticateStudentErrorCode(
    override val sequence: Int,
    override val defaultMessage: String,
) : ErrorCode {

    ALREADY_USED(1, "Already Registered Student"),
    UNKNOWN_NAME(2, "Unknown Student Name"),
    UNKNOWN_NUMBER(3, "Unknown Student Number")

    ;

    override val header: String = "AUTH"
}