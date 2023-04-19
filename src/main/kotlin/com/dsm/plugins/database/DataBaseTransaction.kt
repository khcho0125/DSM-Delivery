package com.dsm.plugins.database

import com.zaxxer.hikari.util.IsolationLevel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

/**
 *
 * 데이터베이스의 트랜잭션을 담당하는 DataBaseTransaction
 *
 * @author Chokyunghyeon
 * @date 2023/04/10
 **/
suspend fun <R> dbQuery(database: Database? = null, block: suspend (Transaction) -> R) : R = newSuspendedTransaction(
    db = database,
    context = Dispatchers.IO
) {
    addLogger(StdOutSqlLogger)
    block(this)
}

suspend fun <R> dbReadOnlyQuery(
    database: Database? = null, block: suspend (Transaction) -> R
) : R = newSuspendedTransaction(
    db = database,
    context = Dispatchers.IO,
    transactionIsolation = IsolationLevel.TRANSACTION_READ_COMMITTED.levelId
) {
    addLogger(StdOutSqlLogger)
    block(this)
}