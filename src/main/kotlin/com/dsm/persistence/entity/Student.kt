package com.dsm.persistence.entity

import com.dsm.plugins.BCryptFormatter
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import java.util.UUID

/**
 *
 * 학생 객체를 담당하는 Student
 *
 * @author Chokyunghyeon
 * @date 2023/03/16
 **/
object StudentTable : UUIDTable("tbl_student") {
    val name: Column<String> = varchar("name", Student.NAME_MAX_LENGTH)
    val number: Column<Int> = integer("school_number")
    val sex: Column<Sex> = enumerationByName("sex", Sex.VALUE_MAX_LENGTH, Sex::class)
    val password: Column<String> = char("password", Student.HASHED_PASSWORD_LENGTH)
}

enum class Sex {
    FEMALE, MALE;

    internal companion object {
        const val VALUE_MAX_LENGTH = 6
    }
}

data class Student(
    val id: UUID,
    val name: String,
    val number: Int,
    val sex: Sex,
    val password: String
) {

    fun verify(password: String): Unit =
        if (BCryptFormatter.checkPassword(password, this.password)) {
            Unit
        } else {
            TODO("throw Different Password Exception")
        }

    internal companion object {
        const val NAME_MAX_LENGTH = 20
        const val HASHED_PASSWORD_LENGTH = 60
    }
}
