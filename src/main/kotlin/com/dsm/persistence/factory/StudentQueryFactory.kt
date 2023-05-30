package com.dsm.persistence.factory

import com.dsm.persistence.entity.Student
import com.dsm.persistence.entity.StudentTable
import com.dsm.persistence.mapper.StudentMapper
import com.dsm.persistence.repository.StudentRepository
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select

/**
 *
 * 학생에 관한 질의를 요청하는 StudentQuery
 *
 * @author Chokyunghyeon
 * @date 2023/03/22
 **/
class StudentQueryFactory : StudentRepository {

    override suspend fun findById(id: Int): Student? = StudentTable
        .select { StudentTable.id eq id }
        .singleOrNull()
        ?.let(StudentMapper::of)

    override suspend fun findByNumber(number: Int): Student? = StudentTable
        .select { StudentTable.number eq number }
        .singleOrNull()
        ?.let(StudentMapper::of)

    override suspend fun findByName(name: String): Student? = StudentTable
        .select { StudentTable.name eq name }
        .singleOrNull()
        ?.let(StudentMapper::of)

    override suspend fun existsById(id: Int): Boolean = StudentTable
        .select { StudentTable.id eq id }
        .limit(1)
        .empty().not()

    override suspend fun insert(student: Student): Int = StudentTable.insertAndGetId {
        it[name] = student.name
        it[number] = student.number
        it[password] = student.password
        it[sex] = student.sex
        it[room] = student.room
    }.value
}
