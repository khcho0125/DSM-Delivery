package com.dsm.domain.quest.usecase

import com.dsm.exception.QuestException
import com.dsm.persistence.entity.Quest
import com.dsm.persistence.repository.QuestRepository
import com.dsm.plugins.database.dbQuery

/**
 *
 * 퀘스트 완료 처리를 담당하는 CompleteQuest
 *
 * @author Chokyunghyeon
 * @date 2023/05/16
 **/
class CompleteQuest(
    private val questRepository: QuestRepository
) {

    suspend operator fun invoke(questId: Int, studentId: Int) : Unit = dbQuery {
        val quest: Quest = questRepository.findById(questId)
            ?: throw QuestException.NotFound()

        questRepository.update(
            quest.complete(studentId)
        )
    }
}