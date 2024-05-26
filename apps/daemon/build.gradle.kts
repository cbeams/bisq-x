plugins {
    id("application")
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core:node"))
    implementation(project(":core:logging"))
    implementation("ch.qos.logback:logback-classic:1.5.3")
}

application {
    mainClass = "bisq.app.daemon.BisqDaemon"
}

tasks.getByName("build").dependsOn("installDist")
