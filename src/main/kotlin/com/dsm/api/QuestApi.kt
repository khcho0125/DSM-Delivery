package com.dsm.api

import com.dsm.domain.quest.usecase.AcceptQuest
import com.dsm.domain.quest.usecase.CancelQuest
import com.dsm.domain.quest.usecase.CompleteQuest
import com.dsm.domain.quest.usecase.FailQuest
import com.dsm.domain.quest.usecase.GetActivityQuest
import com.dsm.domain.quest.usecase.GetPublicQuest
import com.dsm.domain.quest.usecase.GetPublishQuest
import com.dsm.domain.quest.usecase.PublishQuest
import com.dsm.exception.DomainException
import com.dsm.plugins.currentUserId
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 *
 * 퀘스트의 API를 관리하는 QuestApi
 *
 * @author Chokyunghyeon
 * @date 2023/04/26
 **/
class QuestApi(
    getPublishQuest: GetPublishQuest,
    getActivityQuest: GetActivityQuest,
    publishQuest: PublishQuest,
    getPublicQuest: GetPublicQuest,
    acceptQuest: AcceptQuest,
    cancelQuest: CancelQuest,
    completeQuest: CompleteQuest,
    failQuest: FailQuest
) : Api({
    route("/quest") {
        get {
            call.respond(
                message = getPublicQuest(),
                status = HttpStatusCode.OK
            )
        }

        authenticate {
            get("/activity") {
                val studentId: Int = call.currentUserId()

                call.respond(
                    message = getActivityQuest(studentId),
                    status = HttpStatusCode.OK
                )
            }

            get("/publish") {
                val studentId: Int = call.currentUserId()

                call.respond(
                    message = getPublishQuest(studentId),
                    status = HttpStatusCode.OK
                )
            }

            post {
                val request: PublishQuest.Request = call.receive()
                val studentId: Int = call.currentUserId()

                call.respond(
                    message = publishQuest(request, studentId),
                    status = HttpStatusCode.Created
                )
            }

            patch("/{quest-id}") {
                val questId: Int = call.parameters["quest-id"]?.toInt()
                    ?: throw DomainException.BadRequest("Require Quest ID")

                val studentId: Int = call.currentUserId()

                acceptQuest(questId, studentId)

                call.response.status(HttpStatusCode.NoContent)
            }

            delete("/{quest-id}") {
                val questId: Int = call.parameters["quest-id"]?.toInt()
                    ?: throw DomainException.BadRequest("Require Quest ID")

                val studentId: Int = call.currentUserId()

                cancelQuest(
                    questId = questId,
                    studentId = studentId
                )

                call.response.status(HttpStatusCode.NoContent)
            }

            patch("/{quest-id}/complete") {
                val questId: Int = call.parameters["quest-id"]?.toInt()
                    ?: throw DomainException.BadRequest("Require Quest ID")

                val studentId: Int = call.currentUserId()

                completeQuest(
                    questId = questId,
                    studentId = studentId
                )

                call.response.status(HttpStatusCode.OK)
            }

            patch("/{quest-id}/failure") {
                val questId: Int = call.parameters["quest-id"]?.toInt()
                    ?: throw DomainException.BadRequest("Require Quest ID")

                val studentId: Int = call.currentUserId()

                failQuest(
                    questId = questId,
                    studentId = studentId
                )

                call.response.status(HttpStatusCode.OK)
            }
        }
    }
}) {
    companion object {
        val module: Module = module {
            singleOf(::GetPublishQuest)
            singleOf(::GetActivityQuest)
            singleOf(::AcceptQuest)
            singleOf(::PublishQuest)
            singleOf(::GetPublicQuest)
            singleOf(::CancelQuest)
            singleOf(::CompleteQuest)
            singleOf(::FailQuest)
            singleOf(::QuestApi) bind Api::class
        }
    }
}
