package org.michaelbel.movies.persistence.database.entity.mini

data class MovieDbMini(
    val movieList: String,
    val id: Int,
    val title: String,
) {
    companion object {
        val Empty = MovieDbMini(
            movieList = "",
            id = 0,
            title = ""
        )
    }
}