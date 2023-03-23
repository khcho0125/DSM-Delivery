package com.dsm_delivery.persistence.entity

import com.dsm_delivery.plugins.BCryptFormatter
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
    val name: Column<String> = varchar("name", 20)
    val number: Column<Int> = integer("school_number")
    val sex: Column<Sex> = enumerationByName("sex", 6, Sex::class)
    val password: Column<String> = char("password", 60)
}

enum class Sex {
    FEMALE, MALE
}

data class Student(
    val id: UUID,
    val name: String,
    val number: Int,
    val sex: Sex,
    val password: String
) {

    fun verify(password: String): Unit =
        if(BCryptFormatter.checkPassword(password, this.password)) Unit
        else TODO("throw Different Password Exception")
}