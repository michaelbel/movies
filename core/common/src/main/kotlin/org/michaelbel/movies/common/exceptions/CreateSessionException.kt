package org.michaelbel.movies.common.exceptions

object CreateSessionException: Exception() {
    private fun readResolve(): Any = CreateSessionException
}