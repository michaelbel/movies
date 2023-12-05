package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.michaelbel.movies.persistence.database.entity.SuggestionDb

class SuggestionDbPreviewParameterProvider: PreviewParameterProvider<List<SuggestionDb>> {
    override val values: Sequence<List<SuggestionDb>> = sequenceOf(
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
}