package com.dsm.domain.quest.usecase

import com.dsm.exception.QuestException
import com.dsm.persistence.entity.Quest
import com.dsm.persistence.repository.QuestRepository
import com.dsm.plugins.database.dbQuery

/**
 *
 * 퀘스트 타임아웃시 퀘스트 실패 처리를 담당하는 FailQuest
 *
 * @author Chokyunghyeon
 * @date 2023/05/16
 **/
class FailQuest(
    private val questRepository: QuestRepository
) {

    suspend operator fun invoke(questId: Int, studentId: Int) : Unit = dbQuery {
        val quest: Quest = questRepository.findById(questId)
            ?: throw QuestException.NotFound()

        questRepository.update(
            quest.failure(studentId)
        )
    }
}