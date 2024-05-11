plugins {
    java
}

// TODO: reduce this to e.g. 17 in actual generated sdk sources
java.toolchain.languageVersion = JavaLanguageVersion.of(21)

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
}
