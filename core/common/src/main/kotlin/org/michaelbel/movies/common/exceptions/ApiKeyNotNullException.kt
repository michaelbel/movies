package org.michaelbel.movies.common.exceptions

object ApiKeyNotNullException: Exception() {
    private fun readResolve(): Any = ApiKeyNotNullException
}