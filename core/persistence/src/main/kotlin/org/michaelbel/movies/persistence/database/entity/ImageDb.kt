package org.michaelbel.movies.persistence.database.entity

import androidx.room.Entity
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

@Entity(tableName = "images", primaryKeys = ["movieId", "filePath"])
data class ImageDb(
    @NotNull val movieId: Int,
    @NotNull val filePath: String,
    @NotNull val type: Type,
    @NotNull val width: Int,
    @NotNull val height: Int,
    @NotNull val aspectRatio: Float,
    @NotNull val voteAverage: Float,
    @NotNull val voteCount: Int,
    @Nullable val lang: String?,
    @NotNull val position: Int
) {
    companion object {
        val Empty: ImageDb = ImageDb(
            movieId = 0,
            filePath = "",
            type = Type.BACKDROP,
            width = 0,
            height = 0,
            aspectRatio = 0F,
            voteAverage = 0F,
            voteCount = 0,
            lang = null,
            position = 0
        )
    }

    enum class Type {
        BACKDROP,
        POSTER,
        LOGO
    }
}