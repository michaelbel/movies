package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.extensions.androidTestApi
import org.michaelbel.moviemade.extensions.api
import org.michaelbel.moviemade.extensions.debugApi

/**
 * Jetpack Compose
 *
 * @see <a href="https://d.android.com/jetpack/androidx/releases/compose">Compose</a>
 */

private const val ComposeVersion = "1.2.0-beta03"
private const val ComposeCompilerVersion = "1.2.0"

private const val Material3Version = "1.0.0-alpha11"
private const val Material3SamplesVersion = "1.0.0-SNAPSHOT"
private const val MaterialIconsVersion = "1.1.0-SNAPSHOT"

private const val ComposeAnimation = "androidx.compose.animation:animation:$ComposeVersion"
private const val ComposeAnimationCore = "androidx.compose.animation:animation-core:$ComposeVersion"
private const val ComposeCompiler = "androidx.compose.compiler:compiler:$ComposeCompilerVersion"
private const val ComposeFoundation = "androidx.compose.foundation:foundation:$ComposeVersion"
private const val ComposeFoundationLayout = "androidx.compose.foundation:foundation-layout:$ComposeVersion"
private const val ComposeMaterialIconsCore = "androidx.compose.material:material-icons-core-samples:$MaterialIconsVersion"
private const val ComposeMaterialIconsExtended = "androidx.compose.material:material-icons-extended:$ComposeVersion"
private const val ComposeMaterial3 = "androidx.compose.material3:material3:$Material3Version"
private const val ComposeMaterial3Samples = "androidx.compose.material3:material3-samples:$Material3SamplesVersion"
private const val ComposeRuntime = "androidx.compose.runtime:runtime:$ComposeVersion"
private const val ComposeRuntimeLivedata = "androidx.compose.runtime:runtime-livedata:$ComposeVersion"
private const val ComposeUi = "androidx.compose.ui:ui:$ComposeVersion"
private const val ComposeUiGeometry = "androidx.compose.ui:ui-geometry:$ComposeVersion"
private const val ComposeUiGraphics = "androidx.compose.ui:ui-graphics:$ComposeVersion"
private const val ComposeUiText = "androidx.compose.ui:ui-text:$ComposeVersion"
private const val ComposeUiUtil = "androidx.compose.ui:ui-util:$ComposeVersion"
private const val ComposeUiTest = "androidx.compose.ui:ui-test-junit4:$ComposeVersion"
private const val ComposeUiTestManifest = "androidx.compose.ui:ui-test-manifest:$ComposeVersion"
private const val ComposeUiTooling = "androidx.compose.ui:ui-tooling:$ComposeVersion"
private const val ComposeUiViewBinding = "androidx.compose.ui:ui-viewbinding:$ComposeVersion"

const val OptExperimentalAnimationApi = "-Xopt-in=androidx.compose.animation.ExperimentalAnimationApi"
const val OptExperimentalComposeUiApi = "-Xopt-in=androidx.compose.ui.ExperimentalComposeUiApi"
const val OptExperimentalFoundationApi = "-Xopt-in=androidx.compose.foundation.ExperimentalFoundationApi"

fun DependencyHandler.apiComposeDependencies() {
    api(ComposeCompiler)
    api(ComposeFoundation)
    api(ComposeFoundationLayout)
    //api(ComposeMaterialIconsCore)
    //api(ComposeMaterialIconsExtended)
    api(ComposeMaterial3)
    //api(ComposeMaterial3Samples)
    api(ComposeRuntime)
    api(ComposeRuntimeLivedata)
    api(ComposeUi)
    api(ComposeUiViewBinding)
    api(ComposeUiTooling)
}

fun DependencyHandler.apiComposeTestDependencies() {
    androidTestApi(ComposeUiTest)
    androidTestApi(ComposeUiTest)
    debugApi(ComposeUiTestManifest)
    api(ComposeUiTestManifest)
}