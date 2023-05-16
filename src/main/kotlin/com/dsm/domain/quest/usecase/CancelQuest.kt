package com.dsm.domain.quest.usecase

import com.dsm.exception.QuestException
import com.dsm.persistence.entity.Quest
import com.dsm.persistence.entity.QuestState
import com.dsm.persistence.repository.QuestRepository
import com.dsm.plugins.database.dbQuery

/**
 *
 * 배달 퀘스트를 취소하는 CancelQuest
 *
 * @author Chokyunghyeon
 * @date 2023/05/15
 **/
class CancelQuest(
    private val questRepository: QuestRepository
) {

    suspend operator fun invoke(questId: Int, studentId: Int) : Unit = dbQuery {
        val quest: Quest = questRepository.findById(questId)
            ?: throw QuestException.NotFound()

        if(quest.ownerId != studentId) {
            throw QuestException.UnableAccept()
        }

        if(quest.state != QuestState.PUBLISHING) {
            throw QuestException.DifferentState(QuestState.PUBLISHING)
        }

        questRepository.update(
            quest.cancel()
        )
    }
}