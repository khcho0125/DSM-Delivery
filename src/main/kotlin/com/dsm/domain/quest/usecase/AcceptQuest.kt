package com.dsm.domain.quest.usecase

import com.dsm.exception.QuestException
import com.dsm.exception.StudentException
import com.dsm.persistence.entity.Quest
import com.dsm.persistence.entity.QuestState
import com.dsm.persistence.repository.QuestRepository
import com.dsm.persistence.repository.StudentRepository
import com.dsm.plugins.database.dbQuery

/**
 *
 * 퀘스트 수락을 담당하는 AcceptQuest
 *
 * @author Chokyunghyeon
 * @date 2023/05/07
 **/
class AcceptQuest(
    private val questRepository: QuestRepository,
    private val studentRepository: StudentRepository
) {

    suspend operator fun invoke(questId: Int, studentId: Int): Unit = dbQuery {
        val quest: Quest = questRepository.findById(questId)
            ?: throw QuestException.NotFound()

        when {
            studentRepository.existsById(studentId).not() -> throw StudentException.NotFound()
            quest.ownerId == studentId -> throw QuestException.UnableAccept("Quest Owner and Student are the same")
            quest.state != QuestState.PUBLISHING -> throw QuestException.NotPublishing()
        }

        questRepository.insert(
            quest.accept(studentId)
        )
    }
}