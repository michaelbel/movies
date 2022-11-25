package org.michaelbel.moviemade.plugins

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.michaelbel.moviemade.ktx.configureKotlinAndroid
import org.michaelbel.moviemade.ktx.configureLintCheck

internal class AndroidApplicationConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }
            val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
            extensions.configure<ApplicationExtension> {
                defaultConfig.targetSdk = libs.findVersion("target-sdk").get().requiredVersion.toInt()
                configureKotlinAndroid(this)
                configureLintCheck(this)
            }
        }
    }
}