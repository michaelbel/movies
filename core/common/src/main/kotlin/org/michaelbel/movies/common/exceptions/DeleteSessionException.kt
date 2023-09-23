package org.michaelbel.movies.common.exceptions

object DeleteSessionException: Exception() {
    private fun readResolve(): Any = DeleteSessionException
}