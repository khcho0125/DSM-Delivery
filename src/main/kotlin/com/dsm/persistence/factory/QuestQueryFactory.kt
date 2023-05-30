package com.dsm.persistence.factory

import com.dsm.persistence.entity.Quest
import com.dsm.persistence.entity.QuestState
import com.dsm.persistence.entity.QuestStudent
import com.dsm.persistence.entity.QuestTable
import com.dsm.persistence.entity.StudentTable
import com.dsm.persistence.mapper.QuestMapper
import com.dsm.persistence.repository.QuestRepository
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.leftJoin
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import java.time.LocalDateTime

/**
 *
 * 퀘스트에 대한 질의를 요청하는 QuestQueryFactory
 *
 * @author Chokyunghyeon
 * @date 2023/05/03
 **/
class QuestQueryFactory : QuestRepository {

    override suspend fun findById(id: Int): Quest? = QuestTable
        .select { QuestTable.id eq id }
        .singleOrNull()
        ?.let(QuestMapper::of)

    override suspend fun existsByOwnerIdAndStates(
        ownerId: Int,
        state: QuestState,
        vararg states: QuestState
    ): Boolean = QuestTable
        .select {
            QuestTable.owner eq ownerId and
                    (QuestTable.state inList listOf(state, *states))
        }
        .limit(1)
        .empty().not()

    override suspend fun insert(quest: Quest): Int = QuestTable
        .insertAndGetId {
            it[owner] = quest.ownerId
            it[reward] = quest.reward.value
            it[state] = quest.state
            it[deadline] = quest.deadline
            it[mission] = quest.mission.content
            it[acceptor] = quest.acceptorId
        }.value

    override suspend fun findAllByStateWithStudent(state: QuestState): List<QuestStudent> = QuestTable
        .leftJoin(QuestMapper.ownerTable, { owner }, { QuestMapper.ownerTable[StudentTable.id] })
        .leftJoin(QuestMapper.acceptorTable, { QuestTable.acceptor }, { QuestMapper.acceptorTable[StudentTable.id] })
        .select { QuestTable.state eq state }
        .map(QuestMapper::withStudent)

    override suspend fun findAllByStatesAfterDeadlineWithStudent(
        state: QuestState,
        vararg states: QuestState,
        deadline: LocalDateTime
    ): List<QuestStudent> = QuestTable
        .leftJoin(QuestMapper.ownerTable, { owner }, { QuestMapper.ownerTable[StudentTable.id] })
        .leftJoin(QuestMapper.acceptorTable, { QuestTable.acceptor }, { QuestMapper.acceptorTable[StudentTable.id] })
        .select {
            QuestTable.deadline greater deadline and
                    (QuestTable.state inList listOf(state, *states))
        }
        .map(QuestMapper::withStudent)

    override suspend fun update(quest: Quest): Int = QuestTable
        .update({ QuestTable.id eq quest.id }) {
            it[owner] = quest.ownerId
            it[reward] = quest.reward.value
            it[state] = quest.state
            it[deadline] = quest.deadline
            it[mission] = quest.mission.content
            it[acceptor] = quest.acceptorId
        }

    override suspend fun findByIdWithStudent(id: Int): QuestStudent? = QuestTable
        .leftJoin(QuestMapper.ownerTable, { owner }, { QuestMapper.ownerTable[StudentTable.id] })
        .leftJoin(QuestMapper.acceptorTable, { QuestTable.acceptor }, { QuestMapper.acceptorTable[StudentTable.id] })
        .select { QuestTable.id eq id }
        .singleOrNull()
        ?.let(QuestMapper::withStudent)

    override suspend fun findAllByAcceptorIdAndStatesWithStudent(
        acceptorId: Int,
        state: QuestState,
        vararg states: QuestState,
        limit: Int
    ): List<QuestStudent> = QuestTable
        .leftJoin(QuestMapper.ownerTable, { owner }, { QuestMapper.ownerTable[StudentTable.id] })
        .leftJoin(QuestMapper.acceptorTable, { QuestTable.acceptor }, { QuestMapper.acceptorTable[StudentTable.id] })
        .select {
            QuestTable.acceptor eq acceptorId and
                    (QuestTable.state inList listOf(state, *states))
        }
        .limit(limit)
        .map(QuestMapper::withStudent)

    override suspend fun findAllByOwnerIdAndStatesWithStudent(
        ownerId: Int,
        state: QuestState,
        vararg states: QuestState,
        limit: Int,
    ): List<QuestStudent> = QuestTable
        .leftJoin(QuestMapper.ownerTable, { owner }, { QuestMapper.ownerTable[StudentTable.id] })
        .leftJoin(QuestMapper.acceptorTable, { QuestTable.acceptor }, { QuestMapper.acceptorTable[StudentTable.id] })
        .select {
            QuestTable.owner eq ownerId and
                    (QuestTable.state inList listOf(state, *states))
        }
        .limit(limit)
        .map(QuestMapper::withStudent)
}