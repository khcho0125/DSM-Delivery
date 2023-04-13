package com.dsm.persistence.entity

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

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

    @Contextual
    val studentId: UUID,

    val expired: Long
)