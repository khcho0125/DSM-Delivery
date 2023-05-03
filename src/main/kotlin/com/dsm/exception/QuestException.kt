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
}

enum class QuestErrorCode(
    override val sequence: Int,
    override val defaultMessage: String
) : ErrorCode {

    ALREADY_POSTED(1, "The other quest has been posted"),
    OUT_OF_LIMIT_LENGTH(2, "The length is too much long")

    ;

    override val header: String = "QUEST"

}