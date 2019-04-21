package org.michaelbel.data

import androidx.room.ColumnInfo
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.michaelbel.data.remote.model.*
import org.michaelbel.data.remote.model.Collection
import java.io.Serializable

//@Entity(tableName = "movies")
data class Movie(
        @Expose @SerializedName("id") @PrimaryKey val id: Int = 0,
        @Expose @SerializedName("imdb_id") @ColumnInfo(name = "imdb_id") val imdbId: String = "",
        @Expose @SerializedName("adult") @ColumnInfo(name = "adult") val adult: Boolean = false,
        @Expose @SerializedName("backdrop_path") @ColumnInfo(name = "backdrop_path") val backdropPath: String,
        @Expose @SerializedName("belongs_to_collection") @Ignore val belongsToCollection: Collection,
        @Expose @SerializedName("budget") val budget: Int = 0,
        @Expose @SerializedName("genres") @Ignore val genres: List<Genre> = emptyList(),
        @Expose @SerializedName("homepage") val homepage: String = "",
        @Expose @SerializedName("original_language") val originalLanguage: String = "",
        @Expose @SerializedName("original_title") val originalTitle: String = "",
        @Expose @SerializedName("overview") val overview: String = "",
        @Expose @SerializedName("popularity") val popularity: Double = 0.0,
        @Expose @SerializedName("poster_path") val posterPath: String = "",
        @Expose @SerializedName("production_companies") @Ignore val companies: List<Company> = emptyList(),
        @Expose @SerializedName("production_countries") @Ignore val countries: List<Country> = emptyList(),
        @Expose @SerializedName("release_date") val releaseDate: String = "",
        @Expose @SerializedName("revenue") val revenue: Int = 0,
        @Expose @SerializedName("runtime") val runtime: Int = 0,
        @Expose @SerializedName("spoken_languages") @Ignore val languages: List<Language> = emptyList(),
        @Expose @SerializedName("status") val status: String = "",
        @Expose @SerializedName("tagline") val tagline: String = "",
        @Expose @SerializedName("title") val title: String = "",
        @Expose @SerializedName("video") val video: Boolean = false,
        @Expose @SerializedName("vote_average") val voteAverage: Float = 0F,
        @Expose @SerializedName("vote_count") val voteCount: Int = 0,
        @Expose @SerializedName("media_type") val mediaType: String = "",

        @Expose @SerializedName("genre_ids") @Ignore val genreIds: List<Int>, // Get short info response.
        @Expose @SerializedName("credits") @Ignore val credits: CreditsResponse // Append to response.
): Serializable {

    companion object {
        const val TV = "tv"
        const val MOVIE = "movie"
        const val CREDITS = "credits"
    }
}