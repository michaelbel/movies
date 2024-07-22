package org.michaelbel.movies.ui.preview

import org.michaelbel.movies.persistence.database.entity.pojo.SuggestionPojo
import org.michaelbel.movies.ui.preview.base.CollectionPreviewParameterProvider

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