package com.dsm_delivery.domain.auth.token

import java.util.UUID

/**
 *
 * 토큰을 제공하는 TokenProvider
 *
 * @author Chokyunghyeon
 * @date 2023/03/20
 **/
interface TokenProvider {
    suspend fun generateToken(studentId: UUID) : TokenCarton
}