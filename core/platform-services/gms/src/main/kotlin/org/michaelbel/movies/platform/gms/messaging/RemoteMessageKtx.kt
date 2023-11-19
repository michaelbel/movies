package org.michaelbel.movies.platform.gms.messaging

import android.net.Uri
import com.google.firebase.messaging.RemoteMessage
import org.michaelbel.movies.notifications.model.MoviesPush

internal val RemoteMessage.mapToMoviesPush: MoviesPush
    get() {
        return MoviesPush(
            notificationId = 0,
            notificationTitle = notification?.title.orEmpty(),
            notificationDescription = notification?.body.orEmpty(),
            notificationImage = notification?.imageUrl ?: Uri.EMPTY,
            movieId = data["movieId"]?.toIntOrNull() ?: 0
        )
    }