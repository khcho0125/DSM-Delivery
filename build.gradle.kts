plugins {
    kotlin("jvm") version Version.KOTLIN
    id("io.ktor.plugin") version Version.KTOR
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

    implementation(Dependency.KTOR_SERIALIZATION)

    implementation(Dependency.KOIN)
    implementation(Dependency.KOIN_LOGGER)

    implementation(Dependency.KTOR_AUTH)
    implementation(Dependency.KTOR_AUTH_JWT)
    implementation(Dependency.KTOR_CORS)

    implementation(Dependency.KTOR_LOGGING)
    implementation(Dependency.LOGBACK)

    implementation(Dependency.KTOR_TEST)
    implementation(Dependency.KOTLIN_TEST)
}