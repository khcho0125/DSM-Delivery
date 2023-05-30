package com.dsm.domain.quest.dto

import com.dsm.persistence.entity.QuestStudent
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class QuestView(
    val id: Int,
    val mission: String,
    val reward: Int,
    val owner: Owner,
    @Contextual
    val deadline: LocalDateTime,
    val acceptor: Acceptor?
) {
    @Serializable
    data class Owner(
        val id: Int,
        val number: Int,
        val name: String,
        val room: Int
    )

    @Serializable
    data class Acceptor(
        val id: Int,
        val number: Int,
        val name: String,
    )

    companion object {
        fun of(questStudent: QuestStudent) = QuestView(
            id = questStudent.id,
            mission = questStudent.mission.content,
            reward = questStudent.reward.value,
            deadline = questStudent.deadline,
            owner = questStudent.owner.let {
                Owner(
                    id = it.id,
                    number = it.number,
                    name = it.name,
                    room = it.room
                )
            },
            acceptor = questStudent.acceptor?.let {
                Acceptor(
                    id = it.id,
                    number = it.number,
                    name = it.name
                )
            }
        )
    }
}