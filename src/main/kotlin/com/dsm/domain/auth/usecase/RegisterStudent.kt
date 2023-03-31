package com.dsm.domain.auth.usecase

import com.dsm.domain.auth.token.TokenContainer
import com.dsm.domain.auth.token.TokenProvider
import com.dsm.exception.StudentExceptions
import com.dsm.persistence.entity.AuthenticateStudent
import com.dsm.persistence.entity.Student
import com.dsm.persistence.repository.AuthenticateStudentRepository
import com.dsm.persistence.repository.StudentRepository
import io.ktor.http.HttpStatusCode

/**
 *
 * 학생 가입을 담당하는 RegisterStudent
 *
 * @author Chokyunghyeon
 * @date 2023/03/31
 **/
class RegisterStudent(
    private val authenticateStudentRepository: AuthenticateStudentRepository,
    private val studentRepository: StudentRepository,
    private val tokenProvider: TokenProvider
) {
    suspend operator fun invoke(request: Request): TokenContainer {
        val authenticate: AuthenticateStudent = authenticateStudentRepository.findByNumber(request.number)
            ?: throw StudentExceptions.Unauthorized("Not Allowed Student Number")

        authenticate.check(request.name)

        val student: Student = studentRepository.insert(Student.register(
            name = authenticate.name,
            number = authenticate.number,
            sex = authenticate.sex,
            password = request.password
        ))
        authenticateStudentRepository.updateIsUsed(authenticate.used())

        return tokenProvider.generateToken(student.id)
    }

    data class Request(
        val number: Int,
        val name: String,
        val password: String,
        val room: Int
    )

    companion object {
        val SUCCESS_STATUS: HttpStatusCode = HttpStatusCode.Created
    }
}