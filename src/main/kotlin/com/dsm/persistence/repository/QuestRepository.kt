package com.dsm.persistence.repository

import com.dsm.persistence.entity.Quest
import com.dsm.persistence.entity.QuestOwner
import com.dsm.persistence.entity.QuestState

/**
 *
 * 퀘스트에 대한 요청을 관리하는 QuestRepository
 *
 * @author Chokyunghyeon
 * @date 2023/04/26
 **/
interface QuestRepository {

    suspend fun findById(id: Int) : Quest?

    suspend fun existsByOwnerId(ownerId: Int) : Boolean

    suspend fun insert(quest: Quest) : Int

    suspend fun findAllByStatusWithOwner(state: QuestState): List<QuestOwner>

}