package com.dsm_delivery.api

import com.dsm_delivery.domain.auth.usecase.StudentLogin
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

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