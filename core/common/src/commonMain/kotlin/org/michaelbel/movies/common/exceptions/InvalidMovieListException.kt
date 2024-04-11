package org.michaelbel.movies.common.exceptions

internal data object InvalidMovieListException: Exception("Invalid movie list") {
    private fun readResolve(): Any = InvalidMovieListException
}