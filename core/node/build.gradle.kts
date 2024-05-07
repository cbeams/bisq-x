plugins {
    id("io.micronaut.library") version "4.3.8"
}

repositories {
    mavenCentral()
}

micronaut {
    version.set("4.4.1")
}

dependencies {
    implementation(project(":core:trade"))
    implementation(project(":core:p2p"))
    implementation(project(":core:util"))

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
