package org.michaelbel.moviemade.plugins

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.michaelbel.moviemade.ktx.configureKotlinAndroid
import org.michaelbel.moviemade.ktx.configureLintCheck

internal class AndroidLibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
            val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
            extensions.configure<LibraryExtension> {
                defaultConfig.targetSdk = libs.findVersion("target-sdk").get().requiredVersion.toInt()
                configureKotlinAndroid(this)
                configureLintCheck(this)
            }
        }
    }
}