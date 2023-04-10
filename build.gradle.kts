plugins {
    kotlin("jvm") version Version.KOTLIN
    kotlin("plugin.serialization") version Version.KOTLIN
    id("io.ktor.plugin") version Version.KTOR
    id("io.gitlab.arturbosch.detekt") version Version.DETEKT
}

group = "com.dsm_delivery"

version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(Dependency.KTOR_CORE)
    implementation(Dependency.KTOR_NETTY)

    implementation(Dependency.EXPOSED_CORE)
    implementation(Dependency.EXPOSED_DAO)
    implementation(Dependency.EXPOSED_JDBC)
    implementation(Dependency.EXPOSED_TIME)
    implementation(Dependency.MYSQL)
    implementation(Dependency.HIKARI)
    implementation(Dependency.LETTUCE)

    implementation(Dependency.KTOR_NEGOTIATION)
    implementation(Dependency.KTOR_SERIALIZATION)

    implementation(Dependency.KOIN)
    implementation(Dependency.KOIN_LOGGER)

    implementation(Dependency.KTOR_AUTH)
    implementation(Dependency.KTOR_AUTH_JWT)
    implementation(Dependency.KTOR_CORS)
    implementation(Dependency.BCRYPT)

    implementation(Dependency.KTOR_LOGGING)
    implementation(Dependency.LOGBACK)

    implementation(Dependency.STATUS_PAGES)

    implementation(Dependency.KTOR_TEST)
    implementation(Dependency.KOTLIN_TEST)

    detektPlugins(Dependency.DETEKT)
}

detekt {
    toolVersion = Version.DETEKT
    buildUponDefaultConfig = true
    autoCorrect = true
    config.setFrom(files("$rootDir/src/main/resources/detekt-config.yml"))
}