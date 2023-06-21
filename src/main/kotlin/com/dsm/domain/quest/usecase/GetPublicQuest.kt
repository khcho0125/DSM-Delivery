package com.dsm.domain.quest.usecase

import com.dsm.domain.quest.dto.QuestView
import com.dsm.persistence.entity.QuestState
import com.dsm.persistence.entity.QuestStudent
import com.dsm.persistence.repository.QuestRepository
import com.dsm.plugins.database.dbQuery
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

/**
 *
 * 공개된 퀘스트 불러오기를 담당하는 GetPublicQuest
 *
 * @author Chokyunghyeon
 * @date 2023/05/03
 **/
class GetPublicQuest(
    private val questRepository: QuestRepository
) {

    suspend operator fun invoke(): Response = dbQuery {
        val questStudent: List<QuestStudent> = questRepository
            .findAllByStatesAfterDeadlineWithStudent(
                QuestState.PUBLISHING,
                QuestState.PROCESSING,
                deadline = LocalDateTime.now()
            )

        return@dbQuery questStudent
            .map(QuestView::of)
            .let(::Response)
    }

    @Serializable
    data class Response(
        val quest: List<QuestView>
    )
}
