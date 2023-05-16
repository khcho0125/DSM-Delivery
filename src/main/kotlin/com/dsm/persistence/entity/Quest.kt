package com.dsm.persistence.entity

import com.dsm.exception.QuestException
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

/**
 *
 * 배달 퀘스트 객체를 담당하는 Quest
 *
 * @author Chokyunghyeon
 * @date 2023/03/16
 **/
object QuestTable : IntIdTable("tbl_quest") {
    val owner: Column<EntityID<Int>> = reference("owner_id", StudentTable).uniqueIndex()
    val acceptor: Column<EntityID<Int>?> = reference("acceptor_id", StudentTable).nullable()
    val stuff: Column<String> = varchar("stuff", Quest.STUFF_MAX_LENGTH)
    val deadline: Column<LocalDateTime> = datetime("deadline")
    val price: Column<Long> = long("price")
    val state: Column<QuestState> = enumerationByName("state", QuestState.STATE_MAX_LENGTH)
}

enum class QuestState {
    PUBLISHING, DELIVERING, COMPLETED, CANCELED, MISSING;

    internal companion object {
        const val STATE_MAX_LENGTH: Int = 10
    }
}

data class Quest(
    val id: Int = 0,
    val ownerId: Int,
    val acceptorId: Int?,
    val stuff: String,
    val deadline: LocalDateTime,
    val price: Long,
    val state: QuestState
) {

    fun accept(studentId: Int): Quest {
        if(this.state != QuestState.PUBLISHING) {
            throw QuestException.DifferentState(QuestState.PUBLISHING)
        }

        return this.copy(
            acceptorId = studentId,
            state = QuestState.DELIVERING,
            deadline = LocalDateTime.now().plusMinutes(DELIVERY_TIME)
        )
    }

    fun complete(): Quest {
        if(this.state !in arrayOf(QuestState.MISSING, QuestState.DELIVERING)) {
            throw QuestException.DifferentState()
        }

        return this.copy(
            state = QuestState.COMPLETED
        )
    }

    fun cancel(): Quest {
        if(this.state != QuestState.PUBLISHING) {
            throw QuestException.DifferentState(QuestState.PUBLISHING)
        }

        return this.copy(
            state = QuestState.CANCELED
        )
    }

    fun failure(): Quest {
        if(this.state != QuestState.DELIVERING) {
            throw QuestException.DifferentState(QuestState.DELIVERING)
        }

        if(this.deadline.isBefore(LocalDateTime.now())) {
            throw QuestException.NotTimeout()
        }

        return this.copy(
            state = QuestState.MISSING
        )
    }

    internal companion object {
        const val STUFF_MAX_LENGTH: Int = 50
        const val DELIVERY_TIME: Long = 10L

        fun publish(orderId: Int, stuff: String, deadline: LocalDateTime, price: Long): Quest {
            if (stuff.length > STUFF_MAX_LENGTH) {
                throw QuestException
                    .OutOfLimitLength("Too much long stuff (${stuff.length}/$STUFF_MAX_LENGTH)")
            }

            return Quest(
                ownerId = orderId,
                stuff = stuff,
                deadline = deadline,
                state = QuestState.PUBLISHING,
                price = price,
                acceptorId = null
            )
        }

        fun of(row: ResultRow) : Quest = Quest(
            id = row[QuestTable.id].value,
            ownerId = row[QuestTable.owner].value,
            acceptorId = row[QuestTable.acceptor]?.value,
            price = row[QuestTable.price],
            state = row[QuestTable.state],
            stuff = row[QuestTable.stuff],
            deadline = row[QuestTable.deadline]
        )
    }
}

data class QuestOwner(
    val id: Int,
    val owner: Owner,
    val stuff: String,
    val acceptorId: Int?,
    val deadline: LocalDateTime,
    val price: Long,
    val state: QuestState
) {

    data class Owner(
        val id: Int,
        val name: String,
        val sex: Sex,
        val room: Int
    )

    fun toQuest() : Quest = Quest(
        id = id,
        ownerId = owner.id,
        acceptorId = acceptorId,
        stuff = stuff,
        deadline = deadline,
        price = price,
        state = state
    )

    companion object {
        fun of(row: ResultRow) : QuestOwner = QuestOwner(
            id = row[QuestTable.id].value,
            owner = Owner(
                id = row[StudentTable.id].value,
                name = row[StudentTable.name],
                room = row[StudentTable.room].value,
                sex = row[StudentTable.sex]
            ),
            stuff = row[QuestTable.stuff],
            acceptorId = row[QuestTable.acceptor]?.value,
            deadline = row[QuestTable.deadline],
            price = row[QuestTable.price],
            state = row[QuestTable.state]
        )
    }
}