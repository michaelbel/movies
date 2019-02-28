package org.michaelbel.moviemade.presentation.features.keywords

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.Keyword
import org.michaelbel.moviemade.core.entity.KeywordsResponse
import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.presentation.base.BasePresenter

interface KeywordsContract {

    interface View {
        fun setKeywords(keywords: List<Keyword>)
        fun setError(@EmptyViewMode mode: Int)
    }

    interface Presenter: BasePresenter<View> {
        fun getKeywords(movieId: Int)
    }

    interface Repository {
        fun getKeywords(movieId: Int): Observable<KeywordsResponse>
    }
}