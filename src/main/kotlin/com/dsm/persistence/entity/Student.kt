package com.dsm.persistence.entity

import com.dsm.exception.DomainException
import com.dsm.exception.StudentException
import com.dsm.plugins.PasswordFormatter
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

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
    val password: Column<String> = char("password", Password.HASHED_LENGTH)
    val room: Column<EntityID<Int>> = reference("room_id", DormitoryRoomTable.id)
}

enum class Sex {
    FEMALE, MALE;

    internal companion object {
        const val VALUE_MAX_LENGTH = 6
    }
}

@JvmInline
value class Password(private val value: String) {
    init {
        if(value.matches(ALLOWED_PATTERN).not()) {
            throw DomainException.BadRequest("Invalid Password Configuration")
        }
    }

    fun encode(): String = PasswordFormatter.encodePassword(value)

    companion object {
        fun verify(password: String, encodePassword: String) {
            if (PasswordFormatter.correctPassword(password, encodePassword).not()) {
                throw StudentException.IncorrectPassword()
            }
        }

        private const val MIN_LENGTH: Int = 8
        private const val MAX_LENGTH: Int = 20

        private val ALLOWED_PATTERN = Regex("""(?=.*[a-zA-Z])(?=.*\d)(?=^[\w$+-]{$MIN_LENGTH,$MAX_LENGTH}$).*""")

        internal const val HASHED_LENGTH: Int = 60
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

    companion object {
        fun register(
            authenticate: AuthenticateStudent,
            password: Password,
            room: Int
        ) = Student(
            name = authenticate.name,
            number = authenticate.number,
            sex = authenticate.sex,
            password = password.encode(),
            room = room
        )

        internal const val NAME_MAX_LENGTH: Int = 20
    }
}
