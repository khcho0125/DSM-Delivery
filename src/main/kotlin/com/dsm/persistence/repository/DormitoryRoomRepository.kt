package com.dsm.persistence.repository

/**
 *
 * 기숙사 방에 관한 관리하는 DormitoryRoomRepository
 *
 * @author Chokyunghyeon
 * @date 2023/05/03
 **/
interface DormitoryRoomRepository {

    suspend fun existsById(id: Int): Boolean
}