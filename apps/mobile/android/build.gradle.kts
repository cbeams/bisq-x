plugins {
    kotlin("jvm")
    application
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":clients:kotlin"))
}

application {
    mainClass = "org.example.AppKt"
}
