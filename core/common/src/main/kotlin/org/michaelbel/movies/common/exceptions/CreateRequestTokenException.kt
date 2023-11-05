package org.michaelbel.movies.common.exceptions

object CreateRequestTokenException: Exception() {
    private fun readResolve(): Any = CreateRequestTokenException
}