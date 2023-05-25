package com.dsm.persistence.repository

import com.dsm.persistence.entity.Quest
import com.dsm.persistence.entity.QuestOwner
import com.dsm.persistence.entity.QuestState
import java.time.LocalDateTime

/**
 *
 * 퀘스트에 대한 요청을 관리하는 QuestRepository
 *
 * @author Chokyunghyeon
 * @date 2023/04/26
 **/
interface QuestRepository {

    suspend fun findById(id: Int): Quest?

    suspend fun existsByOwnerId(ownerId: Int): Boolean

    suspend fun insert(quest: Quest): Int

    suspend fun findAllByStateWithOwner(state: QuestState): List<QuestOwner>

    suspend fun findAllByStateWithOwnerAfterDeadline(state: QuestState, deadline: LocalDateTime): List<QuestOwner>

    suspend fun update(quest: Quest): Int

    suspend fun findByIdWithOwner(id: Int): QuestOwner?
}