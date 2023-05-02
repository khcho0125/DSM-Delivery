package com.dsm.persistence.repository

import com.dsm.persistence.entity.Student

/**
 *
 * 학생을 관리하는 StudentRepository
 *
 * @author Chokyunghyeon
 * @date 2023/03/22
 **/
interface StudentRepository {
    suspend fun findById(id: Int): Student?

    suspend fun findByNumber(number: Int): Student?

    suspend fun findByName(name: String): Student?

    suspend fun existsById(id: Int): Boolean

    suspend fun insert(student: Student): Int
}
