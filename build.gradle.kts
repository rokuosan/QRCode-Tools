import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    java

    kotlin("jvm") version "1.7.10"
    id("org.jetbrains.compose") version "1.2.1"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "me.konso"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_1_8

application {
    mainClass.set("${group}.qrcodeTools.MainKt")
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    // QR Code
    implementation("com.google.zxing:core:3.5.1")
    implementation("com.google.zxing:javase:3.5.1")

    // Webcam
    implementation("com.github.sarxos:webcam-capture:0.3.12")
    implementation("org.slf4j:slf4j-log4j12:2.0.5")

    // Compose
    implementation(compose.desktop.currentOs)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}