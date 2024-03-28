package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import org.michaelbel.movies.persistence.database.entity.SuggestionPojo

class SuggestionDbPreviewParameterProvider: CollectionPreviewParameterProvider<List<SuggestionPojo>>(
    listOf(
        listOf(
            SuggestionPojo(
                title = "Leo"
            ),
            SuggestionPojo(
                title = "Trolls Band Together"
            ),
            SuggestionPojo(
                title = "Five Nights at Freddy's"
            ),
            SuggestionPojo(
                title = "Godzilla Minus One"
            ),
            SuggestionPojo(
                title = "Napoleon"
            )
        )
    )
)