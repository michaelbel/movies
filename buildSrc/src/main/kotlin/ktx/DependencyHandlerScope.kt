package ktx

import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope

internal fun DependencyHandlerScope.implementation(
    dependency: Provider<MinimalExternalModuleDependency>
) {
    "implementation"(dependency)
}

internal fun DependencyHandlerScope.ksp(
    dependency: Provider<MinimalExternalModuleDependency>
) {
    "ksp"(dependency)
}