package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.ktx.api

/**
 * Google Accompanist
 *
 * Accompanist is a group of libraries that aim to supplement Jetpack Compose
 * with features that are commonly required by developers but not yet available.
 *
 * @see <a href="https://google.github.io/accompanist">Overview</a>
 * @see <a href="https://github.com/google/accompanist">Repository</a>
 */

private const val AccompanistVersion = "0.25.1"

private const val AccompanistAppCompat = "com.google.accompanist:accompanist-appcompat-theme:$AccompanistVersion"
private const val AccompanistDrawablePainter = "com.google.accompanist:accompanist-drawablepainter:$AccompanistVersion"
private const val AccompanistInsets = "com.google.accompanist:accompanist-insets:$AccompanistVersion"
private const val AccompanistInsetsUi = "com.google.accompanist:accompanist-insets-ui:$AccompanistVersion"
private const val AccompanistNavigationAnimation = "com.google.accompanist:accompanist-navigation-animation:$AccompanistVersion"
private const val AccompanistSystemUiController = "com.google.accompanist:accompanist-systemuicontroller:$AccompanistVersion"

fun DependencyHandler.apiAccompanistDependencies() {
    api(AccompanistAppCompat)
    api(AccompanistDrawablePainter)
    api(AccompanistInsets)
    api(AccompanistInsetsUi)
    api(AccompanistNavigationAnimation)
    api(AccompanistSystemUiController)
}