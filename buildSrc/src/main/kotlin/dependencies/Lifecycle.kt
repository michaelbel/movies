package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.ktx.api
import org.michaelbel.moviemade.ktx.implementation
import org.michaelbel.moviemade.ktx.kapt

/**
 * Lifecycle
 *
 * @see <a href="https://d.android.com/jetpack/androidx/releases/lifecycle">Lifecycle</a>
 */

private const val LifecycleVersion = "2.5.1"

private const val LifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:$LifecycleVersion"
private const val LifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:$LifecycleVersion"
private const val LifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$LifecycleVersion"
private const val LifecycleViewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$LifecycleVersion"
private const val LifecycleViewModelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$LifecycleVersion"
private const val LifecycleService = "androidx.lifecycle:lifecycle-service:$LifecycleVersion"
private const val LifecycleProcess = "androidx.lifecycle:lifecycle-process:$LifecycleVersion"

fun DependencyHandler.implementationLifecycleDependencies() {
    implementation(LifecycleCommon)
    implementation(LifecycleRuntime)
    implementation(LifecycleViewModel)
    implementation(LifecycleViewModelSavedState)
    kapt(LifecycleCommon)
}

fun DependencyHandler.apiLifecycleDependencies() {
    api(LifecycleCommon)
    api(LifecycleRuntime)
    api(LifecycleViewModel)
    api(LifecycleViewModelSavedState)
    kapt(LifecycleCommon)
}