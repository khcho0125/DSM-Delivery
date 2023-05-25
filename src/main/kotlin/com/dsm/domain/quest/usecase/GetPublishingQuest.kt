package com.dsm.domain.quest.usecase

import com.dsm.persistence.entity.QuestOwner
import com.dsm.persistence.entity.QuestState
import com.dsm.persistence.repository.QuestRepository
import com.dsm.plugins.database.dbQuery
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

/**
 *
 * 게시 상태인 퀘스트 불러오기를 담당하는 GetPublishingQuest
 *
 * @author Chokyunghyeon
 * @date 2023/05/03
 **/
class GetPublishingQuest(
    private val questRepository: QuestRepository
) {

    suspend operator fun invoke(): Response = dbQuery {
        val questOwner: List<QuestOwner> = questRepository
            .findAllByStateWithOwnerAfterDeadline(QuestState.PUBLISHING, LocalDateTime.now())

        return@dbQuery questOwner.map {
            QuestView(
                id = it.id,
                stuff = it.stuff,
                price = it.price,
                deadline = it.deadline,
                owner = QuestView.Owner(
                    id = it.owner.id,
                    name = it.owner.name,
                    room = it.owner.room
                )
            )
        }.let(::Response)
    }

    @Serializable
    data class Response(
        val quests: List<QuestView>
    )

    @Serializable
    data class QuestView(
        val id: Int,
        val stuff: String,
        val price: Long,
        val owner: Owner,
        @Contextual
        val deadline: LocalDateTime
    ) {
        @Serializable
        data class Owner(
            val id: Int,
            val name: String,
            val room: Int
        )
    }
}