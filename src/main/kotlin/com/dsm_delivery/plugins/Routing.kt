package com.dsm_delivery.plugins

import com.dsm_delivery.api.Api
import io.ktor.server.application.*
import org.koin.java.KoinJavaComponent.getKoin

/**
 *
 * API 구성을 담당하는 Routing
 *
 * @author Chokyunghyeon
 * @date 2023/03/16
 **/
fun Application.configureRouting() {
    getKoin().getAll<Api>().toSet().forEach {
        it(this)
    }
}