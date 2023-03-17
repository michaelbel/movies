package org.michaelbel.movies.auth.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.michaelbel.movies.domain.exceptions.AccountDetailsException
import org.michaelbel.movies.domain.exceptions.CreateRequestTokenException
import org.michaelbel.movies.domain.exceptions.CreateSessionException
import org.michaelbel.movies.domain.exceptions.CreateSessionWithLoginException

internal class ThrowablePreviewParameterProvider: PreviewParameterProvider<Throwable> {
    override val values: Sequence<Throwable> = sequenceOf(
        CreateRequestTokenException,
        CreateSessionWithLoginException,
        CreateSessionException,
        AccountDetailsException
    )
}