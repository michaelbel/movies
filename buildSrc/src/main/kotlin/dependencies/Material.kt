package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.extensions.api
import org.michaelbel.moviemade.extensions.implementation

/**
 * Material
 *
 * @see <a href="https://github.com/material-components/material-components-android/releases">Material</a>
 */

private const val MaterialVersion = "1.6.0"
private const val MaterialComposeThemeAdapterVersion = "1.1.3"

private const val Material = "com.google.android.material:material:$MaterialVersion"
private const val MaterialComposeThemeAdapter = "com.google.android.material:compose-theme-adapter:$MaterialComposeThemeAdapterVersion"

const val OptExperimentalMaterialApi = "-Xopt-in=androidx.compose.material.ExperimentalMaterialApi"
const val OptExperimentalMaterial3Api = "-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api"

fun DependencyHandler.implementationMaterialDependencies() {
    implementation(Material)
    implementation(MaterialComposeThemeAdapter)
}

fun DependencyHandler.apiMaterialDependencies() {
    api(Material)
    api(MaterialComposeThemeAdapter)
}