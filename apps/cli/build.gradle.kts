plugins {
    application
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

repositories {
    mavenCentral()
}

application {
    mainClass = "bisq.app.cli.BisqCLI"
}

dependencies {
    implementation(project(":client:oas"))
    implementation("net.sf.jopt-simple:jopt-simple:5.0.4")
}

tasks.getByName("build").dependsOn("installDist")
