plugins {
    // This plugin is applied here, once and only once in the build in order
    // to avoid "Kotlin Gradle plugin loaded multiple times" warnings that are
    // caused when the kotlin client library and the android mobile app try to
    // apply this same plugin independently.
    kotlin("jvm") version "1.9.23" apply false
}
