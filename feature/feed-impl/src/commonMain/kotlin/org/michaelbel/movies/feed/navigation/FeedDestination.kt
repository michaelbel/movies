package org.michaelbel.movies.feed.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class FeedDestination(
    val requestToken: String? = null,
    val approved: Boolean = false
) : NavKey