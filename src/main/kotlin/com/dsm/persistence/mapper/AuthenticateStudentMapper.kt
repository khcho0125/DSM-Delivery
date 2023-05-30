package com.dsm.persistence.mapper

import com.dsm.persistence.entity.AuthenticateStudent
import com.dsm.persistence.entity.AuthenticateStudentTable
import org.jetbrains.exposed.sql.ResultRow

/**
 *
 * 학생 인증 애그리거트의 매핑을 담당하는 AuthenticateStudentMapper
 *
 * @author Chokyunghyeon
 * @date 2023/05/29
 **/
object AuthenticateStudentMapper {

    fun of(row: ResultRow) = AuthenticateStudent(
        number = row[AuthenticateStudentTable.number],
        name = row[AuthenticateStudentTable.name],
        isUsed = row[AuthenticateStudentTable.isUsed],
        sex = row[AuthenticateStudentTable.sex]
    )
}