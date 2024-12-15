@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.notifications.model

import android.net.Uri
import org.michaelbel.movies.persistence.database.typealiases.MovieId

actual data class MoviesPush(
    val notificationId: Int,
    val notificationTitle: String,
    val notificationDescription: String,
    val notificationImage: Uri,
    val movieId: MovieId
)