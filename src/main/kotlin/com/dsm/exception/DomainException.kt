package com.dsm.exception

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

/**
 *
 * 예외 처리를 담당하는 DomainException
 *
 * @author Chokyunghyeon
 * @date 2023/03/30
 **/
abstract class DomainException(
    override val message: String?,
    val status: HttpStatusCode
) : Throwable(message) {

    fun toResponse(): ExceptionContainer = ExceptionContainer(
        message = message ?: status.description,
        status = status.value
    )
}

@Serializable
data class ExceptionContainer(
    val message: String,
    val status: Int
)
