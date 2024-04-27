package org.michaelbel.movies.persistence.database.entity

import org.michaelbel.movies.persistence.database.typealiases.MovieId

data class ImagePojo(
    val movieId: MovieId,
    val filePath: String,
    val type: ImageType,
    val width: Int,
    val height: Int,
    val aspectRatio: Float,
    val voteAverage: Float,
    val voteCount: Int,
    val lang: String?,
    val position: Int
) {
    companion object {
        val Empty = ImagePojo(
            movieId = 0,
            filePath = "",
            type = ImageType.BACKDROP,
            width = 0,
            height = 0,
            aspectRatio = 0F,
            voteAverage = 0F,
            voteCount = 0,
            lang = null,
            position = 0
        )
    }
}