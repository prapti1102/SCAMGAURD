plugins {
    kotlin("android") version "1.8.0" // Use the latest version of Kotlin.
    id("com.android.application") version "7.4.2" // Use the latest Android plugin.
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2") // Android Gradle Plugin
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0") // Kotlin plugin
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
