package org.michaelbel.movies.interactor.entity

data class AuthRedirect(
    val requestToken: String?,
    val approved: Boolean?
)
