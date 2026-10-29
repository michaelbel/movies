package org.michaelbel.movies.common.list

import org.michaelbel.movies.common.SealedString

sealed interface TraktList: SealedString {

    data class Trending(
        val name: String = "trending"
    ): TraktList

    data class Popular(
        val name: String = "popular"
    ): TraktList

    data class Anticipated(
        val name: String = "anticipated"
    ): TraktList

    data class Hot(
        val name: String = "hot"
    ): TraktList

    companion object {
        val VALUES = listOf(
            Trending(),
            Popular(),
            Anticipated(),
            Hot()
        )

        fun transform(name: String): TraktList {
            return when (name) {
                Trending().toString() -> Trending()
                Popular().toString() -> Popular()
                Anticipated().toString() -> Anticipated()
                Hot().toString() -> Hot()
                else -> Trending()
            }
        }

        fun name(traktList: TraktList): String {
            return when (traktList) {
                is Trending -> Trending().name
                is Popular -> Popular().name
                is Anticipated -> Anticipated().name
                is Hot -> Hot().name
            }
        }
    }
}
