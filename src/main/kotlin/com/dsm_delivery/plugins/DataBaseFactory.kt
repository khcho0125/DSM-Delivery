package com.dsm_delivery.plugins

import com.dsm_delivery.persistence.entity.MissionTable
import com.dsm_delivery.persistence.entity.StudentTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

/**
 *
 * 데이터베이스 연결을 담당하는 DataBaseFactory
 *
 * @author Chokyunghyeon
 * @date 2023/03/16
 **/
object DataBaseFactory {

    private const val DB_PREFIX = "database"
    private const val DB_URL = "$DB_PREFIX.url"
    private const val DB_DRIVER = "$DB_PREFIX.driver"
    private const val DB_USER = "$DB_PREFIX.user"
    private const val DB_PASSWD = "$DB_PREFIX.password"

    operator fun invoke(config: ApplicationConfig) {
        val database = Database.connect(
            HikariDataSource(HikariConfig().apply {
                driverClassName = config.property(DB_DRIVER).getString()
                jdbcUrl = config.property(DB_URL).getString()
                username = config.property(DB_USER).getString()
                password = config.property(DB_PASSWD).getString()
            })
        )

        val tables: Array<Table> = arrayOf(
            MissionTable,
            StudentTable
        )

        transaction(database) {
            addLogger(StdOutSqlLogger)
            tables.run(SchemaUtils::create)
        }
    }

    suspend fun <T> dbQuery(database: Database? = null, block: () -> T) : T = newSuspendedTransaction(
        db = database, context = Dispatchers.IO
    ) {
        addLogger(StdOutSqlLogger)
        block()
    }

}