package plugins

import ktx.implementation
import ktx.kapt
import ktx.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal class AndroidHiltConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("dagger.hilt.android.plugin")
                apply("org.jetbrains.kotlin.kapt")
            }

            dependencies {
                implementation(libs.findLibrary("hilt.android").get())
                kapt(libs.findLibrary("hilt.compiler").get())
            }
        }
    }
}