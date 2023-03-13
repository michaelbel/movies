package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.michaelbel.movies.domain.data.entity.AccountDb

class AccountPreviewParameterProvider: PreviewParameterProvider<AccountDb> {
    override val values: Sequence<AccountDb> = sequenceOf(
        AccountDb(
            accountId = 7692212,
            avatarUrl = "",
            language = "en",
            country = "US",
            name = "Michael Bely",
            adult = true,
            username = "michaelbel"
        )
    )
}