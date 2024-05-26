plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.dynamic.feature) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.cocoapods) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.google.ksp) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.google.firebase.appdistribution) apply false
    alias(libs.plugins.google.firebase.crashlytics) apply false
    alias(libs.plugins.sqldelight) apply false
    alias(libs.plugins.androidx.navigation.safeargs) apply false
    alias(libs.plugins.buildkonfig) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.detekt)
    alias(libs.plugins.palantir.git)
}

detekt {
    config.setFrom("$projectDir/config/detekt/detekt.yml")
}

subprojects {
    if (name != "desktopApp") {
        apply(plugin = "io.gitlab.arturbosch.detekt")
    }
}

extra.apply {
    set("jvmTarget", "11")
}