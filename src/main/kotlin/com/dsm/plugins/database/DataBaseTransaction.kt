package com.dsm.plugins.database

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

/**
 *
 * 데이터베이스의 트랜잭션을 담당하는 DataBaseTransaction
 *
 * @author Chokyunghyeon
 * @date 2023/04/10
 **/
suspend fun <R> dbQuery(database: Database? = null, block: suspend () -> R) : R = newSuspendedTransaction(
    db = database,
    context = Dispatchers.IO
) {
    block()
}