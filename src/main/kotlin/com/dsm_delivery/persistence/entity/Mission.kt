package com.dsm_delivery.persistence.entity

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.UUID

/**
 *
 * 배달 미션 객체를 담당하는 Mission
 *
 * @author Chokyunghyeon
 * @date 2023/03/16
 **/
object MissionTable : UUIDTable("tbl_mission") {
    val student: Column<EntityID<UUID>> = reference("student_id", StudentTable).uniqueIndex()
    val deliveryman: Column<EntityID<UUID>?> = reference("delivery_man_id", StudentTable).nullable()
    val stuff: Column<String> = varchar("stuff", 50)
    val deadline: Column<LocalDateTime> = datetime("deadline")
    val state: Column<DeliveryState> = enumerationByName("state", 10, DeliveryState::class)
}

enum class DeliveryState {
    POSTING, DELIVERING, COMPLETED, MISSED
}

class Mission(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Mission>(MissionTable)

    val student: Student by Student referencedOn MissionTable.student
    var state: DeliveryState by MissionTable.state
    var stuff: String by MissionTable.stuff
    var deadline: LocalDateTime by MissionTable.deadline
    var deliveryState: Student? by Student optionalReferencedOn MissionTable.deliveryman
}