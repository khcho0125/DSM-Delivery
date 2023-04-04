package com.dsm.persistence.factory

import com.dsm.persistence.entity.Student
import com.dsm.persistence.entity.StudentTable
import com.dsm.persistence.repository.StudentRepository
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.util.UUID

/**
 *
 * 학생에 관한 질의를 요청하는 StudentQuery
 *
 * @author Chokyunghyeon
 * @date 2023/03/22
 **/
class StudentQueryFactory : StudentRepository {

    private fun toEntity(row: ResultRow): Student = Student(
        id = row[StudentTable.id].value,
        name = row[StudentTable.name],
        number = row[StudentTable.number],
        password = row[StudentTable.password],
        sex = row[StudentTable.sex]
    )

    override suspend fun findById(id: UUID): Student? = StudentTable
        .select { StudentTable.id eq id }
        .singleOrNull()
        ?.let(::toEntity)

    override suspend fun findByNumber(number: Int): Student? = StudentTable
        .select { StudentTable.number eq number }
        .singleOrNull()
        ?.let(::toEntity)

    override suspend fun findByName(name: String): Student? = StudentTable
        .select { StudentTable.name eq name }
        .singleOrNull()
        ?.let(::toEntity)

    override suspend fun findBy(where: () -> Op<Boolean>): Student? = StudentTable
        .select(where())
        .singleOrNull()
        ?.let(::toEntity)

    override suspend fun existsById(id: UUID): Boolean = StudentTable
        .select { StudentTable.id eq id }
        .empty()

    override suspend fun existsBy(where: () -> Op<Boolean>): Boolean = StudentTable
        .select(where())
        .empty()

    override suspend fun insert(student: Student): Student = StudentTable.insert {
        it[name] = student.name
        it[number] = student.number
        it[password] = student.password
        it[sex] = student.sex
    }.resultedValues!!.single().let(::toEntity)
}
