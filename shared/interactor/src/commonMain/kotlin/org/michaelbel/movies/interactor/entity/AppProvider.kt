package org.michaelbel.movies.interactor.entity

import org.michaelbel.movies.common.SealedString

sealed interface AppProvider: SealedString {

    data object Tmdb: AppProvider
    data object Trakt: AppProvider

    companion object {
        val VALUES = listOf(Tmdb, Trakt)

        fun transform(key: String): AppProvider {
            return when (key) {
                "trakt" -> Trakt
                else -> Tmdb
            }
        }

        fun key(provider: AppProvider): String {
            return when (provider) {
                is Tmdb -> "tmdb"
                is Trakt -> "trakt"
            }
        }
    }
}
