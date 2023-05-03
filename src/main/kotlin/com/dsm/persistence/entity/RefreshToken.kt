package com.dsm.persistence.entity

import kotlinx.serialization.Serializable

/**
 *
 * JWT Refresh 토큰을 담당하는 RefreshToken
 *
 * @author Chokyunghyeon
 * @date 2023/04/04
 **/
@Serializable
data class RefreshToken(
    val token: String,

    val studentId: Int,

    val expiredMillis: Long
)