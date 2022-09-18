package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.extensions.api

/**
 * Activity
 *
 * @see <a href="https://developer.android.com/jetpack/androidx/releases/activity">Activity</a>
 */

private const val ActivityVersion = "1.5.0"

private const val Activity = "androidx.activity:activity-ktx:$ActivityVersion"
private const val ActivityCompose = "androidx.activity:activity-compose:$ActivityVersion"

fun DependencyHandler.apiActivityDependencies() {
    api(Activity)
    api(ActivityCompose)
}