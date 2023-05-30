package com.dsm.domain.quest.usecase

import com.dsm.domain.quest.dto.QuestView
import com.dsm.persistence.entity.QuestState
import com.dsm.persistence.entity.QuestStudent
import com.dsm.persistence.repository.QuestRepository
import com.dsm.plugins.database.dbQuery

/**
 *
 * 등록된 퀘스트를 불러오는 GetPublishQuest
 *
 * @author Chokyunghyeon
 * @date 2023/05/29
 **/
class GetPublishQuest(
    private val questRepository: QuestRepository
){

    suspend operator fun invoke(studentId: Int): Response = dbQuery {
        val processingQuest: List<QuestStudent> = questRepository
            .findAllByOwnerIdAndStatesWithStudent(studentId, QuestState.PUBLISHING, QuestState.PROCESSING, limit = 1)

        val completedQuest: List<QuestStudent> = questRepository
            .findAllByOwnerIdAndStatesWithStudent(studentId, QuestState.COMPLETED, QuestState.MISSING, limit = 20)

        return@dbQuery Response(
            processingQuest = processingQuest.map(QuestView::of),
            completedQuest = completedQuest.map(QuestView::of)
        )
    }

    data class Response(
        val processingQuest: List<QuestView>,
        val completedQuest: List<QuestView>
    )
}