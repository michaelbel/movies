package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import org.michaelbel.movies.persistence.database.entity.SuggestionDb

class SuggestionDbPreviewParameterProvider: CollectionPreviewParameterProvider<List<SuggestionDb>>(
    listOf(
        listOf(
            SuggestionDb(
                title = "Leo"
            ),
            SuggestionDb(
                title = "Trolls Band Together"
            ),
            SuggestionDb(
                title = "Five Nights at Freddy's"
            ),
            SuggestionDb(
                title = "Godzilla Minus One"
            ),
            SuggestionDb(
                title = "Napoleon"
            )
        )
    )
)