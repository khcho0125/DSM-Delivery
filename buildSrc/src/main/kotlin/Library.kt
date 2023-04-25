import org.gradle.api.artifacts.dsl.DependencyHandler

enum class Configure(
    val type: String
) {
    IMPLEMENTATION("implementation"),
    TEST_IMPLEMENTATION("testImplementation"),
    KAPT("kapt"),
    RUNTIME_ONLY("runtimeOnly"),
    DETEKT_PLUGINS("detektPlugins")
}

interface Library {

    val dependency: List<Pair<String, Configure>>

    object JVM : Library {
        override val dependency: List<Pair<String, Configure>> = listOf(
            "io.ktor:ktor-server-core-jvm:${Version.KTOR}" to Configure.IMPLEMENTATION,
            "io.ktor:ktor-server-netty-jvm:${Version.KTOR}" to Configure.IMPLEMENTATION,
        )
    }

    object Negotiation : Library {
        override val dependency: List<Pair<String, Configure>> = listOf(
            "io.ktor:ktor-serialization-kotlinx-json-jvm:${Version.KTOR}" to Configure.IMPLEMENTATION,
            "io.ktor:ktor-server-content-negotiation:${Version.KTOR}" to Configure.IMPLEMENTATION,
        )
    }

    object Auth : Library {
        override val dependency: List<Pair<String, Configure>> = listOf(
            "io.ktor:ktor-server-auth-jvm:${Version.KTOR}" to Configure.IMPLEMENTATION,
            "io.ktor:ktor-server-auth-jwt-jvm:${Version.KTOR}" to Configure.IMPLEMENTATION,
            "io.ktor:ktor-server-cors-jvm:${Version.KTOR}" to Configure.IMPLEMENTATION,
            "org.mindrot:jbcrypt:${Version.BCRYPT}" to Configure.IMPLEMENTATION,
        )
    }

    object Logging : Library {
        override val dependency: List<Pair<String, Configure>> = listOf(
            "io.ktor:ktor-server-call-logging-jvm:${Version.KTOR}" to Configure.IMPLEMENTATION,
            "ch.qos.logback:logback-classic:${Version.LOGBACK}" to Configure.IMPLEMENTATION,
        )
    }

    object Test : Library {
        override val dependency: List<Pair<String, Configure>> = listOf(
            "io.ktor:ktor-server-tests-jvm:${Version.KTOR}" to Configure.IMPLEMENTATION,
            "org.jetbrains.kotlin:kotlin-test-junit:${Version.KOTLIN}" to Configure.IMPLEMENTATION,
        )
    }

    object Exposed : Library {
        override val dependency: List<Pair<String, Configure>> = listOf(
            "org.jetbrains.exposed:exposed-core:${Version.EXPOSED}" to Configure.IMPLEMENTATION,
            "org.jetbrains.exposed:exposed-dao:${Version.EXPOSED}" to Configure.IMPLEMENTATION,
            "org.jetbrains.exposed:exposed-jdbc:${Version.EXPOSED}" to Configure.IMPLEMENTATION,
            "org.jetbrains.exposed:exposed-java-time:${Version.EXPOSED}" to Configure.IMPLEMENTATION,
            "com.mysql:mysql-connector-j:${Version.MYSQL}" to Configure.IMPLEMENTATION,
            "com.zaxxer:HikariCP:${Version.HIKARI}" to Configure.IMPLEMENTATION,
        )
    }

    object Lettuce : Library {
        override val dependency: List<Pair<String, Configure>> = listOf(
            "io.lettuce:lettuce-core:${Version.LETTUCE}" to Configure.IMPLEMENTATION
        )
    }

    object Koin : Library {
        override val dependency: List<Pair<String, Configure>> = listOf(
            "io.insert-koin:koin-logger-slf4j:${Version.KOIN}" to Configure.IMPLEMENTATION,
            "io.insert-koin:koin-ktor:${Version.KOIN}" to Configure.IMPLEMENTATION
        )
    }

    object ExceptionHandling : Library {
        override val dependency: List<Pair<String, Configure>> = listOf(
            "io.ktor:ktor-server-status-pages:${Version.KTOR}" to Configure.IMPLEMENTATION
        )
    }

    object Detekt : Library {
        override val dependency: List<Pair<String, Configure>> = listOf(
            "io.gitlab.arturbosch.detekt:detekt-formatting:${Version.DETEKT}" to Configure.DETEKT_PLUGINS
        )
    }
}

fun DependencyHandler.includeLibrary(library: Library) {
    library.dependency.forEach { (dependency: String, configure: Configure) ->
        add(configure.type, dependency)
    }
}