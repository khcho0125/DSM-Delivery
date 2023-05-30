package com.dsm.domain.quest.usecase

import com.dsm.exception.QuestException
import com.dsm.persistence.entity.Mission
import com.dsm.persistence.entity.PublishTime
import com.dsm.persistence.entity.Quest
import com.dsm.persistence.entity.QuestState
import com.dsm.persistence.entity.Reward
import com.dsm.persistence.repository.QuestRepository
import com.dsm.plugins.database.dbQuery
import kotlinx.serialization.Serializable

/**
 *
 * 퀘스트 게시를 담당하는 PublishQuest
 *
 * @author Chokyunghyeon
 * @date 2023/04/26
 **/
class PublishQuest(
    private val questRepository: QuestRepository
) {

    suspend operator fun invoke(request: Request, studentId: Int) : Response = dbQuery {
        if (questRepository.existsByOwnerIdAndStates(studentId, QuestState.PUBLISHING, QuestState.PROCESSING)) {
            throw QuestException.AlreadyPublishing()
        }

        val questId: Int = questRepository.insert(Quest.publish(
            orderId = studentId,
            mission = request.mission,
            publishTime = request.publishTime,
            reward = request.reward
        ))

        return@dbQuery Response(questId)
    }

    class Request(
        mission: String,
        publishTime: Long,
        reward: Int
    ) {
        val mission: Mission by lazy { Mission(mission) }
        val publishTime: PublishTime by lazy { PublishTime(publishTime) }
        val reward: Reward by lazy { Reward(reward) }
    }

    @Serializable
    data class Response(
        val questId: Int
    )
}