package com.dsm.persistence.entity

import com.dsm.exception.StudentException
import com.dsm.plugins.PasswordFormatter
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow

/**
 *
 * 학생 객체를 담당하는 Student
 *
 * @author Chokyunghyeon
 * @date 2023/03/16
 **/
object StudentTable : IntIdTable("tbl_student") {
    val name: Column<String> = varchar("name", Student.NAME_MAX_LENGTH)
    val number: Column<Int> = integer("school_number")
    val sex: Column<Sex> = enumerationByName("sex", Sex.VALUE_MAX_LENGTH)
    val password: Column<String> = char("password", Student.HASHED_PASSWORD_LENGTH)
    val room: Column<EntityID<Int>> = reference("room_id", DormitoryRoomTable)
}

enum class Sex {
    FEMALE, MALE;

    internal companion object {
        const val VALUE_MAX_LENGTH = 6
    }
}

data class Student(
    val id: Int = 0,
    val name: String,
    val number: Int,
    val sex: Sex,
    val password: String,
    val room: Int
) {

    fun verifyPassword(password: String): Unit =
        if (PasswordFormatter.checkPassword(password, this.password)) {
            Unit
        } else {
            throw StudentException.IncorrectPassword()
        }

    companion object {
        fun register(
            name: String,
            number: Int,
            sex: Sex,
            password: String,
            room: Int
        ): Student = Student(
            name = name,
            number = number,
            sex = sex,
            password = PasswordFormatter.encodePassword(password),
            room = room
        )

        fun of(row: ResultRow): Student = Student(
            id = row[StudentTable.id].value,
            name = row[StudentTable.name],
            number = row[StudentTable.number],
            password = row[StudentTable.password],
            sex = row[StudentTable.sex],
            room = row[StudentTable.room].value
        )

        internal const val NAME_MAX_LENGTH: Int = 20
        internal const val HASHED_PASSWORD_LENGTH: Int = 60
    }
}
