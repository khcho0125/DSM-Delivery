package com.dsm_delivery.domain.auth.usecase

import com.dsm_delivery.domain.auth.token.TokenCarton
import com.dsm_delivery.domain.auth.token.TokenProvider
import com.dsm_delivery.persistence.entity.Student

/**
 *
 * 학생 로그인을 담당하는 StudentLogin
 *
 * @author Chokyunghyeon
 * @date 2023/03/20
 **/
class StudentLogin(
    private val tokenProvider: TokenProvider
) {
    suspend operator fun invoke(request: Request) : TokenCarton {
        val student: Student = Student.findByNumber(request.number)
            ?: TODO()

        return tokenProvider.generateToken(student.id.value)
    }

    data class Request(
        val number: Int,
        val password: String
    )
}