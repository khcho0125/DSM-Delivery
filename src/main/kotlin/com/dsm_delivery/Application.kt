package com.dsm_delivery


import com.dsm_delivery.plugins.DataBaseFactory
import com.dsm_delivery.plugins.configureHTTP
import com.dsm_delivery.plugins.configureMonitoring
import com.dsm_delivery.plugins.configureNegotiating
import com.dsm_delivery.plugins.configureRouting
import com.dsm_delivery.plugins.configureSecurity
import com.dsm_delivery.plugins.injectModule
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    DataBaseFactory(environment.config)
    injectModule()
    configureNegotiating()
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureRouting()
}
