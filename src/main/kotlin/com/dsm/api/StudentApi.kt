package com.dsm.api

import com.dsm.domain.auth.usecase.StudentLogin
import io.ktor.server.application.call
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
    studentLogin: StudentLogin
) : Api({
    route("/student") {
        post("/login") {
            val request: StudentLogin.Request = call.receive()
            call.respond(studentLogin(request))
        }
    }
})
