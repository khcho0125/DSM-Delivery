object Dependency {

    // jvm
    const val KTOR_CORE: String = "io.ktor:ktor-server-core-jvm:${Version.KTOR}"
    const val KTOR_NETTY: String = "io.ktor:ktor-server-netty-jvm:${Version.KTOR}"

    // negotiation
    const val KTOR_NEGOTIATION: String = "io.ktor:ktor-server-content-negotiation:${Version.KTOR}"
    const val KTOR_SERIALIZATION: String = "io.ktor:ktor-serialization-kotlinx-json:${Version.KTOR}"

    // auth
    const val KTOR_AUTH: String = "io.ktor:ktor-server-auth-jvm:${Version.KTOR}"
    const val KTOR_AUTH_JWT: String = "io.ktor:ktor-server-auth-jwt-jvm:${Version.KTOR}"
    const val KTOR_CORS: String = "io.ktor:ktor-server-cors-jvm:${Version.KTOR}"
    const val BCRYPT: String = "org.mindrot:jbcrypt:${Version.BCRYPT}"

    // logging
    const val KTOR_LOGGING: String ="io.ktor:ktor-server-call-logging-jvm:${Version.KTOR}"
    const val LOGBACK: String = "ch.qos.logback:logback-classic:${Version.LOGBACK}"

    // test
    const val KTOR_TEST: String = "io.ktor:ktor-server-tests-jvm:${Version.KTOR}"
    const val KOTLIN_TEST: String = "org.jetbrains.kotlin:kotlin-test-junit:${Version.KOTLIN}"

    // database
    const val EXPOSED_CORE: String = "org.jetbrains.exposed:exposed-core:${Version.EXPOSED}"
    const val EXPOSED_DAO: String = "org.jetbrains.exposed:exposed-dao:${Version.EXPOSED}"
    const val EXPOSED_JDBC: String = "org.jetbrains.exposed:exposed-jdbc:${Version.EXPOSED}"
    const val EXPOSED_TIME: String = "org.jetbrains.exposed:exposed-java-time:${Version.EXPOSED}"
    const val MYSQL: String = "com.mysql:mysql-connector-j:${Version.MYSQL}"
    const val HIKARI: String = "com.zaxxer:HikariCP:${Version.HIKARI}"

    const val LETTUCE: String = "io.lettuce:lettuce-core:${Version.LETTUCE}"

    // koin
    const val KOIN_LOGGER: String = "io.insert-koin:koin-logger-slf4j:${Version.KOIN}"
    const val KOIN: String = "io.insert-koin:koin-ktor:${Version.KOIN}"

    // exception handling
    const val STATUS_PAGES: String = "io.ktor:ktor-server-status-pages:${Version.KTOR}"

    // ci
    const val DETEKT: String = "io.gitlab.arturbosch.detekt:detekt-formatting:${Version.DETEKT}"
}