plugins {
    id("io.micronaut.minimal.library") version "4.3.8"
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
    implementation("ch.qos.logback:logback-classic:1.5.3")

    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("jakarta.validation:jakarta.validation-api")
}
