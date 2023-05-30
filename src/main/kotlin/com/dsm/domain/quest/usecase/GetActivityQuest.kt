package com.dsm.domain.quest.usecase

import com.dsm.domain.quest.dto.QuestView
import com.dsm.persistence.entity.QuestState
import com.dsm.persistence.entity.QuestStudent
import com.dsm.persistence.repository.QuestRepository
import com.dsm.plugins.database.dbQuery
import kotlinx.serialization.Serializable

/**
 *
 * 퀘스트 활동을 불러오는 GetActivityQuest
 *
 * @author Chokyunghyeon
 * @date 2023/05/29
 **/
class GetActivityQuest(
    private val questRepository: QuestRepository
) {

    suspend operator fun invoke(studentId: Int): Response = dbQuery {
        val processingQuest: List<QuestStudent> = questRepository
            .findAllByAcceptorIdAndStatesWithStudent(studentId, QuestState.PROCESSING, QuestState.MISSING, limit = 1)

        val completedQuest: List<QuestStudent> = questRepository
            .findAllByAcceptorIdAndStatesWithStudent(studentId, QuestState.COMPLETED, limit = 20)

        return@dbQuery Response(
            processingQuest = processingQuest.map(QuestView::of),
            completedQuest = completedQuest.map(QuestView::of)
        )
    }

    @Serializable
    data class Response(
        val processingQuest: List<QuestView>,
        val completedQuest: List<QuestView>
    )
}