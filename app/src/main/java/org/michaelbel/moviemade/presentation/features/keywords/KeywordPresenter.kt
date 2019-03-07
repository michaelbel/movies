package org.michaelbel.moviemade.presentation.features.keywords

import org.michaelbel.moviemade.core.EmptyViewMode
import org.michaelbel.moviemade.core.net.NetworkUtil
import org.michaelbel.moviemade.presentation.base.Presenter

class KeywordPresenter(
        private val repository: KeywordRepository
): Presenter(), KeywordContract.Presenter {

    private var page: Int = 0
    private lateinit var view: KeywordContract.View

    override fun attach(view: KeywordContract.View) {
        this.view = view
    }

    override fun getMovies(keywordId: Int) {
        if (NetworkUtil.isNetworkConnected().not()) {
            view.error(EmptyViewMode.MODE_NO_CONNECTION)
            return
        }

        page = 1
        disposable.add(repository.getMovies(keywordId, page)
                .doOnSubscribe { view.loading(true) }
                .doAfterTerminate { view.loading(false) }
                .switchIfEmpty { view.error(EmptyViewMode.MODE_NO_MOVIES) }
                .subscribe({ view.content(it) }, { view.error(EmptyViewMode.MODE_NO_MOVIES) }))
    }

    override fun getMoviesNext(keywordId: Int) {
        if (NetworkUtil.isNetworkConnected().not()) {
            return
        }

        page++
        disposable.add(repository.getMovies(keywordId, page).subscribe { view.content(it) })
    }

    override fun destroy() {
        disposable.dispose()
    }
}