@file:Suppress("SpellCheckingInspection", "unused")

package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.extensions.api
import org.michaelbel.moviemade.extensions.implementation

/**
 * ConstraintLayout
 *
 * @see <a href="https://developer.android.com/jetpack/androidx/releases/constraintlayout">ConstraintLayout</a>
 */

private const val ConstraintLayoutComposeVersion = "1.0.1"

private const val ConstraintLayoutCompose = "androidx.constraintlayout:constraintlayout-compose:$ConstraintLayoutComposeVersion"

fun DependencyHandler.apiConstraintLayoutDependencies() {
    api(ConstraintLayoutCompose)
}