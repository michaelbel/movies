package org.michaelbel.moviemade.presentation.features.keywords

import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.core.utils.NetworkUtil

import java.util.ArrayList

import org.michaelbel.moviemade.presentation.base.Presenter

class KeywordsPresenter(repository: KeywordsRepository): Presenter(), KeywordsContract.Presenter {

    private var view: KeywordsContract.View? = null
    private val repository: KeywordsContract.Repository = repository

    override fun attach(view: KeywordsContract.View) {
        this.view = view
    }

    override fun getKeywords(movieId: Int) {
        if (!NetworkUtil.isNetworkConnected()) {
            view?.setError(EmptyViewMode.MODE_NO_CONNECTION)
            return
        }

        disposable.add(repository.getKeywords(movieId)
                .subscribe({ (_, keywords) ->
                    val results = ArrayList(keywords)
                    if (results.isEmpty()) {
                        view?.setError(EmptyViewMode.MODE_NO_KEYWORDS)
                        return@subscribe
                    }
                    view?.setKeywords(results)
                }, { view?.setError(EmptyViewMode.MODE_NO_KEYWORDS) }))
    }

    override fun destroy() {
        disposable.dispose()
    }
}