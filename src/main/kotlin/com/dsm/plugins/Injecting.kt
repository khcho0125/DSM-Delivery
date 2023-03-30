package com.dsm.plugins

import com.dsm.api.Api
import com.dsm.api.StudentApi
import com.dsm.domain.auth.token.JwtGenerator
import com.dsm.domain.auth.token.TokenProvider
import com.dsm.domain.auth.usecase.StudentLogin
import com.dsm.persistence.factory.StudentQueryFactory
import com.dsm.persistence.repository.StudentRepository
import io.ktor.server.application.Application
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
private val auth: List<Module> = listOf(
    module {
        singleOf(::StudentLogin)
        singleOf(::StudentApi) bind Api::class
    },
    module {
        singleOf(::JwtGenerator) bind TokenProvider::class
    }
)

private val factory: List<Module> = listOf(
    module {
        singleOf(::StudentQueryFactory) bind StudentRepository::class
    }
)

fun Application.injectModule() {
    val properties: List<Module> = listOf(
        module {
            single { SecurityProperties(environment.config) }
        }
    )

    stopKoin()
    startKoin {
        modules(
            auth + factory + properties
        )
    }
}
