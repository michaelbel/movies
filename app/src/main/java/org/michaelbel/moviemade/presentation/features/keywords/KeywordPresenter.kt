package org.michaelbel.moviemade.presentation.features.keywords

import io.reactivex.disposables.CompositeDisposable
import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.core.utils.NetworkUtil
import org.michaelbel.moviemade.presentation.base.Presenter
import java.util.*

class KeywordPresenter(repository: KeywordRepository): Presenter(), KeywordContract.Presenter {

    private var page: Int = 0
    private var view: KeywordContract.View? = null
    private val repository: KeywordContract.Repository = repository

    override fun attach(view: KeywordContract.View) {
        this.view = view
    }

    override fun getMovies(keywordId: Int) {
        if (!NetworkUtil.isNetworkConnected()) {
            view?.setError(EmptyViewMode.MODE_NO_CONNECTION)
            return
        }

        page = 1
        disposable.add(repository.getMovies(keywordId, page)
                .subscribe({ (_, movies) ->
                    val results = ArrayList(movies)
                    if (results.isEmpty()) {
                        view?.setError(EmptyViewMode.MODE_NO_MOVIES)
                        return@subscribe
                    }
                    view!!.setMovies(results)
                }, { view?.setError(EmptyViewMode.MODE_NO_MOVIES) }))
    }

    override fun getMoviesNext(keywordId: Int) {
        if (!NetworkUtil.isNetworkConnected()) {
            return
        }

        page++
        disposable.add(repository.getMovies(keywordId, page).subscribe { (_, movies) -> view?.setMovies(movies) })
    }

    override fun destroy() {
        disposable.dispose()
    }
}