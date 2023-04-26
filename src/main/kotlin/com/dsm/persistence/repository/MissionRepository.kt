package com.dsm.persistence.repository

import com.dsm.persistence.entity.Mission
import java.util.UUID

/**
 *
 * 미션에 대한 요청을 관리하는 MissionRepository
 *
 * @author Chokyunghyeon
 * @date 2023/04/26
 **/
interface MissionRepository {

    suspend fun findById(id: UUID) : Mission?

    suspend fun existsByStudentId(studentId: UUID) : Boolean

    suspend fun insert(mission: Mission) : Mission

}