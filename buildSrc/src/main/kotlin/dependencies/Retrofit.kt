package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.ktx.api
import org.michaelbel.moviemade.ktx.implementation

/**
 * Retrofit
 *
 * @see <a href="https://github.com/square/retrofit">Retrofit</a>
 * @see <a href="https://github.com/JakeWharton/retrofit2-kotlinx-serialization-converter">Kotlin Serialization Converter</a>
 */

private const val RetrofitVersion = "2.9.0"
private const val RetrofitConverterSerializationVersion = "0.8.0"

private const val Retrofit = "com.squareup.retrofit2:retrofit:$RetrofitVersion"
private const val RetrofitConverterSerialization = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:$RetrofitConverterSerializationVersion"

fun DependencyHandler.apiRetrofitDependencies() {
    api(Retrofit)
}

fun DependencyHandler.implementationRetrofitConverterSerializationDependencies() {
    implementation(RetrofitConverterSerialization)
}