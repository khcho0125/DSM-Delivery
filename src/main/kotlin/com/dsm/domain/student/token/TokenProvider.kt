package com.dsm.domain.student.token

/**
 *
 * 토큰을 제공하는 TokenProvider
 *
 * @author Chokyunghyeon
 * @date 2023/03/20
 **/
interface TokenProvider {
    suspend fun generateToken(studentId: Int): TokenResult
}
