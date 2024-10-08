plugins {
    id("org.openjfx.javafxplugin") version "0.1.0"
    application
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

repositories {
    mavenCentral()
}

javafx {
    version = "21"
    modules("javafx.controls")
}

dependencies {
    implementation(project(":core:node"))
    implementation(project(":core:domain:trading"))
    implementation(project(":core:network:p2p"))
    implementation(project(":core:logging"))
    implementation("ch.qos.logback:logback-classic:1.5.3")
}

application {
    mainClass = "bisq.app.desktop.BisqDesktop\$Launcher"
}

// Customize start scripts to ensure JavaFX is loaded from the module
// path. This avoids the "JavaFX runtime components are missing" error
// without having to fully modularize the build.
// See https://edencoding.com/runtime-components-error/
tasks.getByName<CreateStartScripts>("startScripts") {
    defaultJvmOpts = listOf(
        "--module-path", "${rootDir}/apps/desktop/build/install/desktop/lib/",
        "--add-modules", "javafx.controls"
    )
}

tasks.getByName("build").dependsOn("installDist")
