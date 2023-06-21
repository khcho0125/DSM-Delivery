package com.dsm.persistence.entity

import com.dsm.exception.DomainException
import com.dsm.exception.QuestException
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
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
    val owner: Column<EntityID<Int>> = reference("owner_id", StudentTable)
    val acceptor: Column<EntityID<Int>?> = reference("acceptor_id", StudentTable).nullable()
    val mission: Column<String> = varchar("mission", Mission.MAX_LENGTH)
    val deadline: Column<LocalDateTime> = datetime("deadline")
    val reward: Column<Int> = integer("reward")
    val state: Column<QuestState> = enumerationByName("state", QuestState.STATE_MAX_LENGTH)
}

enum class QuestState {
    PUBLISHING, PROCESSING, COMPLETED, CANCELED, MISSING;

    internal companion object {
        const val STATE_MAX_LENGTH: Int = 10
    }
}

@JvmInline
value class Mission(val content: String) {
    init {
        require(content.length <= MAX_LENGTH) {
            throw QuestException
                .OutOfLimitLength("Too Much Long Mission (${content.length}/$MAX_LENGTH)")
        }
    }

    internal companion object {
        const val MAX_LENGTH: Int = 50
    }
}

@JvmInline
value class Reward(val value: Int) {
    init {
        require(value >= MIN_LIMIT) {
            throw DomainException.BadRequest("Quest Reward Too Much Low")
        }

        require(value <= MAX_LIMIT) {
            throw DomainException.BadRequest("Quest Reward Too Much Charge")
        }
    }

    private companion object {
        const val MAX_LIMIT: Int = 50_000
        const val MIN_LIMIT: Int = 1_000
    }
}

@JvmInline
value class PublishTime(val value: Long) {
    init {
        require(value >= MIN_LIMIT) {
            throw DomainException.BadRequest("Quest Publish Time Too Much Low")
        }

        require(value <= MAX_LIMIT) {
            throw DomainException.BadRequest("Quest Publish Time Too Much Long")
        }
    }

    private companion object {
        const val MAX_LIMIT: Int = 30
        const val MIN_LIMIT: Int = 5
    }
}

data class Quest(
    val id: Int = 0,
    val ownerId: Int,
    val acceptorId: Int?,
    val mission: Mission,
    val deadline: LocalDateTime,
    val reward: Reward,
    val state: QuestState
) {

    fun accept(studentId: Int): Quest {
        if (state != QuestState.PUBLISHING) {
            throw QuestException.DifferentState(QuestState.PUBLISHING)
        }

        return copy(
            acceptorId = studentId,
            state = QuestState.PROCESSING,
            deadline = LocalDateTime.now().plusMinutes(EXTRA_TIME).withNano(0)
        )
    }

    fun complete(studentId: Int): Quest {
        isOwnerAccept(studentId)

        if (state !in arrayOf(QuestState.MISSING, QuestState.PROCESSING)) {
            throw QuestException.DifferentState()
        }

        return copy(
            state = QuestState.COMPLETED
        )
    }

    fun cancel(studentId: Int): Quest {
        isOwnerAccept(studentId)

        if (state != QuestState.PUBLISHING) {
            throw QuestException.DifferentState(QuestState.PUBLISHING)
        }

        return copy(
            state = QuestState.CANCELED
        )
    }

    fun failure(studentId: Int): Quest {
        isOwnerAccept(studentId)

        if (state != QuestState.PROCESSING) {
            throw QuestException.DifferentState(QuestState.PROCESSING)
        }

        if (deadline.isBefore(LocalDateTime.now())) {
            throw QuestException.NotYetTimeout()
        }

        return copy(
            state = QuestState.MISSING
        )
    }

    private fun isOwnerAccept(studentId: Int) {
        if (ownerId != studentId) {
            throw QuestException.UnableAccept()
        }
    }

    companion object {
        private const val EXTRA_TIME: Long = 10L

        fun publish(orderId: Int, mission: Mission, publishTime: PublishTime, reward: Reward) = Quest(
            ownerId = orderId,
            mission = mission,
            deadline = LocalDateTime.now().plusMinutes(publishTime.value).withNano(0),
            state = QuestState.PUBLISHING,
            reward = reward,
            acceptorId = null
        )
    }
}

data class QuestStudent(
    val id: Int,
    val owner: Owner,
    val mission: Mission,
    val acceptor: Acceptor?,
    val deadline: LocalDateTime,
    val reward: Reward,
    val state: QuestState
) {

    data class Owner(
        val id: Int,
        val number: Int,
        val name: String,
        val sex: Sex,
        val room: Int
    )

    data class Acceptor(
        val id: Int,
        val number: Int,
        val name: String
    )

    fun toQuest() = Quest(
        id = id,
        ownerId = owner.id,
        acceptorId = acceptor?.id,
        mission = mission,
        deadline = deadline,
        reward = reward,
        state = state
    )
}
