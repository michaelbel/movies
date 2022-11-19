package org.michaelbel.moviemade.plugins

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.michaelbel.moviemade.App.TargetSdk
import org.michaelbel.moviemade.ktx.configureKotlinAndroid
import org.michaelbel.moviemade.ktx.configureLintCheck

internal class AndroidLibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig.targetSdk = TargetSdk
                configureKotlinAndroid(this)
                configureLintCheck(this)
            }
        }
    }
}