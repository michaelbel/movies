package org.michaelbel.movies.common.exceptions

internal data object InvalidFeedViewException: Exception("Invalid feed view") {
    private fun readResolve(): Any = InvalidFeedViewException
}