plugins {
    application
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

repositories {
    mavenCentral()
}

application {
    mainClass = "bisq.demo.bot.BisqDcaDemo"
}

dependencies {
    implementation(project(":client:oas"))
}

tasks.getByName("build").dependsOn("installDist")
