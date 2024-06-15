import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
}

group = "bisq"
version = "2.1.0"

val kotlin_version = "1.9.23"
val coroutines_version = "1.7.3"
val serialization_version = "1.6.1"
val ktor_version = "2.3.6"

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

repositories {
    mavenCentral()
}

kotlin {
    jvm()
  //iosX64()
  //iosArm64()
  //iosSimulatorArm64()
  //js {
  //    browser()
  //    nodejs()
  //}

    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serialization_version")

                api("io.ktor:ktor-client-core:$ktor_version")
                api("io.ktor:ktor-client-serialization:$ktor_version")
                api("io.ktor:ktor-client-content-negotiation:$ktor_version")
                api("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

                api("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
            }
        }

      //commonTest {
      //    dependencies {
      //        implementation(kotlin("test"))
      //        implementation("io.ktor:ktor-client-mock:$ktor_version")
      //    }
      //}

      jvmMain {
          dependencies {
              implementation(kotlin("stdlib-jdk7"))
              implementation("io.ktor:ktor-client-cio-jvm:$ktor_version")
          }
      }

      //jvmTest {
      //    dependencies {
      //        implementation(kotlin("test-junit"))
      //    }
      //}

      //iosMain {
      //    dependencies {
      //        api("io.ktor:ktor-client-ios:$ktor_version")
      //    }
      //}

      //jsMain {
      //    dependencies {
      //        api("io.ktor:ktor-client-js:$ktor_version")
      //    }
      //}

      //all {
      //    languageSettings.apply {
      //        optIn("kotlin.Experimental")
      //    }
      //}
    }
}

//tasks {
//    register("iosTest") {
//        val device = project.findProperty("device")?.toString() ?: "iPhone 8"
//        dependsOn("linkDebugTestIosX64")
//        group = JavaBasePlugin.VERIFICATION_GROUP
//        description = "Execute unit tests on ${device} simulator"
//        doLast {
//            val binary = kotlin.targets.getByName<KotlinNativeTarget>("iosX64").binaries.getTest("DEBUG")
//            exec {
//                commandLine("xcrun", "simctl", "spawn", device, binary.outputFile)
//            }
//        }
//    }
//    register("test") {
//        dependsOn("allTests")
//    }
//}

// work around https://github.com/OpenAPITools/openapi-generator/issues/18904
tasks.register<ReplaceInFilesTask>("dedupeSerializableAnnotation") {
    group = "Custom"
    description = "Replaces occurrences of @Serializable@Serializable with @Serializable in source files " +
            "to work around the bug described at https://github.com/OpenAPITools/openapi-generator/issues/18904"

    sourceDir.set(file("src"))
    targetString.set("@Serializable@Serializable")
    replacementString.set("@Serializable")
}

abstract class ReplaceInFilesTask : DefaultTask() {
    @get:InputDirectory
    abstract val sourceDir: DirectoryProperty

    @get:Input
    abstract val targetString: Property<String>

    @get:Input
    abstract val replacementString: Property<String>

    @TaskAction
    fun replaceStrings() {
        val target = targetString.get()
        val replacement = replacementString.get()
        val sourceDir = sourceDir.get().asFile

        if (sourceDir.exists()) {
            sourceDir.walkTopDown()
                .filter { it.isFile }
                .forEach { file ->
                    val content = file.readText()
                    val newContent = content.replace(target, replacement)
                    if (content != newContent) {
                        file.writeText(newContent)
                        logger.info("Removed any duplicate @Serializable annotation from: ${file.absolutePath}")
                    }
                }
        } else {
            println("Source directory does not exist: $sourceDir")
        }
    }
}

tasks.getByName("compileKotlinJvm").dependsOn("dedupeSerializableAnnotation")
//tasks.getByName("compileKotlinJvm").dependsOn("openApiGenerate")
//tasks.getByName("openApiGenerate").finalizedBy("dedupeSerializableAnnotation")
