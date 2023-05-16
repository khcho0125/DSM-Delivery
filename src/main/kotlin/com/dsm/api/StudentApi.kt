package com.dsm.api

import com.dsm.domain.student.usecase.RegisterStudent
import com.dsm.domain.student.usecase.ReissueToken
import com.dsm.domain.student.usecase.StudentLogin
import com.dsm.exception.DomainException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.header
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

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
}) {
    companion object {
        val module: Module = module {
            singleOf(::StudentLogin)
            singleOf(::RegisterStudent)
            singleOf(::ReissueToken)
            singleOf(::StudentApi) bind Api::class
        }
    }
}