package com.dsm.plugins.database

import com.dsm.persistence.entity.AuthenticateStudentTable
import com.dsm.persistence.entity.MissionTable
import com.dsm.persistence.entity.StudentTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.events.Events
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.config.ApplicationConfig
import kotlinx.coroutines.DisposableHandle
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

/**
 *
 * Exposed 데이터베이스 연결을 담당하는 ExposedDataBaseConnector
 *
 * @author Chokyunghyeon
 * @date 2023/03/16
 **/
object ExposedDataBaseConnector {

    lateinit var master: Database

    fun Events.connectExposed() : DisposableHandle = subscribe(ApplicationStarted) { application: Application ->
        runCatching {
            val config: ApplicationConfig = application.environment.config

            master = Database.connect(ExposedDataSource.Master(config)).also { database: Database ->
                val tables: Array<Table> = arrayOf(
                    MissionTable,
                    StudentTable,
                    AuthenticateStudentTable
                )

                transaction(database) {
                    addLogger(StdOutSqlLogger)
                    tables.run(SchemaUtils::create)
                }
            }

        }.onFailure { e: Throwable ->
            e.printStackTrace()
            throw e
        }
    }
}

sealed class ExposedDataSource(
    path: String,
    config: ApplicationConfig
) : HikariDataSource(HikariConfig().apply {
    config.config("${Prefix.DATASOURCE}.$path").run {
        driverClassName = property(Prefix.DRIVER).getString()
        username = property(Prefix.USER).getString()
        jdbcUrl = property(Prefix.URL).getString()
        password = property(Prefix.PASSWD).getString()
    }
}) {

    class Master(config: ApplicationConfig) : ExposedDataSource(MASTER, config)

    private companion object {
        const val MASTER: String = "master"
    }

    private object Prefix {
        const val DATASOURCE: String = "exposed.datasource"
        const val DRIVER: String = "driver"
        const val USER: String = "user"
        const val PASSWD: String = "password"
        const val URL: String = "url"
    }
}