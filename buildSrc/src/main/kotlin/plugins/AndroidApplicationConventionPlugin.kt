package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

internal class AndroidApplicationConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            /*val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
            extensions.configure<ApplicationExtension> {
                defaultConfig.targetSdk = libs.findVersion("target-sdk").get().requiredVersion.toInt()
                configureKotlinAndroid(this)
                configureLintCheck(this)
            }*/
        }
    }
}