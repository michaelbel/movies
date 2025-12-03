package org.michaelbel.movies.main.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class MainDestination(
    val requestToken: String? = null,
    val approved: Boolean? = null
) : NavKey