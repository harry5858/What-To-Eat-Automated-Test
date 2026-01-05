plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("io.appium:java-client:7.6.0")
    testImplementation("org.seleniumhq.selenium:selenium-java:3.141.59")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}