package org.michaelbel.movies.interactor.exception

internal data object InvalidLocaleException: Exception("Invalid locale") {
    private fun readResolve(): Any = InvalidLocaleException
}