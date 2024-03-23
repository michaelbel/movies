package org.michaelbel.movies.common.localization.exceptions

internal data object InvalidLocaleException: Exception("Invalid locale") {
    private fun readResolve(): Any = InvalidLocaleException
}