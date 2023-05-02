package com.dsm.domain.auth.usecase

import com.dsm.domain.auth.token.TokenProvider
import com.dsm.domain.auth.token.TokenResult
import com.dsm.exception.AuthenticateStudentException
import com.dsm.persistence.entity.AuthenticateStudent
import com.dsm.persistence.entity.Student
import com.dsm.persistence.repository.AuthenticateStudentRepository
import com.dsm.persistence.repository.StudentRepository
import com.dsm.plugins.database.dbQuery
import kotlinx.serialization.Serializable
import java.util.UUID

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
        val studentId: UUID = registerStudentAccount(request)
        return@dbQuery tokenProvider.generateToken(studentId)
    }

    private suspend fun registerStudentAccount(request: Request): UUID {
        val authenticate: AuthenticateStudent = authenticateStudentRepository.findByNumber(request.number)
            ?: throw AuthenticateStudentException.UnknownNumber()

        authenticate(request.name)

        val studentId: UUID = studentRepository.insert(Student.register(
            name = request.name,
            number = authenticate.number,
            sex = authenticate.sex,
            password = request.password
        ))
        authenticateStudentRepository.update(authenticate.used())

        return studentId
    }

    @Serializable
    data class Request(
        val number: Int,
        val name: String,
        val password: String,
        val room: Int
    )
}