package org.michaelbel.moviemade.presentation.features.search

import org.michaelbel.moviemade.core.errors.EmptyViewMode
import org.michaelbel.moviemade.core.net.NetworkUtil
import org.michaelbel.moviemade.presentation.base.Presenter

class SearchMoviesPresenter(
        private val repository: SearchContract.Repository
): Presenter(), SearchContract.Presenter {

    private var page: Int = 0
    private var currentQuery: String? = null
    private lateinit var view: SearchContract.View

    override fun attach(view: SearchContract.View) {
        this.view = view
    }

    override fun search(query: String) {
        currentQuery = query
        view.searchStart()

        if (!NetworkUtil.isNetworkConnected()) {
            view.error(EmptyViewMode.MODE_NO_CONNECTION)
            return
        }

        page = 1
        disposable.add(repository.search(query, page)
                .doOnSubscribe { view.loading(true) }
                .doAfterTerminate { view.loading(false) }
                .subscribe({
                    if (it.isEmpty()) {
                        view.error(EmptyViewMode.MODE_NO_RESULTS)
                        return@subscribe
                    }
                    view.content(it)
                }, { view.error(EmptyViewMode.MODE_NO_RESULTS) }))
    }

    override fun nextResults() {
        if (!NetworkUtil.isNetworkConnected()) return

        page++
        disposable.add(repository.search(currentQuery!!, page).subscribe { view.content(it) })
    }

    override fun destroy() {
        disposable.dispose()
    }
}