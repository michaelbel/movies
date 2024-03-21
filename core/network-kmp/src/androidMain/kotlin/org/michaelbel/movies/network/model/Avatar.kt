package org.michaelbel.movies.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Avatar(
    @SerialName("gravatar") val grAvatar: GrAvatar?,
    @SerialName("tmdb") val tmdbAvatar: TmdbAvatar?
)