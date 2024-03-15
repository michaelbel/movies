package plugins

import ktx.implementation
import ktx.ksp
import ktx.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal class AndroidHiltConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("dagger.hilt.android.plugin")
                apply("com.google.devtools.ksp")
            }

            dependencies {
                implementation(libs.findLibrary("google.hilt.android").get())
                ksp(libs.findLibrary("google.hilt.compiler").get())
            }
        }
    }
}