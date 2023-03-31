package com.dsm.persistence.factory

import com.dsm.persistence.entity.AuthenticateStudent
import com.dsm.persistence.entity.AuthenticateStudentTable
import com.dsm.persistence.repository.AuthenticateStudentRepository
import com.dsm.plugins.DataBaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update

/**
 *
 * 학생 인증 목록에 관한 질의를 요청하는 AuthenticateStudentQueryFactory
 *
 * @author Chokyunghyeon
 * @date 2023/03/31
 **/
class AuthenticateStudentQueryFactory : AuthenticateStudentRepository {

    private fun toEntity(row: ResultRow): AuthenticateStudent = AuthenticateStudent(
        number = row[AuthenticateStudentTable.number].value,
        name = row[AuthenticateStudentTable.name],
        isUsed = row[AuthenticateStudentTable.isUsed],
        sex = row[AuthenticateStudentTable.sex]
    )

    override suspend fun findByNumber(number: Int): AuthenticateStudent? = dbQuery {
        AuthenticateStudentTable
            .select { AuthenticateStudentTable.number eq number }
            .singleOrNull()
            ?.let(::toEntity)
    }

    override suspend fun updateIsUsed(authenticateStudent: AuthenticateStudent): Boolean = dbQuery {
        AuthenticateStudentTable
            .update({ AuthenticateStudentTable.number eq authenticateStudent.number }) {
                it[isUsed] = authenticateStudent.isUsed
            } > 0
    }
}