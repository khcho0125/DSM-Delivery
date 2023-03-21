package com.dsm_delivery.api

import io.ktor.server.application.*
import io.ktor.server.routing.*

abstract class Api(private val route: Routing.() -> Unit) {
    operator fun invoke(app: Application) = app.routing(route)
}