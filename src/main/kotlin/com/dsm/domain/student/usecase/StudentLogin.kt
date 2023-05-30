package com.dsm.domain.student.usecase

import com.dsm.domain.student.token.TokenProvider
import com.dsm.domain.student.token.TokenResult
import com.dsm.exception.StudentException
import com.dsm.persistence.entity.Password
import com.dsm.persistence.entity.Student
import com.dsm.persistence.repository.StudentRepository
import com.dsm.plugins.database.dbQuery
import kotlinx.serialization.Serializable

/**
 *
 * 학생 로그인을 담당하는 StudentLogin
 *
 * @author Chokyunghyeon
 * @date 2023/03/20
 **/
class StudentLogin(
    private val tokenProvider: TokenProvider,
    private val studentRepository: StudentRepository
) {

    suspend operator fun invoke(request: Request): Response = dbQuery {
        val student: Student = studentRepository.findByNumber(request.number)
            ?: throw StudentException.NotFound()

        Password.verify(request.password, student.password)

        return@dbQuery Response(
            id = student.id,
            token = tokenProvider.generateToken(student.id)
        )
    }

    data class Request(
        val number: Int,
        val password: String
    )

    @Serializable
    data class Response(
        val id: Int,
        val token: TokenResult
    )
}
