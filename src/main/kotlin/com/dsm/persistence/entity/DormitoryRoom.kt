package com.dsm.persistence.entity

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow

/**
 *
 * 기숙사 방을 담당하는 DormitoryRoom
 *
 * @author Chokyunghyeon
 * @date 2023/05/03
 **/
object DormitoryRoomTable : IntIdTable(name = "tbl_dormitory_room")

data class DormitoryRoom(
    val id: Int
) {

    companion object {
        fun of(row: ResultRow) : DormitoryRoom = DormitoryRoom(
            id = row[DormitoryRoomTable.id].value
        )
    }
}