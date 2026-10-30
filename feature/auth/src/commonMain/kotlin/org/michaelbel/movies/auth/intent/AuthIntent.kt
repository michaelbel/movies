package org.michaelbel.movies.auth.intent

import org.michaelbel.movies.common.mvi.Intent
import org.michaelbel.movies.interactor.entity.Password
import org.michaelbel.movies.interactor.entity.Username

sealed interface AuthIntent: Intent {
    data object BackClick: AuthIntent
    data object LoginClick: AuthIntent
    data object ResetRequestToken: AuthIntent
    data object CollectProvider: AuthIntent
    data object TraktSignInClick: AuthIntent
    data object TraktSignInCancelClick: AuthIntent
    data class UsernameChange(val username: Username): AuthIntent
    data class PasswordChange(val password: Password): AuthIntent
    data class SignInClick(val username: Username, val password: Password): AuthIntent
}
