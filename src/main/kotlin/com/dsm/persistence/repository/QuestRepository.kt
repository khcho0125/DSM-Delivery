package com.dsm.persistence.repository

import com.dsm.persistence.entity.Quest
import com.dsm.persistence.entity.QuestState
import com.dsm.persistence.entity.QuestStudent
import java.time.LocalDateTime

/**
 *
 * 퀘스트에 대한 요청을 관리하는 QuestRepository
 *
 * @author Chokyunghyeon
 * @date 2023/04/26
 **/
interface QuestRepository {

    suspend fun insert(quest: Quest): Int

    suspend fun update(quest: Quest): Int

    suspend fun existsByOwnerIdAndStates(
        ownerId: Int,
        state: QuestState,
        vararg states: QuestState
    ): Boolean

    suspend fun findById(id: Int): Quest?

    suspend fun findByIdWithStudent(id: Int): QuestStudent?

    suspend fun findAllByStateWithStudent(state: QuestState): List<QuestStudent>

    suspend fun findAllByStatesAfterDeadlineWithStudent(
        state: QuestState,
        vararg states: QuestState,
        deadline: LocalDateTime
    ): List<QuestStudent>

    suspend fun findAllByAcceptorIdAndStatesWithStudent(
        acceptorId: Int,
        state: QuestState,
        vararg states: QuestState,
        limit: Int
    ): List<QuestStudent>

    suspend fun findAllByOwnerIdAndStatesWithStudent(
        ownerId: Int,
        state: QuestState,
        vararg states: QuestState,
        limit: Int
    ): List<QuestStudent>
}
