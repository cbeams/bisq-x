plugins {
    id("io.micronaut.minimal.library") version "4.3.8"
    id("com.google.protobuf") version "0.9.4"
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core:logging"))
    implementation("ch.qos.logback:logback-classic:1.5.3")

    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("jakarta.validation:jakarta.validation-api")

    implementation("com.google.protobuf:protobuf-java:3.25.3")
}

micronaut {
    version.set("4.4.1")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.3"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                java {}
            }
        }
    }
}

sourceSets {
    main {
        proto {
            srcDir("src/main/proto")
        }
        java {
            srcDir("build/generated/source/proto/main/java")
        }
    }
}

tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}