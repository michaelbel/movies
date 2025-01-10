package org.michaelbel.movies.feed

import kotlinx.serialization.Serializable

@Serializable
class FeedDestination(
    val requestToken: String? = null,
    val approved: Boolean = false
)