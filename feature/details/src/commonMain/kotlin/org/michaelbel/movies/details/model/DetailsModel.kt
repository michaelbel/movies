package org.michaelbel.movies.details.model

import kotlinx.coroutines.Job
import org.michaelbel.movies.common.mvi.model.Model
import org.michaelbel.movies.details.ktx.movie
import org.michaelbel.movies.network.config.ScreenState
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.persistence.database.ktx.traktUrl
import org.michaelbel.movies.persistence.database.ktx.url

data class DetailsModel(
    val networkStatus: NetworkStatus = NetworkStatus.Unavailable,
    val detailsState: ScreenState = ScreenState.Loading,
    val isAuthorized: Boolean = false,
    val isFavorite: Boolean = false,
    val favoriteJob: Job? = null,
    val isDetailsFavoriteFeatureEnabled: Boolean = false,
    val isDetailsGalleryFeatureEnabled: Boolean = false,
    val isDetailsShareFeatureEnabled: Boolean = false,
    val isTrakt: Boolean = false
): Model {

    val isFavoriteJobActive: Boolean
        get() = favoriteJob != null && favoriteJob.isActive

    val movieUrl: String?
        get() = when {
            detailsState !is ScreenState.Content<*> -> null
            isTrakt -> detailsState.movie.traktUrl
            else -> detailsState.movie.url
        }
}
