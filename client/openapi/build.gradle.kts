plugins {
    java
    id("org.openapi.generator") version "7.5.0"
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.swagger:swagger-annotations:1.6.8")
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.google.code.gson:gson:2.9.1")
    implementation("io.gsonfire:gson-fire:1.9.0")
    implementation("javax.ws.rs:jsr311-api:1.1.1")
    implementation("javax.ws.rs:javax.ws.rs-api:2.1.1")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("jakarta.annotation:jakarta.annotation-api:1.3.5")
}

openApiGenerate { // [1]
    groupId = "bisq"
    id = "openapi-java-client"
    generatorName = "java"
    inputSpec = "$rootDir/core/openapi/build/classes/java/main/META-INF/swagger/bisq-openapi-2.1.0.yml"
    outputDir = projectDir.path
    invokerPackage = "bisq.client.openapi"
    apiPackage = "bisq.client.openapi.endpoint"
    modelPackage = "bisq.client.openapi.model"
    apiNameSuffix = "Endpoint"
    generateApiTests = false
    generateModelTests = false
    configOptions.put("hideGenerationTimestamp", "true") // [2]
}

// remove generated sources before re-generating them.
// This ensures that renamed and removed classes get removed
tasks.getByName("openApiGenerate").doFirst {
    delete(
        "api",          // generated openapi.yaml spec
        "src",          // generated client sources
        "docs",         // generated api usage documentation
        "README.md"     // generated api usage documentation
    )
}

// ensure the openapi spec file gets generated before we try to generate classes based on it.
tasks.getByName("openApiGenerate").dependsOn(":core:openapi:compileJava")

// ensure that we have generated our classes before trying to compile them
tasks.getByName("compileJava").dependsOn("openApiGenerate")

// [1]: https://github.com/OpenAPITools/openapi-generator/blob/master/modules/openapi-generator-gradle-plugin/README.adoc#openapigenerate
// [2]: https://github.com/OpenAPITools/openapi-generator/blob/master/docs/generators/java.md#config-options
