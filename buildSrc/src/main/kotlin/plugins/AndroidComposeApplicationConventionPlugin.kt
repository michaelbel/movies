package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

internal class AndroidComposeApplicationConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.application")
            }

            /*val extension: ApplicationExtension = extensions.getByType()
            configureAndroidCompose(extension)*/
        }
    }
}