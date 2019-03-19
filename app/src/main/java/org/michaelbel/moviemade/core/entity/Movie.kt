package org.michaelbel.moviemade.core.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Suppress("unused")
data class Movie(
        @Expose @SerializedName("adult") val adult: Boolean = false,
        @Expose @SerializedName("backdrop_path") val backdropPath: String = "",
        @Expose @SerializedName("belongs_to_collection") val belongsToCollection: Collection? = null,
        @Expose @SerializedName("budget") val budget: Int = 0,
        @Expose @SerializedName("genres") val genres: List<Genre> = emptyList(),
        @Expose @SerializedName("homepage") val homepage: String = "",
        @Expose @SerializedName("id") val id: Int = 0,
        @Expose @SerializedName("imdb_id") val imdbId: String = "",
        @Expose @SerializedName("original_language") val originalLanguage: String = "",
        @Expose @SerializedName("original_title") val originalTitle: String = "",
        @Expose @SerializedName("overview") val overview: String = "",
        @Expose @SerializedName("popularity") val popularity: Double = 0.0,
        @Expose @SerializedName("poster_path") val posterPath: String = "",
        @Expose @SerializedName("production_companies") val companies: List<Company> = emptyList(),
        @Expose @SerializedName("production_countries") val countries: List<Country> = emptyList(),
        @Expose @SerializedName("release_date") val releaseDate: String = "",
        @Expose @SerializedName("revenue") val revenue: Int = 0,
        @Expose @SerializedName("runtime") val runtime: Int = 0,
        @Expose @SerializedName("spoken_languages") val languages: List<Language> = emptyList(),
        @Expose @SerializedName("status") val status: String = "",
        @Expose @SerializedName("tagline") val tagline: String = "",
        @Expose @SerializedName("title") val title: String = "",
        @Expose @SerializedName("video") val video: Boolean = false,
        @Expose @SerializedName("vote_average") val voteAverage: Float = 0F,
        @Expose @SerializedName("vote_count") val voteCount: Int = 0,
        @Expose @SerializedName("media_type") val mediaType: String = "",
        @Expose @SerializedName("genre_ids") val genreIds: List<Int> = emptyList(), // Get short info response.
        @Expose @SerializedName("credits") val credits: CreditsResponse? = null // Append to response.
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