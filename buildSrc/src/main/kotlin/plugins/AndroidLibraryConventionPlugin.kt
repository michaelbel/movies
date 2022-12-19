package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

internal class AndroidLibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            /*val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
            extensions.configure<LibraryExtension> {
                defaultConfig.targetSdk = libs.findVersion("target-sdk").get().requiredVersion.toInt()
                configureKotlinAndroid(this)
                configureLintCheck(this)
            }*/
        }
    }
}