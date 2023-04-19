package com.dsm.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 *
 * LocalDateTime 타입의 직렬화를 담당하는 LocalDateTimeSerializer
 *
 * @author Chokyunghyeon
 * @date 2023/04/04
 **/
object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(LocalDateTime::class.simpleName!!, PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDateTime = 
        LocalDateTime.parse(decoder.decodeString(), formatter)
    
    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        val formatted: String = value.format(formatter)
        encoder.encodeString(formatted)
    }
}