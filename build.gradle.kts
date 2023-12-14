plugins {
    alias(libs.plugins.application) apply false
    alias(libs.plugins.library) apply false
    alias(libs.plugins.dynamic.feature) apply false
    alias(libs.plugins.test) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.firebase.appdistribution) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.google.services) apply false
    //alias(libs.plugins.huawei.services.gradle.plugin) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.androidx.navigation.safeargs) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.detekt)
    alias(libs.plugins.palantir.git)
}

detekt {
    config.setFrom("$projectDir/config/detekt/detekt.yml")
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
}