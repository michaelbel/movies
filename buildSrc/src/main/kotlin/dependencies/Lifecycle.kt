package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.ktx.api

/**
 * Lifecycle
 *
 * @see <a href="https://d.android.com/jetpack/androidx/releases/lifecycle">Lifecycle</a>
 */

private const val LifecycleVersion = "2.6.0-alpha01"

private const val LifecycleRuntimeCompose = "androidx.lifecycle:lifecycle-runtime-compose:$LifecycleVersion"
private const val LifecycleViewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$LifecycleVersion"

const val OptExperimentalLifecycleComposeApi = "-Xopt-in=androidx.lifecycle.compose.ExperimentalLifecycleComposeApi"

fun DependencyHandler.apiLifecycleDependencies() {
    api(LifecycleRuntimeCompose)
    api(LifecycleViewModelCompose)
}