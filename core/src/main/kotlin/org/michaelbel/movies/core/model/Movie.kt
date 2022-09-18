package org.michaelbel.movies.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("id") val id: Int = 0,
    @SerialName("imdb_id") val imdbId: String?,
    @SerialName("adult") val adult: Boolean?,
    @SerialName("backdrop_path") val backdropPath: String?,
    //@SerialName("belongs_to_collection") val belongsToCollection: Collection?,
    @SerialName("budget") val budget: Int,
    @SerialName("genres") val genres: List<Genre>,
    @SerialName("homepage") val homepage: String?,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    @SerialName("overview") val overview: String?,
    @SerialName("popularity") val popularity: Double,
    @SerialName("poster_path") val posterPath: String?,
    //@SerialName("production_companies") val companies: List<Company>,
    @SerialName("production_countries") val countries: List<Country>,
    @SerialName("release_date") val releaseDate: String?,
    @SerialName("revenue") val revenue: Long,
    @SerialName("runtime") val runtime: Int,
    @SerialName("spoken_languages") val languages: List<Language>,
    @SerialName("status") val status: String,
    @SerialName("tagline") val tagline: String,
    @SerialName("title") val title: String?,
    @SerialName("video") val video: Boolean,
    @SerialName("vote_average") val voteAverage: Float,
    @SerialName("vote_count") val voteCount: Int,
    //@SerialName("media_type") val mediaType: String,
    //@SerialName("genre_ids") val genreIds: List<Int>,
    //@SerialName("credits") val credits: CreditsResponse?
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