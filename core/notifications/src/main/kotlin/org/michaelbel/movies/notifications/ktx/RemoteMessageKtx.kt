package org.michaelbel.movies.notifications.ktx

import com.google.firebase.messaging.RemoteMessage
import org.michaelbel.movies.common.ktx.orEmpty
import org.michaelbel.movies.notifications.model.MoviesPush

internal val RemoteMessage.mapToMoviesPush: MoviesPush
    get() {
        return MoviesPush(
            notificationId = 0,
            notificationTitle = notification?.title.orEmpty(),
            notificationDescription = notification?.body.orEmpty(),
            notificationImage = notification?.imageUrl.orEmpty(),
            movieId = data["movieId"]?.toIntOrNull() ?: 0
        )
    }