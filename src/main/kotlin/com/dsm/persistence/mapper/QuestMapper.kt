package com.dsm.persistence.mapper

import com.dsm.persistence.entity.Mission
import com.dsm.persistence.entity.Quest
import com.dsm.persistence.entity.QuestStudent
import com.dsm.persistence.entity.QuestTable
import com.dsm.persistence.entity.Reward
import com.dsm.persistence.entity.StudentTable
import org.jetbrains.exposed.sql.Alias
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.alias

/**
 *
 * 퀘스트 애그리거트의 매핑을 담당하는 QuestMapper
 *
 * @author Chokyunghyeon
 * @date 2023/05/29
 **/
object QuestMapper {

    val ownerTable: Alias<StudentTable> = StudentTable.alias("owner")
    val acceptorTable: Alias<StudentTable> = StudentTable.alias("acceptor")

    fun of(row: ResultRow) = Quest(
        id = row[QuestTable.id].value,
        ownerId = row[QuestTable.owner].value,
        acceptorId = row[QuestTable.acceptor]?.value,
        reward = row[QuestTable.reward].let(::Reward),
        state = row[QuestTable.state],
        mission = row[QuestTable.mission].let(::Mission),
        deadline = row[QuestTable.deadline]
    )

    fun withStudent(row: ResultRow) = QuestStudent(
        id = row[QuestTable.id].value,
        owner = QuestStudent.Owner(
            id = row[ownerTable[StudentTable.id]].value,
            name = row[ownerTable[StudentTable.name]],
            room = row[ownerTable[StudentTable.room]].value,
            sex = row[ownerTable[StudentTable.sex]],
            number = row[ownerTable[StudentTable.number]]
        ),
        mission = row[QuestTable.mission].let(::Mission),
        acceptor = row[QuestTable.acceptor]?.let {
            QuestStudent.Acceptor(
                id = row[acceptorTable[StudentTable.id]].value,
                name = row[acceptorTable[StudentTable.name]],
                number = row[acceptorTable[StudentTable.number]]
            )
        },
        deadline = row[QuestTable.deadline],
        reward = row[QuestTable.reward].let(::Reward),
        state = row[QuestTable.state]
    )
}