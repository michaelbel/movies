package org.michaelbel.movies.common.exceptions

object AccountDetailsException: Exception() {
    private fun readResolve(): Any = AccountDetailsException
}