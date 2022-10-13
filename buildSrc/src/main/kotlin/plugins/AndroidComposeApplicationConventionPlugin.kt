package org.michaelbel.moviemade.plugins

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.michaelbel.moviemade.ktx.configureAndroidCompose

class AndroidComposeApplicationConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("com.android.application")
            val extension: ApplicationExtension = extensions.getByType()
            configureAndroidCompose(extension)
        }
    }
}