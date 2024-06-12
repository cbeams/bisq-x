plugins {
    id("io.micronaut.library") version "4.3.8"
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

repositories {
    mavenCentral()
}

micronaut {
    version.set("4.4.1")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core:logging"))

    implementation("io.micronaut:micronaut-http")
    implementation("jakarta.validation:jakarta.validation-api")
    implementation("ch.qos.logback:logback-classic:1.5.3")

    runtimeOnly("io.micronaut:micronaut-http-server-netty")

    // The following are necessary only for the experimental ProxyFilter
    // implementation. If it goes away, these can go away.
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.projectreactor:reactor-core")
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.10.1")
        }
    }
}
