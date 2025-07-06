pluginManagement {
    repositories {
        gradlePluginPortal()  // Gradle plugin portal
        google()              // Google's repository for Android-related dependencies
        mavenCentral()        // Maven Central repository for general dependencies
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("gradle/libs.versions.toml"))  // Path to the version catalog file
        }
    }
}

rootProject.name = "WhatsAppSecurity"  // Name of your project

// Include modules, for example, the app module
include(":app")
