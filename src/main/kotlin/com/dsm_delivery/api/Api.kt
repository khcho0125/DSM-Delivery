package com.dsm_delivery.api

import io.ktor.server.application.*
import io.ktor.server.routing.*

/**
 *
 * Routing을 도와주는 Api
 *
 * @author Chokyunghyeon
 * @date 2023/03/22
 **/
abstract class Api(private val route: Routing.() -> Unit) {
    operator fun invoke(app: Application) = app.routing(route)
}