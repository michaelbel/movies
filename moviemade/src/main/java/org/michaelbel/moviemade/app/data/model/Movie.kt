package org.michaelbel.moviemade.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("id") val id: Int = 0,
    @SerialName("imdb_id") val imdbId: String? = null,
    @SerialName("adult") val adult: Boolean?,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("belongs_to_collection") val belongsToCollection: Collection = Collection(),
    @SerialName("budget") val budget: Int = 0,
    @SerialName("genres") val genres: List<Genre> = emptyList(),
    @SerialName("homepage") val homepage: String? = null,
    @SerialName("original_language") val originalLanguage: String = "",
    @SerialName("original_title") val originalTitle: String = "",
    @SerialName("overview") val overview: String? = null,
    @SerialName("popularity") val popularity: Double = 0.0,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("production_companies") val companies: List<Company> = emptyList(),
    @SerialName("production_countries") val countries: List<Country> = emptyList(),
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("revenue") val revenue: Long = 0L,
    @SerialName("runtime") val runtime: Int = 0,
    @SerialName("spoken_languages") val languages: List<Language> = emptyList(),
    @SerialName("status") val status: String = "",
    @SerialName("tagline") val tagline: String = "",
    @SerialName("title") val title: String? = null,
    @SerialName("video") val video: Boolean = false,
    @SerialName("vote_average") val voteAverage: Float = 0F,
    @SerialName("vote_count") val voteCount: Int = 0,
    @SerialName("media_type") val mediaType: String = "",
    @SerialName("genre_ids") val genreIds: List<Int> = emptyList(),
    @SerialName("credits") val credits: CreditsResponse? = null
) {

    companion object {
        const val TV = "tv"
        const val MOVIE = "movie"
        const val CREDITS = "credits"

        const val ASC = "created_at.asc"
        const val DESC = "created_at.desc"

        const val NOW_PLAYING = "now_playing"
        const val POPULAR = "popular"
        const val TOP_RATED = "top_rated"
        const val UPCOMING = "upcoming"

        const val SIMILAR = "similar"
        const val RECOMMENDATIONS = "recommendations"

        const val FAVORITE = "favorite"
        const val WATCHLIST = "watchlist"
    }
}