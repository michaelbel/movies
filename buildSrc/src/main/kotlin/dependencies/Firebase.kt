package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.ktx.api
import org.michaelbel.moviemade.ktx.implementation

/**
 * Firebase
 *
 * @see <a href="https://mvnrepository.com/artifact/com.google.firebase/firebase-analytics-ktx">Firebase Analytics</a>
 * @see <a href="https://firebase.google.com/docs/app-distribution/android/distribute-gradle">Distribute</a>
 * @see <a href="https://mvnrepository.com/artifact/com.google.firebase/firebase-crashlytics-gradle">Firebase Crashlytics Gradle</a>
 * @see <a href="https://mvnrepository.com/artifact/com.google.firebase/firebase-crashlytics-ktx">Firebase Crashlytics</a>
 * @see <a href="https://mvnrepository.com/artifact/com.google.firebase/firebase-config">Firebase Config</a>
 */

private const val FirebaseAnalyticsVersion = "21.1.1"
private const val FirebaseAppDistributionVersion = "3.0.3"
private const val FirebaseConfigVersion = "21.1.2"
private const val FirebaseCrashlyticsPluginVersion = "2.9.2"
private const val FirebaseCrashlyticsVersion = "18.2.13"

private const val FirebaseAnalytics = "com.google.firebase:firebase-analytics-ktx:$FirebaseAnalyticsVersion"
private const val FirebaseConfig = "com.google.firebase:firebase-config-ktx:$FirebaseConfigVersion"
private const val FirebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx:$FirebaseCrashlyticsVersion"

fun DependencyHandler.implementationFirebaseAnalyticsDependencies() {
    implementation(FirebaseAnalytics)
}

fun DependencyHandler.implementationFirebaseCrashlyticsDependencies() {
    implementation(FirebaseCrashlytics)
}

fun DependencyHandler.apiFirebaseRemoteConfigDependencies() {
    api(FirebaseConfig)
}