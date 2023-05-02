package com.dsm.api

import com.dsm.domain.mission.usecase.PostMission
import com.dsm.plugins.currentUserId
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import java.util.UUID

/**
 *
 * 배달 미션의 API를 관리하는 MissionApi
 *
 * @author Chokyunghyeon
 * @date 2023/04/26
 **/
class MissionApi(
    postMission: PostMission
) : Api({
    route("/mission") {
        get {

        }

        post {
            val request: PostMission.Request = call.receive()
            val studentId: UUID = call.currentUserId()

            postMission(request, studentId)

            call.response.status(HttpStatusCode.NoContent)
        }

        patch {

        }

        delete {

        }
    }
}) {
    companion object {
        val module: Module = module {
            singleOf(::PostMission)
            singleOf(::MissionApi) bind Api:: class
        }
    }
}
