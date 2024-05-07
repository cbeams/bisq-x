plugins {
    id("application")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core:node"))
    implementation(project(":core:util"))
    implementation("net.sf.jopt-simple:jopt-simple:5.0.4")
}

application {
    mainClass = "bisq.app.daemon.BisqDaemon"
}

tasks.getByName("build").dependsOn("installDist")
