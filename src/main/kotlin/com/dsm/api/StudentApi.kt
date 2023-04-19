package com.dsm.api

import com.dsm.domain.auth.usecase.RegisterStudent
import com.dsm.domain.auth.usecase.ReissueToken
import com.dsm.domain.auth.usecase.StudentLogin
import com.dsm.exception.DomainException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.header
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.route

/**
 *
 * 학생의 API를 관리하는 StudentApi
 *
 * @author Chokyunghyeon
 * @date 2023/03/22
 **/
class StudentApi(
    studentLogin: StudentLogin,
    registerStudent: RegisterStudent,
    reissueToken: ReissueToken
) : Api({
    route("/student") {
        post("/login") {
            val request: StudentLogin.Request = call.receive()
            call.respond(
                message = studentLogin(request),
                status = HttpStatusCode.OK
            )
        }

        post("/signup") {
            val request: RegisterStudent.Request = call.receive()
            call.respond(
                message = registerStudent(request),
                status = HttpStatusCode.Created
            )
        }

        post("/token") {
            val token: String = call.request.header("Reissue-Token")
                ?: throw DomainException.BadRequest("Empty Header Reissue-Token")

            call.respond(
                message = reissueToken(token),
                status = HttpStatusCode.OK
            )
        }
    }
})