package org.michaelbel.movies.common.exceptions

data class CreateRequestTokenException(
    val loginViaTmdb: Boolean
): Exception()