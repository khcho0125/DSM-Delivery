package com.dsm.persistence.factory

import com.dsm.persistence.entity.Student
import com.dsm.persistence.entity.StudentTable
import com.dsm.persistence.repository.StudentRepository
import com.dsm.plugins.DataBaseFactory.dbQuery
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
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

    override suspend fun findById(id: UUID): Student? = dbQuery {
        StudentTable
            .select { StudentTable.id eq id }
            .singleOrNull()
            ?.let(::toEntity)
    }

    override suspend fun findByNumber(number: Int): Student? = dbQuery {
        StudentTable
            .select { StudentTable.number eq number }
            .singleOrNull()
            ?.let(::toEntity)
    }

    override suspend fun findByName(name: String): Student? = dbQuery {
        StudentTable
            .select { StudentTable.name eq name }
            .singleOrNull()
            ?.let(::toEntity)
    }

    override suspend fun findBy(where: () -> Op<Boolean>): Student? = dbQuery {
        StudentTable
            .select(where())
            .singleOrNull()
            ?.let(::toEntity)
    }

    override suspend fun existsById(id: UUID): Boolean = dbQuery {
        StudentTable
            .select { StudentTable.id eq id }
            .empty()
    }

    override suspend fun existsBy(where: () -> Op<Boolean>): Boolean = dbQuery {
        StudentTable
            .select(where())
            .empty()
    }
}
