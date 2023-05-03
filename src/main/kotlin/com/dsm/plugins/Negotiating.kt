package com.dsm.plugins

import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import com.fasterxml.jackson.module.kotlin.addDeserializer
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


/**
 *
 * 클라이언트와 서버 간의 미디어 유형 협상을 담당하는 Negotiating
 *
 * @author Chokyunghyeon
 * @date 2023/03/23
 **/
fun Application.configureNegotiating() {
    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)

            setDefaultPrettyPrinter(DefaultPrettyPrinter().apply {
                indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
                indentObjectsWith(DefaultIndenter())
            })

            registerModule(JavaTimeModule().apply {
                addSerializer(LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME))
                addDeserializer(
                    LocalDateTime::class,
                    LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME)
                )
                addSerializer(LocalDateSerializer(DateTimeFormatter.ISO_DATE))
                addDeserializer(
                    LocalDate::class,
                    LocalDateDeserializer(DateTimeFormatter.ISO_DATE)
                )
                addSerializer(LocalTimeSerializer(DateTimeFormatter.ISO_TIME))
                addDeserializer(
                    LocalTime::class,
                    LocalTimeDeserializer(DateTimeFormatter.ISO_TIME)
                )
            })
        }
    }
}
