plugins {
    application
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

repositories {
    mavenCentral()
}

application {
    mainClass = "bisq.demo.dca.DCABotDemo"
}

dependencies {
}

tasks.getByName("build").dependsOn("installDist")
