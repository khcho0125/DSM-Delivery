package com.dsm.persistence.entity

import com.dsm.exception.StudentException
import com.dsm.plugins.PasswordFormatter
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
    val id: UUID = UUID(0, 0),
    val name: String,
    val number: Int,
    val sex: Sex,
    val password: String
) {

    fun verifyPassword(password: String): Unit =
        if (PasswordFormatter.checkPassword(password, this.password)) {
            Unit
        } else {
            throw StudentException.IncorrectPassword()
        }

    companion object {
        fun register(name: String, number: Int, sex: Sex, password: String): Student = Student(
            name = name,
            number = number,
            sex = sex,
            password = PasswordFormatter.encodePassword(password)
        )

        internal const val NAME_MAX_LENGTH: Int = 20
        internal const val HASHED_PASSWORD_LENGTH: Int = 60
    }
}
