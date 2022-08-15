@file:Suppress("SpellCheckingInspection", "unused")

package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.extensions.androidTestApi
import org.michaelbel.moviemade.extensions.androidTestImplementation
import org.michaelbel.moviemade.extensions.api
import org.michaelbel.moviemade.extensions.implementation
import org.michaelbel.moviemade.extensions.testApi
import org.michaelbel.moviemade.extensions.testImplementation

/**
 * Kotlin
 *
 * @see <a href="https://github.com/JetBrains/kotlin/releases">Kotlin</a>
 * @see <a href="https://github.com/Kotlin/kotlinx.coroutines/releases">Coroutines</a>
 * @see <a href="https://github.com/Kotlin/kotlinx.serialization/releases">Serialization</a>
 * @see <a href="https://github.com/Kotlin/kotlinx-datetime/releases">Datetime</a>
 * @see <a href="https://d.android.com/jetpack/androidx/releases/compose-kotlin">Compose to Kotlin Compatibility Map</a>
 */

const val KotlinVersion = "1.7.0"

private const val KotlinCoroutinesVersion = "1.6.4"
private const val KotlinSerializationVersion = "1.3.3"
private const val KotlinDatetimeVersion = "0.3.3"

const val KotlinCompilerExtensionVersion = "1.2.0"

const val KotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KotlinVersion"
const val KotlinSerializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:$KotlinVersion"

private const val KotlinCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$KotlinCoroutinesVersion"
private const val KotlinCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$KotlinCoroutinesVersion"
private const val KotlinCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$KotlinCoroutinesVersion"
private const val KotlinSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:$KotlinSerializationVersion"
private const val KotlinDatetime = "org.jetbrains.kotlinx:kotlinx-datetime:$KotlinDatetimeVersion"

const val OptExperimentalCoroutinesApi = "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
const val OptExperimentalSerializationApi = "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi"
const val RequiresOptIn = "-Xopt-in=kotlin.RequiresOptIn"
const val OptContextReceivers = "-Xcontext-receivers"

fun DependencyHandler.implementationKotlinDependencies() {
    implementation(KotlinCoroutinesCore)
    implementation(KotlinCoroutinesAndroid)
    implementation(KotlinSerialization)
    testImplementation(KotlinCoroutinesTest)
    androidTestImplementation(KotlinCoroutinesTest)
}

fun DependencyHandler.apiKotlinDependencies() {
    api(KotlinCoroutinesCore)
    api(KotlinCoroutinesAndroid)
    api(KotlinSerialization)
    testApi(KotlinCoroutinesTest)
    androidTestApi(KotlinCoroutinesTest)
}