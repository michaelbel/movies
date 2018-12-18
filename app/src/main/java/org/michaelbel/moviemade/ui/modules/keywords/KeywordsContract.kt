package org.michaelbel.moviemade.ui.modules.keywords

import io.reactivex.Observable
import org.michaelbel.moviemade.data.entity.Keyword
import org.michaelbel.moviemade.data.entity.KeywordsResponse
import org.michaelbel.moviemade.utils.EmptyViewMode

interface KeywordsContract {

    interface View {
        fun setKeywords(keywords: List<Keyword>)
        fun setError(@EmptyViewMode mode: Int)
    }

    interface Presenter {
        fun getKeywords(movieId: Int)
        fun onDestroy()
    }

    interface Repository {
        fun getKeywords(movieId: Int) : Observable<KeywordsResponse>
    }
}