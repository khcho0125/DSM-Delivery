package com.dsm.exception

/**
 *
 * 기숙사 방에 관한 예외를 발생시키는 DormitoryRoomException
 *
 * @author Chokyunghyeon
 * @date 2023/05/03
 **/
object DormitoryRoomException {

    class NotFound(override val message: String? = null) :
        DomainException.NotFound(message, DormitoryRoom.NOT_FOUND)
}

enum class DormitoryRoom(
    override val sequence: Int,
    override val defaultMessage: String
) : ErrorCode {

    NOT_FOUND(1, "Dormitory Room Not Found")

    ;

    override val header: String = "ROOM"
}
