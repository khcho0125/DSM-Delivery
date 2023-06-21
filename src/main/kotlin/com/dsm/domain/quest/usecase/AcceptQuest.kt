package com.dsm.domain.quest.usecase

import com.dsm.exception.QuestException
import com.dsm.exception.StudentException
import com.dsm.persistence.entity.Quest
import com.dsm.persistence.entity.QuestStudent
import com.dsm.persistence.entity.Student
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
        val questStudent: QuestStudent = questRepository.findByIdWithStudent(questId)
            ?: throw QuestException.NotFound()

        val student: Student = studentRepository.findById(studentId)
            ?: throw StudentException.NotFound()

        when {
            questStudent.owner.sex != student.sex ->
                throw QuestException.UnableAccept("Quest Owner and Student have different sex")

            questStudent.owner.id == student.id ->
                throw QuestException.UnableAccept("Quest Owner and Student are the same")
        }

        val quest: Quest = questStudent
            .toQuest()
            .accept(studentId)

        questRepository.update(quest)
    }
}
