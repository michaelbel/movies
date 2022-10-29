package org.michaelbel.moviemade.ktx

import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.implementation(dependency: Provider<MinimalExternalModuleDependency>) {
    "implementation"(dependency)
}

fun DependencyHandlerScope.kapt(dependency: Provider<MinimalExternalModuleDependency>) {
    "kapt"(dependency)
}