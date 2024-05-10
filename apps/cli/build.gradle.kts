plugins {
    application
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

repositories {
    mavenCentral()
}

application {
    mainClass = "bisq.app.cli.BisqCli"
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
}

tasks.getByName("build").dependsOn("installDist")
