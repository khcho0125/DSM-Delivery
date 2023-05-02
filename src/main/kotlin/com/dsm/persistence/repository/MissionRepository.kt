package com.dsm.persistence.repository

import com.dsm.persistence.entity.DeliveryState
import com.dsm.persistence.entity.Mission

/**
 *
 * 미션에 대한 요청을 관리하는 MissionRepository
 *
 * @author Chokyunghyeon
 * @date 2023/04/26
 **/
interface MissionRepository {

    suspend fun findById(id: Int) : Mission?

    suspend fun existsByStudentId(studentId: Int) : Boolean

    suspend fun insert(mission: Mission) : Mission

    suspend fun findAllByStatus(status: DeliveryState): List<Mission>

}