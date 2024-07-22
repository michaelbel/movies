package org.michaelbel.movies.ui.preview

import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo
import org.michaelbel.movies.ui.preview.base.CollectionPreviewParameterProvider

class AccountPreviewParameterProvider: CollectionPreviewParameterProvider<AccountPojo>(
    listOf(
        AccountPojo(
            accountId = 7692212,
            avatarUrl = "",
            language = "en",
            country = "US",
            name = "Michael Bely",
            adult = true,
            username = "michaelbel"
        )
    )
)