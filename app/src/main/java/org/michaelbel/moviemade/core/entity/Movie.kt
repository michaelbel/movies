package org.michaelbel.moviemade.core.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Suppress("unused")
data class Movie(
        @SerializedName("adult") val adult: Boolean = false,
        @SerializedName("backdrop_path") val backdropPath: String = "",
        @SerializedName("belongs_to_collection") val belongsToCollection: Collection? = null,
        @SerializedName("budget") val budget: Int = 0,
        @SerializedName("genres") val genres: List<Genre> = emptyList(),
        @SerializedName("homepage") val homepage: String = "",
        @SerializedName("id") val id: Int = 0,
        @SerializedName("imdb_id") val imdbId: String = "",
        @SerializedName("original_language") val originalLanguage: String = "",
        @SerializedName("original_title") val originalTitle: String = "",
        @SerializedName("overview") val overview: String = "",
        @SerializedName("popularity") val popularity: Double = 0.0,
        @SerializedName("poster_path") val posterPath: String = "",
        @SerializedName("production_companies") val companies: List<Company> = emptyList(),
        @SerializedName("production_countries") val countries: List<Country> = emptyList(),
        @SerializedName("release_date") val releaseDate: String = "",
        @SerializedName("revenue") val revenue: Int = 0,
        @SerializedName("runtime") val runtime: Int = 0,
        @SerializedName("spoken_languages") val languages: List<Language> = emptyList(),
        @SerializedName("status") val status: String = "",
        @SerializedName("tagline") val tagline: String = "",
        @SerializedName("title") val title: String = "",
        @SerializedName("video") val video: Boolean = false,
        @SerializedName("vote_average") val voteAverage: Float = 0F,
        @SerializedName("vote_count") val voteCount: Int = 0,
        @SerializedName("media_type") val mediaType: String = "",
        @SerializedName("genre_ids") val genreIds: List<Int> = emptyList(), // Get short info response.
        @SerializedName("credits") val credits: CreditsResponse? = null // Append to response.
): Serializable {

    companion object {
        const val TV = "tv"
        const val MOVIE = "movie"
        const val CREDITS = "credits"
    }

     constructor(): this(
         adult = false,
         backdropPath = "",
         belongsToCollection = null,
         budget = 0,
         genres = emptyList<Genre>(),
         homepage = "",
         id = 0,
         imdbId = "",
         originalLanguage = "",
         originalTitle = "",
         overview = "",
         popularity = 0.0,
         posterPath = "",
         companies = emptyList<Company>(),
         countries = emptyList<Country>(),
         releaseDate = "",
         revenue = 0,
         runtime = 0,
         languages = emptyList<Language>(),
         status = "",
         tagline = "",
         title = "",
         video = false,
         voteAverage = 0F,
         voteCount = 0,
         mediaType = "",
         genreIds = emptyList<Int>(),
         credits = null
     )
}