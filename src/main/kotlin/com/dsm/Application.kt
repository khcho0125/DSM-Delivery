package com.dsm

import com.dsm.plugins.DataBaseFactory
import com.dsm.plugins.configureHTTP
import com.dsm.plugins.configureHandling
import com.dsm.plugins.configureMonitoring
import com.dsm.plugins.configureNegotiating
import com.dsm.plugins.configureRouting
import com.dsm.plugins.configureSecurity
import com.dsm.plugins.injectModule
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureHandling()
    DataBaseFactory(environment.config)
    injectModule()
    configureNegotiating()
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureRouting()
}
