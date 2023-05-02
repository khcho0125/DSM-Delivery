package com.dsm.exception

/**
 *
 * 학생에 관한 예외를 발생시키는 StudentException
 *
 * @author Chokyunghyeon
 * @date 2023/03/30
 **/
object StudentException {

    class NotFound(override val message: String? = null)
        : DomainException.NotFound(message, StudentErrorCode.NOT_FOUND)

    class IncorrectPassword(override val message: String? = null)
        : DomainException.Unauthorized(message, StudentErrorCode.INCORRECT_PASSWORD)

}

enum class StudentErrorCode(
    override val sequence: Int,
    override val defaultMessage: String
) : ErrorCode {

    NOT_FOUND(1, "Student Not Found"),
    INCORRECT_PASSWORD(2, "Password Not Matched"),

    ;

    override val header: String = "STUDENT"
}