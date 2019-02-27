package org.michaelbel.moviemade.presentation.features.search

import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.core.utils.NetworkUtil

import java.util.ArrayList

import io.reactivex.disposables.CompositeDisposable
import org.michaelbel.moviemade.presentation.base.Presenter

class SearchMoviesPresenter(repository: SearchRepository): Presenter(), SearchContract.Presenter {

    private var page: Int = 0
    private var currentQuery: String? = null
    private var view: SearchContract.View? = null
    private val repository: SearchContract.Repository = repository

    override fun attach(view: SearchContract.View) {
        this.view = view
    }

    override fun search(query: String) {
        currentQuery = query
        view?.searchStart()

        if (!NetworkUtil.isNetworkConnected()) {
            view?.setError(EmptyViewMode.MODE_NO_CONNECTION)
            return
        }

        page = 1
        disposable.add(repository.search(query, page)
                .subscribe({ (_, movies) ->
                    val results = ArrayList(movies)
                    if (results.isEmpty()) {
                        view!!.setError(EmptyViewMode.MODE_NO_RESULTS)
                        return@subscribe
                    }
                    view?.setMovies(results)
                }, { view?.setError(EmptyViewMode.MODE_NO_RESULTS) }))
    }

    override fun loadNextResults() {
        if (!NetworkUtil.isNetworkConnected()) return

        page++
        disposable.add(repository.search(currentQuery!!, page).subscribe { (_, movies) -> view!!.setMovies(movies) })
    }

    override fun destroy() {
        disposable.dispose()
    }
}