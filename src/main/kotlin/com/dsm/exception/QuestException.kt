package com.dsm.exception

import com.dsm.persistence.entity.QuestState

/**
 *
 * 퀘스트에 관한 예외를 발생시키는 QuestException
 *
 * @author Chokyunghyeon
 * @date 2023/04/26
 **/
object QuestException {

    class AlreadyPublishing(override val message: String? = null) :
        DomainException.Conflict(message, QuestErrorCode.ALREADY_PUBLISHING)

    class OutOfLimitLength(override val message: String? = null) :
        DomainException.BadRequest(message, QuestErrorCode.OUT_OF_LIMIT_LENGTH)

    class NotFound(override val message: String? = null) :
        DomainException.NotFound(message, QuestErrorCode.NOT_FOUND)

    class DifferentState(
        override val message: String? = null
    ) : DomainException.Conflict(message, QuestErrorCode.DIFFERENT_STATE) {
        constructor(state: QuestState) : this(
            message = "Not ${state.name} Quest State"
        )
    }

    class UnableAccept(override val message: String? = null) :
        DomainException.Conflict(message, QuestErrorCode.UNABLE_ACCEPT)

    class NotYetTimeout(override val message: String? = null) :
        DomainException.Conflict(message, QuestErrorCode.NOT_YET_TIMEOUT)
}

enum class QuestErrorCode(
    override val sequence: Int,
    override val defaultMessage: String
) : ErrorCode {

    NOT_FOUND(1, "Quest Not Found"),
    ALREADY_PUBLISHING(2, "The other quest has been publishing"),
    OUT_OF_LIMIT_LENGTH(3, "The length is too much long"),
    DIFFERENT_STATE(4, "A Different Quest State"),
    UNABLE_ACCEPT(5, "Can't Accepting Quest"),
    NOT_YET_TIMEOUT(7, "Quest Time still left")

    ;

    override val header: String = "QUEST"
}
