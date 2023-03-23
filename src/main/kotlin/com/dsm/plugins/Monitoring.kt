package com.dsm.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.request.path
import org.slf4j.event.Level

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
