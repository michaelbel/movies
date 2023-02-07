package org.michaelbel.movies.entities.ktx

import org.michaelbel.movies.entities.MovieDetailsData

val MovieDetailsData.isNotEmpty: Boolean
    get() = this != MovieDetailsData.Empty