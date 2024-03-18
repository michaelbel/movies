@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.michaelbel.movies.persistence.database.entity.AccountDb

actual class AccountPreviewParameterProvider: PreviewParameterProvider<AccountDb> {
    override val values = sequenceOf(
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