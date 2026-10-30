package org.michaelbel.movies.common.trakt

data class TraktAuthResult(
    val code: String,
    val codeVerifier: String
)
