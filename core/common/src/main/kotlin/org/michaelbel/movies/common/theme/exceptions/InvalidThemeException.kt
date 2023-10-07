package org.michaelbel.movies.common.theme.exceptions

internal data object InvalidThemeException: Exception("Invalid theme") {
    private fun readResolve(): Any = InvalidThemeException
}