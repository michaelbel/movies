package org.michaelbel.movies.domain.data.ktx

import org.michaelbel.movies.domain.data.entity.MovieDb

val MovieDb.isNotEmpty: Boolean
    get() = this != MovieDb.Empty