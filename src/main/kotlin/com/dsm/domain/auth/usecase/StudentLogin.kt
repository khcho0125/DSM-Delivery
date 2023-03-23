package com.dsm.domain.auth.usecase

import com.dsm.domain.auth.token.TokenCarton
import com.dsm.domain.auth.token.TokenProvider
import com.dsm.persistence.entity.Student
import com.dsm.persistence.repository.StudentRepository
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
    suspend operator fun invoke(request: Request): TokenCarton {
        val student: Student = studentRepository.findByNumber(request.number)
            ?: TODO("throw Not Found Exception")

        student.verify(request.password)

        return tokenProvider.generateToken(student.id)
    }

    @Serializable
    data class Request(
        val number: Int,
        val password: String
    )
}
