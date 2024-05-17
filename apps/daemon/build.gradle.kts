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
    implementation("net.sf.jopt-simple:jopt-simple:5.0.4")
}

application {
    mainClass = "bisq.app.daemon.BisqDaemon"
}

tasks.getByName("build").dependsOn("installDist")
