package com.dsm_delivery.domain.auth

import com.dsm_delivery.domain.Api
import com.dsm_delivery.domain.auth.usecase.StudentLogin
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class AuthApi(
    studentLogin: StudentLogin
) : Api({
    authenticate {
        post("/") {
            call.respond(studentLogin())
        }
    }
})