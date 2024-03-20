@file:Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING",
    "ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_MEMBERS_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING",
    "NON_ACTUAL_MEMBER_DECLARED_IN_EXPECT_NON_FINAL_CLASSIFIER_ACTUALIZATION_WARNING",
    "ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_SUPERTYPES_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING"
)

package org.michaelbel.movies.common.list

import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.common.list.exceptions.InvalidMovieListException

actual sealed class MovieList(
    val name: String
): SealedString {

    data object NowPlaying: MovieList("now_playing")

    data object Popular: MovieList("popular")

    data object TopRated: MovieList("top_rated")

    data object Upcoming: MovieList("upcoming")

    companion object {
        val VALUES = listOf(
            NowPlaying,
            Popular,
            TopRated,
            Upcoming
        )

        fun transform(name: String): MovieList {
            return when (name) {
                NowPlaying.toString() -> NowPlaying
                Popular.toString() -> Popular
                TopRated.toString() -> TopRated
                Upcoming.toString() -> Upcoming
                else -> throw InvalidMovieListException
            }
        }
    }
}