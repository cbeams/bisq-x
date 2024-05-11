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
    implementation(project(":core:domain:trade"))
    implementation(project(":core:network:http"))
    implementation(project(":core:network:p2p"))
    implementation(project(":core:util:logging"))

    implementation("io.micronaut.openapi:micronaut-openapi-annotations")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut:micronaut-http")

    implementation("jakarta.validation:jakarta.validation-api")
    implementation("net.sf.jopt-simple:jopt-simple:5.0.4")
    implementation("ch.qos.logback:logback-classic:1.5.3")
    runtimeOnly("io.micronaut:micronaut-http-server-netty")
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.10.1")
        }
    }
}
