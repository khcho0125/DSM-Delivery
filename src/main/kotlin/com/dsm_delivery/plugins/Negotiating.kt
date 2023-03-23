package com.dsm_delivery.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

/**
 *
 * 클라이언트와 서버 간의 미디어 유형 협상을 담당하는 Negotiating
 *
 * @author Chokyunghyeon
 * @date 2023/03/23
 **/
fun Application.configureNegotiating() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
}