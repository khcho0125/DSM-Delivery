package com.dsm.domain.mission.usecase

import com.dsm.exception.MissionException
import com.dsm.persistence.entity.Mission
import com.dsm.persistence.repository.MissionRepository
import com.dsm.plugins.database.dbQuery
import java.time.LocalDateTime
import java.util.UUID

/**
 *
 * 배달 미션 게시를 담당하는 PostMission
 *
 * @author Chokyunghyeon
 * @date 2023/04/26
 **/
class PostMission(
    private val missionRepository: MissionRepository
) {

    suspend operator fun invoke(request: Request, studentId: UUID) : Unit = dbQuery {
        if (missionRepository.existsByStudentId(studentId)) {
            throw MissionException.AlreadyPosted()
        }

        missionRepository.insert(Mission.doPost(
            studentId = studentId,
            stuff = request.stuff,
            deadline = request.deadline
        ))
    }

    data class Request(
        val stuff: String,
        val deadline: LocalDateTime
    )
}