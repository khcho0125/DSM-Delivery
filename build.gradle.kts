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
    includeLibrary(Library.JVM)
    includeLibrary(Library.Auth)
    includeLibrary(Library.Negotiation)
    includeLibrary(Library.Logging)
    includeLibrary(Library.Exposed)
    includeLibrary(Library.Lettuce)
    includeLibrary(Library.Koin)
    includeLibrary(Library.ExceptionHandling)
    includeLibrary(Library.Test)
    includeLibrary(Library.Detekt)
}

detekt {
    toolVersion = Version.DETEKT
    buildUponDefaultConfig = true
    autoCorrect = true
    config.setFrom(files("$rootDir/src/main/resources/detekt-config.yml"))
}