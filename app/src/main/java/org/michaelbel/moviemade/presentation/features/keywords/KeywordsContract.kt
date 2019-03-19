package org.michaelbel.moviemade.presentation.features.keywords

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.Keyword
import org.michaelbel.moviemade.presentation.base.BasePresenter
import org.michaelbel.moviemade.presentation.base.BaseView

interface KeywordsContract {

    interface View: BaseView<List<Keyword>>

    interface Presenter: BasePresenter<View> {
        fun keywords(movieId: Int)
    }

    interface Repository {
        fun keywords(movieId: Int): Observable<List<Keyword>>
    }
}