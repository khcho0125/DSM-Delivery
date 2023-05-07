package com.dsm.exception

/**
 *
 * 퀘스트에 관한 예외를 발생시키는 QuestException
 *
 * @author Chokyunghyeon
 * @date 2023/04/26
 **/
object QuestException {

    class AlreadyPosted(override val message: String? = null)
        : DomainException.Conflict(message, QuestErrorCode.ALREADY_POSTED)

    class OutOfLimitLength(override val message: String? = null)
        : DomainException.BadRequest(message, QuestErrorCode.OUT_OF_LIMIT_LENGTH)

    class NotFound(override val message: String? = null)
        : DomainException.NotFound(message, QuestErrorCode.NOT_FOUND)

    class NotPublishing(override val message: String? = null)
        : DomainException.Conflict(message, QuestErrorCode.NOT_PUBLISHING)

    class UnableAccept(override val message: String? = null)
        : DomainException.Conflict(message, QuestErrorCode.UNABLE_ACCEPT)
}

enum class QuestErrorCode(
    override val sequence: Int,
    override val defaultMessage: String
) : ErrorCode {

    NOT_FOUND(1, "Quest Not Found"),
    ALREADY_POSTED(2, "The other quest has been posted"),
    OUT_OF_LIMIT_LENGTH(3, "The length is too much long"),
    NOT_PUBLISHING(4, "Not Publishing Quest"),
    UNABLE_ACCEPT(5, "Can't Accepting Quest")

    ;

    override val header: String = "QUEST"

}