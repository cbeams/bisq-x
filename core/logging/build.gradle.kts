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
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("jakarta.validation:jakarta.validation-api")
    implementation("io.micronaut:micronaut-http")
    implementation("io.micronaut.openapi:micronaut-openapi-annotations")
    implementation("ch.qos.logback:logback-classic:1.5.3")
}
