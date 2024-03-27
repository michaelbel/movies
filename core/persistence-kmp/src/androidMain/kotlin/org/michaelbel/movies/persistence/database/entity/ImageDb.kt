package org.michaelbel.movies.persistence.database.entity

import androidx.room.Entity

@Entity(tableName = "images", primaryKeys = ["movieId", "filePath"])
internal data class ImageDb(
    val movieId: Int,
    val filePath: String,
    val type: ImageType,
    val width: Int,
    val height: Int,
    val aspectRatio: Float,
    val voteAverage: Float,
    val voteCount: Int,
    val lang: String?,
    val position: Int
)