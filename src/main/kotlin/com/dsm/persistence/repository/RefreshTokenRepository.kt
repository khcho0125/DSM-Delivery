package com.dsm.persistence.repository

import com.dsm.persistence.entity.RefreshToken

/**
 *
 * 리프레쉬 토큰을 관리하는 RefreshTokenRepository
 *
 * @author Chokyunghyeon
 * @date 2023/04/09
 **/
interface RefreshTokenRepository {
    suspend fun insert(refreshToken: RefreshToken): RefreshToken

    suspend fun findByToken(token: String): RefreshToken?
}