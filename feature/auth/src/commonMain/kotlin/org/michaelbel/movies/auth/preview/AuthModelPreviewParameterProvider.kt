package org.michaelbel.movies.auth.preview

import kotlinx.coroutines.Job
import org.michaelbel.movies.auth.model.AuthModel
import org.michaelbel.movies.domain.usecase.CreateSessionWithLoginUseCase.CreateSessionWithLoginException
import org.michaelbel.movies.interactor.entity.AppProvider
import org.michaelbel.movies.interactor.entity.Password
import org.michaelbel.movies.interactor.entity.Username
import org.michaelbel.movies.ui.preview.base.CollectionPreviewParameterProvider

class AuthModelPreviewParameterProvider: CollectionPreviewParameterProvider<AuthModel>(
    listOf(
        AuthModel(),
        AuthModel(
            username = Username("michaelbel"),
            password = Password("password123"),
            error = CreateSessionWithLoginException("")
        ),
        AuthModel(
            username = Username("michaelbel"),
            password = Password("password123"),
            signInJob = Job()
        ),
        AuthModel(
            username = Username("michaelbel"),
            password = Password("password123"),
            loginJob = Job()
        ),
        AuthModel(
            provider = AppProvider.Trakt
        ),
        AuthModel(
            provider = AppProvider.Trakt,
            traktSignInJob = Job()
        ),
        AuthModel(
            provider = AppProvider.Trakt,
            error = CreateSessionWithLoginException("")
        )
    )
)
