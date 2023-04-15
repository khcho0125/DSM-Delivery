package com.dsm.plugins

import com.dsm.api.Api
import com.dsm.api.StudentApi
import com.dsm.domain.auth.token.JwtGenerator
import com.dsm.domain.auth.token.TokenProvider
import com.dsm.domain.auth.usecase.RegisterStudent
import com.dsm.domain.auth.usecase.ReissueToken
import com.dsm.domain.auth.usecase.StudentLogin
import com.dsm.persistence.factory.AuthenticateStudentQueryFactory
import com.dsm.persistence.factory.RefreshTokenQueryFactory
import com.dsm.persistence.factory.StudentQueryFactory
import com.dsm.persistence.repository.AuthenticateStudentRepository
import com.dsm.persistence.repository.RefreshTokenRepository
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
fun Application.injectModule() {
    val auth: List<Module> = listOf(
        module {
            singleOf(::StudentLogin)
            singleOf(::RegisterStudent)
            singleOf(::ReissueToken)
            singleOf(::StudentApi) bind Api::class
        },
        module {
            singleOf(::JwtGenerator) bind TokenProvider::class
        }
    )

    val factory: List<Module> = listOf(module {
        singleOf(::StudentQueryFactory) bind StudentRepository::class
        singleOf(::AuthenticateStudentQueryFactory) bind AuthenticateStudentRepository::class
        singleOf(::RefreshTokenQueryFactory) bind RefreshTokenRepository::class
    })

    stopKoin()
    startKoin {
        modules(
            auth + factory
        )
    }
}
