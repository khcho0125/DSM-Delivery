package com.dsm.persistence.factory

import com.dsm.persistence.entity.DormitoryRoomTable
import com.dsm.persistence.repository.DormitoryRoomRepository
import org.jetbrains.exposed.sql.select

/**
 *
 * 기숙사 방에 대한 질의를 요청하는 DormitoryRoomQueryFactory
 *
 * @author Chokyunghyeon
 * @date 2023/05/29
 **/
class DormitoryRoomQueryFactory : DormitoryRoomRepository {

    override suspend fun existsById(id: Int): Boolean = DormitoryRoomTable
        .select { DormitoryRoomTable.id eq id }
        .limit(1)
        .empty().not()
}
