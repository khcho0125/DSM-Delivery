package com.dsm.persistence.repository

import com.dsm.persistence.entity.AuthenticateStudent

/**
 *
 * 학생 인증 목록을 관리하는 AuthenticateStudentRepository
 *
 * @author Chokyunghyeon
 * @date 2023/03/31
 **/
interface AuthenticateStudentRepository {

    suspend fun findByNumber(number: Int): AuthenticateStudent?

    suspend fun update(authenticateStudent: AuthenticateStudent): Boolean
}
