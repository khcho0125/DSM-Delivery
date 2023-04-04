package com.dsm.domain.auth.usecase

import com.dsm.domain.auth.token.TokenProvider
import com.dsm.domain.auth.token.TokenResult
import com.dsm.exception.AuthenticateStudentException
import com.dsm.persistence.entity.AuthenticateStudent
import com.dsm.persistence.entity.Student
import com.dsm.persistence.repository.AuthenticateStudentRepository
import com.dsm.persistence.repository.StudentRepository
import com.dsm.plugins.DataBaseFactory.dbQuery
import kotlinx.serialization.Serializable

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
    suspend operator fun invoke(request: Request): TokenResult = dbQuery {
        val student: Student = registerStudentAccount(request)
        tokenProvider.generateToken(student.id)
    }

    private suspend fun registerStudentAccount(request: Request): Student {
        val authenticate: AuthenticateStudent = authenticateStudentRepository.findByNumber(request.number)
            ?: throw AuthenticateStudentException.UnknownNumber()

        authenticate(request.name)

        val student: Student = studentRepository.insert(Student.register(
            name = request.name,
            number = authenticate.number,
            sex = authenticate.sex,
            password = request.password
        ))
        authenticateStudentRepository.update(authenticate.used())

        return student
    }

    @Serializable
    data class Request(
        val number: Int,
        val name: String,
        val password: String,
        val room: Int
    )
}