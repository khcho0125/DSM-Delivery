package com.dsm_delivery.plugins

import io.ktor.server.plugins.callloging.*
import org.slf4j.event.*
import io.ktor.server.request.*
import io.ktor.server.application.*

/**
 *
 * 모니터링을 담당하는 Monitoring
 *
 * @author Chokyunghyeon
 * @date 2023/03/16
 **/
fun Application.configureMonitoring() {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
}
