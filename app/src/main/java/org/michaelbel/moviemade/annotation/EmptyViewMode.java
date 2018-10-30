package org.michaelbel.moviemade.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.michaelbel.moviemade.annotation.EmptyViewMode.MODE_NO_CONNECTION;
import static org.michaelbel.moviemade.annotation.EmptyViewMode.MODE_NO_HISTORY;
import static org.michaelbel.moviemade.annotation.EmptyViewMode.MODE_NO_MOVIES;
import static org.michaelbel.moviemade.annotation.EmptyViewMode.MODE_NO_PEOPLE;
import static org.michaelbel.moviemade.annotation.EmptyViewMode.MODE_NO_RESULTS;
import static org.michaelbel.moviemade.annotation.EmptyViewMode.MODE_NO_REVIEWS;
import static org.michaelbel.moviemade.annotation.EmptyViewMode.MODE_NO_TRAILERS;

@Retention(RetentionPolicy.CLASS)
@IntDef({
    MODE_NO_CONNECTION,
    MODE_NO_MOVIES,
    MODE_NO_PEOPLE,
    MODE_NO_REVIEWS,
    MODE_NO_RESULTS,
    MODE_NO_HISTORY,
    MODE_NO_TRAILERS,
})
@Documented
@EmptyViewMode
public @interface EmptyViewMode {
    int MODE_NO_CONNECTION = 0;
    int MODE_NO_MOVIES = 1;
    int MODE_NO_PEOPLE = 2;
    int MODE_NO_REVIEWS = 3;
    int MODE_NO_RESULTS = 4;
    int MODE_NO_HISTORY = 5;
    int MODE_NO_TRAILERS = 6;
}