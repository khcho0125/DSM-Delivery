package com.dsm.exception

/**
 *
 * 미션에 관한 예외를 발생시키는 MissionException
 *
 * @author Chokyunghyeon
 * @date 2023/04/26
 **/
object MissionException {

    class AlreadyPosted(override val message: String? = null)
        : DomainException.Conflict(message, MissionErrorCode.ALREADY_POSTED)

}

enum class MissionErrorCode(
    override val sequence: Int,
    override val defaultMessage: String
) : ErrorCode {

    ALREADY_POSTED(1, "The other Mission has been posted")

    ;

    override val header: String = "MISSION"

}