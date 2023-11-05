package org.michaelbel.movies.common.exceptions

object CreateSessionWithLoginException: Exception() {
    private fun readResolve(): Any = CreateSessionWithLoginException
}