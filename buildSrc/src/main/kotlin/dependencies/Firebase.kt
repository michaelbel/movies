package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.App.VersionName
import org.michaelbel.moviemade.extensions.api
import org.michaelbel.moviemade.extensions.implementation

/**
 * Firebase
 *
 * @see <a href="https://mvnrepository.com/artifact/com.google.firebase/firebase-abt">FirebaseAbt</a>
 * @see <a href="https://mvnrepository.com/artifact/com.google.firebase/firebase-analytics-ktx">Firebase Analytics</a>
 * @see <a href="https://firebase.google.com/docs/app-distribution/android/distribute-gradle">Distribute</a>
 * @see <a href="https://mvnrepository.com/artifact/com.google.firebase/firebase-bom">Firebase BOM</a>
 * @see <a href="https://mvnrepository.com/artifact/com.google.firebase/firebase-core">Firebase Core</a>
 * @see <a href="https://mvnrepository.com/artifact/com.google.firebase/firebase-crashlytics-ktx">Firebase Crashlytics</a>
 * @see <a href="https://mvnrepository.com/artifact/com.google.firebase/firebase-config">Firebase Config</a>
 * @see <a href="https://mvnrepository.com/artifact/com.google.firebase/firebase-messaging">Firebase Messaging</a>
 */

private const val FirebaseAbtVersion = "21.0.1"
private const val FirebaseAnalyticsVersion = "21.0.0"
private const val FirebaseAppDistributionVersion = "3.0.0"
private const val FirebaseBomVersion = "30.3.1"
private const val FirebaseCommonVersion = "20.0.0"
private const val FirebaseConfigVersion = "21.1.0"
private const val FirebaseCoreVersion = "21.0.0"
private const val FirebaseCrashlyticsPluginVersion = "2.8.1"
private const val FirebaseCrashlyticsVersion = "18.2.10"
private const val FirebaseMessagingVersion = "23.0.6"

const val FirebaseAppDistributionPlugin = "com.google.firebase:firebase-appdistribution-gradle:$FirebaseAppDistributionVersion"
const val FirebaseCrashlyticsPlugin = "com.google.firebase:firebase-crashlytics-gradle:$FirebaseCrashlyticsPluginVersion"

private const val FirebaseAbt = "com.google.firebase:firebase-abt:$FirebaseAbtVersion"
private const val FirebaseAnalytics = "com.google.firebase:firebase-analytics-ktx:$FirebaseAnalyticsVersion"
private const val FirebaseBom = "com.google.firebase:firebase-bom:$FirebaseBomVersion"
private const val FirebaseCommon = "com.google.firebase:firebase-common-ktx:$FirebaseCommonVersion"
private const val FirebaseConfig = "com.google.firebase:firebase-config-ktx:$FirebaseConfigVersion"
private const val FirebaseCore = "com.google.firebase:firebase-core:$FirebaseCoreVersion"
private const val FirebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx:$FirebaseCrashlyticsVersion"
private const val FirebaseMessaging = "com.google.firebase:firebase-messaging:$FirebaseMessagingVersion"

fun DependencyHandler.implementationFirebaseDependencies() {
    implementation(FirebaseAbt)
    implementation(FirebaseAnalytics)
    implementation(FirebaseCommon)
    implementation(FirebaseCore)
    implementation(FirebaseCrashlytics)
    implementation(FirebaseConfig)
}

fun DependencyHandler.apiFirebaseDependencies() {
    api(FirebaseAbt)
    api(FirebaseAnalytics)
    api(FirebaseCommon)
    api(FirebaseCore)
    api(FirebaseCrashlytics)
    api(FirebaseConfig)
}

object FirebaseAppDistribution {
    const val MobileSdkAppId = "1:33042426453:android:f766db4a18b6b79e9102dc"
    const val ArtifactType = "APK"
    const val Testers = "michaelbel24865@gmail.com"
    const val Groups = "qa"
    const val ReleaseNotes = "Release $VersionName"
}