package org.michaelbel.movies.auth.model

import kotlinx.coroutines.Job
import org.michaelbel.movies.common.mvi.model.Model
import org.michaelbel.movies.interactor.entity.AppProvider
import org.michaelbel.movies.interactor.entity.Password
import org.michaelbel.movies.interactor.entity.Username

data class AuthModel(
    val username: Username = Username(""),
    val password: Password = Password(""),
    val requestToken: String? = null,
    val error: Throwable? = null,
    val loginJob: Job? = null,
    val signInJob: Job? = null,
    val provider: AppProvider = AppProvider.Tmdb,
    val traktSignInJob: Job? = null
): Model {

    val isTraktProviderSelected: Boolean
        get() = provider is AppProvider.Trakt

    val isTraktSignInJobActive: Boolean
        get() = traktSignInJob != null && traktSignInJob.isActive


    val isLoginJobActive: Boolean
        get() = loginJob != null && loginJob.isActive

    val isSignInJobActive: Boolean
        get() = signInJob != null && signInJob.isActive
}
