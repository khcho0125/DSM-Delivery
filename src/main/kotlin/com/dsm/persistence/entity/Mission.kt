package com.dsm.persistence.entity

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

/**
 *
 * 배달 미션 객체를 담당하는 Mission
 *
 * @author Chokyunghyeon
 * @date 2023/03/16
 **/
object MissionTable : IntIdTable("tbl_mission") {
    val order: Column<EntityID<Int>> = reference("order_id", StudentTable).uniqueIndex()
    val shipper: Column<EntityID<Int>?> = reference("shipper_id", StudentTable).nullable()
    val stuff: Column<String> = varchar("stuff", Mission.STUFF_MAX_LENGTH)
    val deadline: Column<LocalDateTime> = datetime("deadline")
    val price: Column<Long> = long("price")
    val state: Column<DeliveryState> = enumerationByName("state", DeliveryState.STATE_MAX_LENGTH)
}

enum class DeliveryState {
    POSTING, DELIVERING, COMPLETED, MISSED;

    internal companion object {
        const val STATE_MAX_LENGTH: Int = 10
    }
}

data class Mission(
    val id: Int = 0,
    val orderId: Int,
    val shipperId: Int?,
    val stuff: String,
    val deadline: LocalDateTime,
    val price: Long,
    val state: DeliveryState
) {

    internal companion object {
        const val STUFF_MAX_LENGTH: Int = 50

        fun doPost(orderId: Int, stuff: String, deadline: LocalDateTime, price: Long): Mission = Mission(
            orderId = orderId,
            stuff = stuff,
            deadline = deadline,
            state = DeliveryState.POSTING,
            price = price,
            shipperId = null
        )
    }
}
