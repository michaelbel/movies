package org.michaelbel.movies.common.list.exceptions

internal data object InvalidMovieListException: Exception("Invalid movie list") {
    private fun readResolve(): Any = InvalidMovieListException
}