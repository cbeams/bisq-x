plugins {
    id("io.micronaut.minimal.library") version "4.3.8"
}

repositories {
    mavenCentral()
}

micronaut {
    version.set("4.4.1")
}

dependencies {
    implementation(project(":core:util"))

    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("jakarta.validation:jakarta.validation-api")
}
