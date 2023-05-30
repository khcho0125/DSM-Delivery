package com.dsm.persistence.mapper

import com.dsm.persistence.entity.Student
import com.dsm.persistence.entity.StudentTable
import org.jetbrains.exposed.sql.ResultRow

/**
 *
 * 학생 애그리거트의 매핑을 담당하는 StudentMapper
 *
 * @author Chokyunghyeon
 * @date 2023/05/29
 **/
object StudentMapper {

    fun of(row: ResultRow) = Student(
        id = row[StudentTable.id].value,
        name = row[StudentTable.name],
        number = row[StudentTable.number],
        password = row[StudentTable.password],
        sex = row[StudentTable.sex],
        room = row[StudentTable.room].value
    )
}