package org.michaelbel.movies.interactor.usecase

import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.interactor.Interactor
import javax.inject.Inject

class SelectFeedViewCase @Inject constructor(
    private val interactor: Interactor
) {
    suspend operator fun invoke(feedView: FeedView) {
        interactor.selectFeedView(feedView)
    }
}