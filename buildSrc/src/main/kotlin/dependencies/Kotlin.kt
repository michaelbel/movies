package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.ktx.androidTestApi
import org.michaelbel.moviemade.ktx.api
import org.michaelbel.moviemade.ktx.implementation
import org.michaelbel.moviemade.ktx.testApi

/**
 * Kotlin
 *
 * @see <a href="https://github.com/JetBrains/kotlin/releases">Kotlin</a>
 * @see <a href="https://github.com/Kotlin/kotlinx.coroutines/releases">Coroutines</a>
 * @see <a href="https://github.com/Kotlin/kotlinx.serialization/releases">Serialization</a>
 * @see <a href="https://github.com/Kotlin/kotlinx-datetime/releases">Datetime</a>
 * @see <a href="https://d.android.com/jetpack/androidx/releases/compose-kotlin">Compose to Kotlin Compatibility Map</a>
 */

const val KotlinVersion = "1.7.10"

private const val KotlinCoroutinesVersion = "1.6.4"
private const val KotlinSerializationVersion = "1.4.0"

const val KotlinCompilerExtensionVersion = "1.3.0"

const val KotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KotlinVersion"
const val KotlinSerializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:$KotlinVersion"

private const val KotlinCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$KotlinCoroutinesVersion"
private const val KotlinCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$KotlinCoroutinesVersion"
private const val KotlinCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$KotlinCoroutinesVersion"
private const val KotlinSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:$KotlinSerializationVersion"

const val OptExperimentalCoroutinesApi = "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
const val OptExperimentalSerializationApi = "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi"

fun DependencyHandler.apiKotlinDependencies() {
    api(KotlinCoroutinesCore)
    api(KotlinCoroutinesAndroid)
    testApi(KotlinCoroutinesTest)
    androidTestApi(KotlinCoroutinesTest)
}

fun DependencyHandler.implementationKotlinxSerializationDependencies() {
    implementation(KotlinSerialization)
}