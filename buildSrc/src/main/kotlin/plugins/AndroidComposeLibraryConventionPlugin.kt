package org.michaelbel.moviemade.plugins

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.michaelbel.moviemade.ktx.configureAndroidCompose

internal class AndroidComposeLibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("com.android.library")
            val extension: LibraryExtension = extensions.getByType()
            configureAndroidCompose(extension)
        }
    }
}