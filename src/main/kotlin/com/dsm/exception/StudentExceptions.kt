package com.dsm.exception

import io.ktor.http.HttpStatusCode

/**
 *
 * 학생에 관한 예외를 발생시키는 StudentExceptions
 *
 * @author Chokyunghyeon
 * @date 2023/03/30
 **/
sealed class StudentExceptions(
    message: String?,
    status: HttpStatusCode
) : DomainException(message, status) {

    class NotFound(message: String? = null) : StudentExceptions(message, HttpStatusCode.NotFound)
    class Unauthorized(message: String? = null) : StudentExceptions(message, HttpStatusCode.Unauthorized)
    class Conflict(message: String? = null) : StudentExceptions(message, HttpStatusCode.Conflict)
}
