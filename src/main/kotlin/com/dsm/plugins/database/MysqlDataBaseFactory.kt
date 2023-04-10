package com.dsm.plugins.database

import com.dsm.persistence.entity.AuthenticateStudentTable
import com.dsm.persistence.entity.MissionTable
import com.dsm.persistence.entity.StudentTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.ApplicationConfig
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

/**
 *
 * Mysql의 동작을 담당하는 MysqlDataBaseFactory
 *
 * @author Chokyunghyeon
 * @date 2023/03/16
 **/
object MysqlDataBaseFactory {

    private const val MYSQL_PREFIX = "mysql"
    private const val MYSQL_URL = "$MYSQL_PREFIX.url"
    private const val MYSQL_DRIVER = "$MYSQL_PREFIX.driver"
    private const val MYSQL_USER = "$MYSQL_PREFIX.user"
    private const val MYSQL_PASSWD = "$MYSQL_PREFIX.password"

    operator fun invoke(config: ApplicationConfig) {
        val database = Database.connect(
            HikariDataSource(
                HikariConfig().apply {
                    driverClassName = config.property(MYSQL_DRIVER).getString()
                    jdbcUrl = config.property(MYSQL_URL).getString()
                    username = config.property(MYSQL_USER).getString()
                    password = config.property(MYSQL_PASSWD).getString()
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