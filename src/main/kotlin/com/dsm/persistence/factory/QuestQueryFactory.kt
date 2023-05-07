package com.dsm.persistence.factory

import com.dsm.persistence.entity.Quest
import com.dsm.persistence.entity.QuestOwner
import com.dsm.persistence.entity.QuestState
import com.dsm.persistence.entity.QuestTable
import com.dsm.persistence.entity.StudentTable
import com.dsm.persistence.repository.QuestRepository
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update

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
        ?.let(Quest::of)

    override suspend fun existsByOwnerId(ownerId: Int): Boolean = QuestTable
        .select { QuestTable.owner eq ownerId }
        .limit(1)
        .empty().not()

    override suspend fun insert(quest: Quest): Int = QuestTable
        .insertAndGetId {
            it[owner] = quest.ownerId
            it[price] = quest.price
            it[state] = quest.state
            it[deadline] = quest.deadline
            it[stuff] = quest.stuff
            it[acceptor] = quest.acceptorId
        }.value

    override suspend fun findAllByStatusWithOwner(state: QuestState): List<QuestOwner> = QuestTable
        .leftJoin(StudentTable)
        .select { QuestTable.state eq state }
        .map(QuestOwner::of)

    override suspend fun update(quest: Quest): Int = QuestTable
        .update({  QuestTable.id eq quest.id }) {
            it[owner] = quest.ownerId
            it[price] = quest.price
            it[state] = quest.state
            it[deadline] = quest.deadline
            it[stuff] = quest.stuff
            it[acceptor] = quest.acceptorId
        }
}