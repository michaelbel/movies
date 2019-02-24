package org.michaelbel.moviemade.core.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie(
        @SerializedName("adult") val adult: Boolean,
        @SerializedName("backdrop_path") val backdropPath: String,
        @SerializedName("belongs_to_collection") val belongsToCollection: Collection?,
        @SerializedName("budget") val budget: Int,
        @SerializedName("genres") val genres: List<Genre>,
        @SerializedName("homepage") val homepage: String,
        @SerializedName("id") val id: Int,
        @SerializedName("imdb_id") val imdbId: String,
        @SerializedName("original_language") val originalLanguage: String,
        @SerializedName("original_title") val originalTitle: String,
        @SerializedName("overview") val overview: String,
        @SerializedName("popularity") val popularity: Double,
        @SerializedName("poster_path") val posterPath: String,
        @SerializedName("production_companies") val companies: List<Company>,
        @SerializedName("production_countries") val countries: List<Country>,
        @SerializedName("release_date") val releaseDate: String,
        @SerializedName("revenue") val revenue: Int,
        @SerializedName("runtime") val runtime: Int,
        @SerializedName("spoken_languages") val languages: List<Language>,
        @SerializedName("status") val status: String,
        @SerializedName("tagline") val tagline: String,
        @SerializedName("title") val title: String,
        @SerializedName("video") val video: Boolean,
        @SerializedName("vote_average") val voteAverage: Float,
        @SerializedName("vote_count") val voteCount: Int,
        @SerializedName("media_type") val mediaType: String,
        @SerializedName("genre_ids") val genreIds: List<Int>, // Get short info response.
        @SerializedName("credits") val credits: CreditsResponse? // Append to response.
): Serializable {

    object MediaType {
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