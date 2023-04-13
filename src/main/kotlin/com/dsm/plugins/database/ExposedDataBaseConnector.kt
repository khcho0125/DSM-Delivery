package com.dsm.plugins.database

import com.dsm.persistence.entity.AuthenticateStudentTable
import com.dsm.persistence.entity.MissionTable
import com.dsm.persistence.entity.StudentTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.events.Events
import io.ktor.server.application.ApplicationStarted
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

    private const val EXPOSED_PREFIX: String = "exposed"
    private const val EXPOSED_URL: String = "$EXPOSED_PREFIX.url"
    private const val EXPOSED_DRIVER: String = "$EXPOSED_PREFIX.driver"
    private const val EXPOSED_USER: String = "$EXPOSED_PREFIX.user"
    private const val EXPOSED_PASSWD: String = "$EXPOSED_PREFIX.password"

    fun Events.connectExposed() : DisposableHandle = subscribe(ApplicationStarted) {
        val config = it.environment.config

        val database = Database.connect(
            HikariDataSource(
                HikariConfig().apply {
                    driverClassName = config.property(EXPOSED_DRIVER).getString()
                    jdbcUrl = config.property(EXPOSED_URL).getString()
                    username = config.property(EXPOSED_USER).getString()
                    password = config.property(EXPOSED_PASSWD).getString()
                }
            )
        )

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
}