package com.dsm_delivery.api

import com.dsm_delivery.domain.auth.usecase.StudentLogin
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class SecurityApi(
    studentLogin: StudentLogin
) : Api({
    route("/student") {
        post("/login") {
            val request: StudentLogin.Request = call.receive()
            call.respond(
                status = HttpStatusCode.OK,
                message = studentLogin(request)
            )
        }
    }
})