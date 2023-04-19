package com.dsm.plugins

import com.dsm.api.Api
import io.ktor.server.application.Application
import org.koin.java.KoinJavaComponent.getKoin

/**
 *
 * API 구성을 담당하는 Routing
 *
 * @author Chokyunghyeon
 * @date 2023/03/16
 **/
fun Application.configureRouting() {
    getKoin().getAll<Api>().forEach {
        it(this)
    }
}
