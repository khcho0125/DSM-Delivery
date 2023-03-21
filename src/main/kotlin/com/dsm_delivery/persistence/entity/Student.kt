package com.dsm_delivery.persistence.entity

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
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
}

enum class Sex {
    FEMALE, MALE
}

class Student(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Student>(StudentTable)

    var name: String by StudentTable.name
    var number: Int by StudentTable.number
    val sex: Sex by StudentTable.sex
}