plugins {
    application
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

repositories {
    mavenCentral()
}

application {
    mainClass = "bisq.demo.marketmaker.MarketMakerBot"
}

dependencies {
    implementation(project(":client:java"))
}

tasks.getByName("build").dependsOn("installDist")
