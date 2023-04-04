package com.dsm.persistence.repository

import com.dsm.persistence.entity.Student
import org.jetbrains.exposed.sql.Op
import java.util.UUID

/**
 *
 * 학생을 관리하는 StudentRepository
 *
 * @author Chokyunghyeon
 * @date 2023/03/22
 **/
interface StudentRepository {
    suspend fun findById(id: UUID): Student?

    suspend fun findByNumber(number: Int): Student?

    suspend fun findByName(name: String): Student?

    suspend fun findBy(where: () -> Op<Boolean>): Student?

    suspend fun existsById(id: UUID): Boolean

    suspend fun existsBy(where: () -> Op<Boolean>): Boolean

    suspend fun insert(student: Student): Student
}
