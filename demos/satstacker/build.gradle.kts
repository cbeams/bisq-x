plugins {
    application
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

repositories {
    mavenCentral()
}

application {
    mainClass = "bisq.demo.satstacker.DcaBot"
}

dependencies {
    implementation(project(":clients:java"))
}

tasks.getByName("build").dependsOn("installDist")
