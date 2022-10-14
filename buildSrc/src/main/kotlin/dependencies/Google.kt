@file:Suppress("SpellCheckingInspection")

package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.ktx.api
import org.michaelbel.moviemade.ktx.implementation

/**
 * Google Play services, Play Core
 *
 * @see <a href="https://developers.google.com/android/guides/setup">Set up Google Play services</a>
 * @see <a href="https://d.android.com/reference/com/google/android/play/core/release-notes">Play Core</a>
 */

private const val GmsAds = "20.5.0"
private const val GmsBase = "18.0.1"

private const val GoogleServicesPluginVersion = "4.3.10"

private const val PlayCoreVersion = "1.8.1"

private const val Ads = "com.google.android.gms:play-services-ads:$GmsAds"
private const val Base = "com.google.android.gms:play-services-base:$GmsBase"

private const val PlayCore = "com.google.android.play:core-ktx:$PlayCoreVersion"

fun DependencyHandler.implementationGooglePlayServicesBaseDependencies() {
    implementation(Base)
}

fun DependencyHandler.apiGooglePlayServicesAdsDependencies() {
    api(Ads)
}

fun DependencyHandler.apiPlayCoreDependencies() {
    api(PlayCore)
}