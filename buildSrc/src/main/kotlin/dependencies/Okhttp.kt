@file:Suppress("SpellCheckingInspection")

package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.ktx.implementation

/**
 * Okhttp
 *
 * @see <a href="https://github.com/square/okhttp">Okhttp</a>
 */

private const val OkhttpVersion = "4.10.0"

private const val OkhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$OkhttpVersion"

fun DependencyHandler.implementationOkhttpDependencies() {
    implementation(OkhttpLoggingInterceptor)
}