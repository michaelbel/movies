plugins {
    alias(libs.plugins.firebase.appdistribution) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.navigation.safeargs) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.detekt)

    id("com.github.ben-manes.versions") version "0.43.0"
    id("nl.littlerobots.version-catalog-update") version "0.7.0"
}

buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.21")
    }
}

/**
 * https://github.com/littlerobots/version-catalog-update-plugin
 * ./gradlew versionCatalogUpdate
 */
versionCatalogUpdate {
    sortByKey.set(false)
    keep {
        keepUnusedVersions.set(true)
        keepUnusedLibraries.set(true)
        keepUnusedPlugins.set(true)
    }
}