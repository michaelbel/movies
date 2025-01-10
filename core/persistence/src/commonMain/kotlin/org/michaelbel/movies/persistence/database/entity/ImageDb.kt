package org.michaelbel.movies.persistence.database.entity

import androidx.room.Entity
import org.michaelbel.movies.persistence.database.entity.pojo.ImageType
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.Position

@Entity(tableName = "images", primaryKeys = ["movieId", "filePath"])
data class ImageDb(
    val movieId: MovieId,
    val filePath: String,
    val type: ImageType,
    val width: Int,
    val height: Int,
    val aspectRatio: Float,
    val voteAverage: Float,
    val voteCount: Int,
    val lang: String?,
    val position: Position
)