package com.dsm.plugins

import com.dsm.plugins.database.ExposedDataBaseConnector.connectExposed
import com.dsm.plugins.database.RedisDatabaseConnector.connectRedis
import com.dsm.plugins.database.RedisDatabaseConnector.disconnectRedis
import io.ktor.server.application.Application


/**
 *
 * 이벤트를 등록하는 ApplicationEvents
 *
 * @author Chokyunghyeon
 * @date 2023/04/10
 **/
fun Application.registerEvent() {
    environment.monitor.run {
        connectExposed()
        connectRedis()
        disconnectRedis()
    }
}