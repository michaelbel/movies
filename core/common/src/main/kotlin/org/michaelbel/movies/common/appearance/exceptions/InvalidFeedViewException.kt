package org.michaelbel.movies.common.appearance.exceptions

internal data object InvalidFeedViewException: Exception("Invalid feed view") {
    private fun readResolve(): Any = InvalidFeedViewException
}