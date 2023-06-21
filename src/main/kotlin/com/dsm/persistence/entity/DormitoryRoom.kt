package com.dsm.persistence.entity

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

/**
 *
 * 기숙사 방을 담당하는 DormitoryRoom
 *
 * @author Chokyunghyeon
 * @date 2023/05/03
 **/
object DormitoryRoomTable : IdTable<Int>(name = "tbl_dormitory_room") {
    override val id: Column<EntityID<Int>> = integer("id").entityId()

    override val primaryKey = PrimaryKey(id)
}
