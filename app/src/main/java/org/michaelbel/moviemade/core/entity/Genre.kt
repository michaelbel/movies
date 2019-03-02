package org.michaelbel.moviemade.core.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
): Serializable {

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
            get() {
                val genres = ArrayList<Genre>()
                genres.add(Genre(GENRE_ACTION, "Action"))
                genres.add(Genre(GENRE_ADVENTURE, "Adventure"))
                genres.add(Genre(GENRE_ANIMATION, "Animation"))
                genres.add(Genre(GENRE_COMEDY, "Comedy"))
                genres.add(Genre(GENRE_CRIME, "Crime"))
                genres.add(Genre(GENRE_DOCUMENTARY, "Documentary"))
                genres.add(Genre(GENRE_DRAMA, "Drama"))
                genres.add(Genre(GENRE_FAMILY, "Family"))
                genres.add(Genre(GENRE_FANTASY, "Fantasy"))
                genres.add(Genre(GENRE_HISTORY, "History"))
                genres.add(Genre(GENRE_HORROR, "Horror"))
                genres.add(Genre(GENRE_MUSIC, "Music"))
                genres.add(Genre(GENRE_MYSTERY, "Mystery"))
                genres.add(Genre(GENRE_ROMANCE, "Romance"))
                genres.add(Genre(GENRE_SCIENCE_FICTION, "Science Fiction"))
                genres.add(Genre(GENRE_TV_MOVIE, "TV Movie"))
                genres.add(Genre(GENRE_THRILLER, "Thriller"))
                genres.add(Genre(GENRE_WAR, "War"))
                genres.add(Genre(GENRE_WESTERN, "Western"))
                return genres
            }

        fun getGenreById(genreId: Int): Genre {
            for (genre: Genre in genres) {
                if (genre.id == genreId) {
                    return genre
                }
            }

            return Genre(-1, "")
        }
    }
}