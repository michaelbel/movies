package org.michaelbel.moviemade.presentation.features.search

import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.core.utils.NetworkUtil
import org.michaelbel.moviemade.presentation.base.Presenter
import java.util.*

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
                .subscribe({
                    val results = ArrayList(it.movies)
                    if (results.isEmpty()) {
                        view?.setError(EmptyViewMode.MODE_NO_RESULTS)
                        return@subscribe
                    }
                    view?.setMovies(results)
                }, { view?.setError(EmptyViewMode.MODE_NO_RESULTS) }))
    }

    override fun loadNextResults() {
        if (!NetworkUtil.isNetworkConnected()) return

        page++
        disposable.add(repository.search(currentQuery!!, page).subscribe { view?.setMovies(it.movies) })
    }

    override fun destroy() {
        disposable.dispose()
    }
}