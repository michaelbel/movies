package org.michaelbel.movies.network.ktx

import org.michaelbel.movies.network.model.Result

val <T> Result<T>.nextPage: Int
    get() = page.plus(1)

val <T> Result<T>.isPaginationReached: Boolean
    get() = page == totalPages