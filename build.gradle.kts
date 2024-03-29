import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java-library")
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.spring") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.7.10"
    id("org.jetbrains.kotlin.plugin.noarg")version "1.7.10"
}

group = "com.oriolsoler"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-actuator:2.6.1")
    implementation("org.springframework.boot:spring-boot-starter-web:2.6.1")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.0")

    // Database
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.postgresql:postgresql:42.3.4")
    implementation("org.flywaydb:flyway-core:8.5.10")

    // Thymeleaf
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:2.7.1")

    //Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")

    implementation("org.apache.opennlp:opennlp-tools:1.9.3")
    implementation("org.apache.opennlp:opennlp-uima:1.9.3")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.1")
    testImplementation("org.testcontainers:junit-jupiter:1.16.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")

    // Kotlin test
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.6.0")
    testImplementation("com.nhaarman:mockito-kotlin:1.6.0")
    testImplementation("org.mockito:mockito-inline:4.2.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

allOpen {
    annotation("com.oriolsoler.costcontroler.AllOpenAnnotation")
}

noArg {
    annotation("com.oriolsoler.costcontroler.NoArgAnnotation")
    invokeInitializers = true
}