package com.dsm.plugins

import com.dsm.serializer.LocalDateTimeSerializer
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.time.LocalDateTime

/**
 *
 * 클라이언트와 서버 간의 미디어 유형 협상을 담당하는 Negotiating
 *
 * @author Chokyunghyeon
 * @date 2023/03/23
 **/
fun Application.configureNegotiating() {
    val serializers = SerializersModule {
        contextual(LocalDateTime::class, LocalDateTimeSerializer)
    }

    install(ContentNegotiation) {
        json(
            Json {
                serializersModule = serializers
                prettyPrint = true
                isLenient = true
            }
        )
    }
}
