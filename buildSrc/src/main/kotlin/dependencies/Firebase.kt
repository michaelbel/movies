package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.ktx.api
import org.michaelbel.moviemade.ktx.implementation

/**
 * Firebase
 *
 * @see <a href="https://mvnrepository.com/artifact/com.google.firebase/firebase-abt">FirebaseAbt</a>
 * @see <a href="https://mvnrepository.com/artifact/com.google.firebase/firebase-analytics-ktx">Firebase Analytics</a>
 * @see <a href="https://firebase.google.com/docs/app-distribution/android/distribute-gradle">Distribute</a>
 * @see <a href="https://mvnrepository.com/artifact/com.google.firebase/firebase-bom">Firebase BOM</a>
 * @see <a href="https://mvnrepository.com/artifact/com.google.firebase/firebase-core">Firebase Core</a>
 * @see <a href="https://mvnrepository.com/artifact/com.google.firebase/firebase-crashlytics-gradle">Firebase Crashlytics Gradle</a>
 * @see <a href="https://mvnrepository.com/artifact/com.google.firebase/firebase-crashlytics-ktx">Firebase Crashlytics</a>
 * @see <a href="https://mvnrepository.com/artifact/com.google.firebase/firebase-config">Firebase Config</a>
 * @see <a href="https://mvnrepository.com/artifact/com.google.firebase/firebase-messaging">Firebase Messaging</a>
 */

private const val FirebaseAbtVersion = "21.0.2"
private const val FirebaseAnalyticsVersion = "21.1.1"
private const val FirebaseAppDistributionVersion = "3.0.3"
private const val FirebaseCommonVersion = "20.0.0"
private const val FirebaseConfigVersion = "21.1.2"
private const val FirebaseCoreVersion = "21.1.1"
private const val FirebaseCrashlyticsPluginVersion = "2.9.2"
private const val FirebaseCrashlyticsVersion = "18.2.13"

const val FirebaseAppDistributionPlugin = "com.google.firebase:firebase-appdistribution-gradle:$FirebaseAppDistributionVersion"
const val FirebaseCrashlyticsPlugin = "com.google.firebase:firebase-crashlytics-gradle:$FirebaseCrashlyticsPluginVersion"

private const val FirebaseAbt = "com.google.firebase:firebase-abt:$FirebaseAbtVersion"
private const val FirebaseAnalytics = "com.google.firebase:firebase-analytics-ktx:$FirebaseAnalyticsVersion"
private const val FirebaseCommon = "com.google.firebase:firebase-common-ktx:$FirebaseCommonVersion"
private const val FirebaseConfig = "com.google.firebase:firebase-config-ktx:$FirebaseConfigVersion"
private const val FirebaseCore = "com.google.firebase:firebase-core:$FirebaseCoreVersion"
private const val FirebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx:$FirebaseCrashlyticsVersion"

fun DependencyHandler.implementationFirebaseAnalyticsDependencies() {
    implementation(FirebaseAnalytics)
}

fun DependencyHandler.implementationFirebaseCrashlyticsDependencies() {
    implementation(FirebaseCrashlytics)
}

fun DependencyHandler.apiFirebaseDependencies() {
    api(FirebaseAbt)
    api(FirebaseCommon)
    api(FirebaseCore)
    api(FirebaseConfig)
}

object FirebaseAppDistribution {
    const val MobileSdkAppId = "1:33042426453:android:f766db4a18b6b79e9102dc"
    const val ArtifactType = "APK"
    const val Testers = "michaelbel24865@gmail.com"
    const val Groups = "qa"
}