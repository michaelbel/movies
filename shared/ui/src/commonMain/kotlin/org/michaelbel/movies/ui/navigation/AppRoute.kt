package org.michaelbel.movies.ui.navigation

import kotlinx.serialization.Serializable
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

const val INTENT_ACTION_SEARCH = "movies_shortcut://search"
const val INTENT_ACTION_SETTINGS = "movies_shortcut://settings"

@Serializable
sealed interface AppRoute

@Serializable
data object AuthDestination: AppRoute

@Serializable
data object AccountDestination: AppRoute

@Serializable
data object SettingsDestination: AppRoute

@Serializable
data object FaveDestination: AppRoute

@Serializable
data object NotifyDestination: AppRoute

@Serializable
data object DebugDestination: AppRoute

@Serializable
data class MainDestination(
    val requestToken: String? = null,
    val approved: Boolean? = null,
    val openSearch: Boolean = false
): AppRoute

@Serializable
data class FeedDestination(
    val mainDestination: MainDestination = MainDestination()
): AppRoute

@Serializable
data class DetailsDestination(
    val movieList: PagingKey?,
    val movieId: MovieId,
    val isTrakt: Boolean = false
): AppRoute

@Serializable
data class GalleryDestination(
    val movieId: MovieId
): AppRoute
