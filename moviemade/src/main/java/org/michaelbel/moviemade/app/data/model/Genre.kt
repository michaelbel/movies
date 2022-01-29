package org.michaelbel.moviemade.app.data.model

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
) {

    companion object {
        private const val GENRE_ACTION = 28
        private const val GENRE_ADVENTURE = 12
        private const val GENRE_ANIMATION = 16
        private const val GENRE_COMEDY = 35
        private const val GENRE_CRIME = 80
        private const val GENRE_DOCUMENTARY = 99
        private const val GENRE_DRAMA = 18
        private const val GENRE_FAMILY = 10751
        private const val GENRE_FANTASY = 14
        private const val GENRE_HISTORY = 36
        private const val GENRE_HORROR = 27
        private const val GENRE_MUSIC = 10402
        private const val GENRE_MYSTERY = 9648
        private const val GENRE_ROMANCE = 10749
        private const val GENRE_SCIENCE_FICTION = 878
        private const val GENRE_TV_MOVIE = 10770
        private const val GENRE_THRILLER = 53
        private const val GENRE_WAR = 10752
        private const val GENRE_WESTERN = 37

        private val genres: List<Genre>
            get() = listOf(
                Genre(GENRE_ACTION, "Action"),
                Genre(GENRE_ADVENTURE, "Adventure"),
                Genre(GENRE_ANIMATION, "Animation"),
                Genre(GENRE_COMEDY, "Comedy"),
                Genre(GENRE_CRIME, "Crime"),
                Genre(GENRE_DOCUMENTARY, "Documentary"),
                Genre(GENRE_DRAMA, "Drama"),
                Genre(GENRE_FAMILY, "Family"),
                Genre(GENRE_FANTASY, "Fantasy"),
                Genre(GENRE_HISTORY, "History"),
                Genre(GENRE_HORROR, "Horror"),
                Genre(GENRE_MUSIC, "Music"),
                Genre(GENRE_MYSTERY, "Mystery"),
                Genre(GENRE_ROMANCE, "Romance"),
                Genre(GENRE_SCIENCE_FICTION, "Science Fiction"),
                Genre(GENRE_TV_MOVIE, "TV Movie"),
                Genre(GENRE_THRILLER, "Thriller"),
                Genre(GENRE_WAR, "War"),
                Genre(GENRE_WESTERN, "Western")
            )

        fun getGenreById(genreId: Int): Genre? {
            return genres.find { it.id == genreId }
        }
    }
}