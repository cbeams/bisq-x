plugins {
    application
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

repositories {
    mavenCentral()
}

application {
    mainClass = "bisq.demo.bot.DCABotDemo"
}

dependencies {
    implementation(project(":sdks:java"))
}

tasks.getByName("build").dependsOn("installDist")