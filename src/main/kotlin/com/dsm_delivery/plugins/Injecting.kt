package com.dsm_delivery.plugins

import com.dsm_delivery.api.Api
import com.dsm_delivery.api.StudentApi
import com.dsm_delivery.domain.auth.usecase.StudentLogin
import com.dsm_delivery.domain.auth.token.JwtGenerator
import com.dsm_delivery.domain.auth.token.TokenProvider
import io.ktor.server.application.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 *
 * 모듈 의존성 주입을 담당하는 Injecting
 *
 * @author Chokyunghyeon
 * @date 2023/03/20
 **/
private val auth: Array<Module> = arrayOf(
    module {
        singleOf(::StudentLogin)
        singleOf(::StudentApi) bind Api::class
    },
    module {
        singleOf(::JwtGenerator) bind TokenProvider::class
    }
)

private val domain: Array<Module> = auth

fun Application.injectModule() {
    val properties: Array<Module> = arrayOf(module {
        single { SecurityProperties(environment.config) }
    })

    stopKoin()
    startKoin {
        modules(*properties, *domain)
    }
}