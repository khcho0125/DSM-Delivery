package com.dsm.domain.quest.usecase

import com.dsm.exception.QuestException
import com.dsm.persistence.entity.Quest
import com.dsm.persistence.repository.QuestRepository
import com.dsm.plugins.database.dbQuery
import java.time.LocalDateTime

/**
 *
 * 배달 퀘스트 게시를 담당하는 PublishQuest
 *
 * @author Chokyunghyeon
 * @date 2023/04/26
 **/
class PublishQuest(
    private val questRepository: QuestRepository
) {

    suspend operator fun invoke(request: Request, studentId: Int) : Unit = dbQuery {
        if (questRepository.existsByOwnerId(studentId)) {
            throw QuestException.AlreadyPosted()
        }

        questRepository.insert(Quest.publish(
            orderId = studentId,
            stuff = request.stuff,
            deadline = request.deadline,
            price = request.price
        ))
    }

    data class Request(
        val stuff: String,
        val deadline: LocalDateTime,
        val price: Long
    )
}