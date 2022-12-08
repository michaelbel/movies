package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

internal class AndroidComposeLibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.library")
            }

            /*val extension: LibraryExtension = extensions.getByType()
            configureAndroidCompose(extension)*/
        }
    }
}