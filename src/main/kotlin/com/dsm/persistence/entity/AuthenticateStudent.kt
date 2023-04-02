package com.dsm.persistence.entity

import com.dsm.exception.StudentExceptions
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 *
 * 학생 인증 목록을 관리하는 AuthenticateStudent
 *
 * @author Chokyunghyeon
 * @date 2023/03/31
 **/
object AuthenticateStudentTable : Table("tbl_authenticate_student") {
    val number: Column<Int> = integer("number")
    val name: Column<String> = varchar("name", Student.NAME_MAX_LENGTH)
    val sex: Column<Sex> = enumerationByName("sex", Sex.VALUE_MAX_LENGTH, Sex::class)
    val isUsed: Column<Boolean> = bool("is_used")

    override val primaryKey: PrimaryKey = PrimaryKey(number)
}

data class AuthenticateStudent(
    val number: Int,
    val name: String,
    val isUsed: Boolean,
    val sex: Sex
) {

    fun used(): AuthenticateStudent = copy(isUsed = true)

    operator fun invoke(name: String) {
        when {
            isUsed -> throw StudentExceptions.Conflict(ALREADY_USED)
            this.name != name -> throw StudentExceptions.Unauthorized(DIFFERENT_NAME)
        }
    }

    internal companion object {
        const val ALREADY_USED: String = "Already Registered Student"
        const val DIFFERENT_NAME: String = "Not Matched Student Name"
    }
}