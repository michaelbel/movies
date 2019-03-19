package org.michaelbel.moviemade.presentation.features.main

import org.michaelbel.moviemade.core.errors.EmptyViewMode
import org.michaelbel.moviemade.core.net.NetworkUtil
import org.michaelbel.moviemade.presentation.base.Presenter
import retrofit2.HttpException

class MainPresenter(
        private val repository: MainContract.Repository
): Presenter(), MainContract.Presenter {

    private var page: Int = 0
    private lateinit var view: MainContract.View

    override fun attach(view: MainContract.View) {
        this.view = view
    }

    override fun movies(movieId: Int, list: String) {
        if (!NetworkUtil.isNetworkConnected()) {
            view.error(EmptyViewMode.MODE_NO_CONNECTION)
            return
        }

        page = 1
        disposable.add(repository.movies(movieId, list, page)
                .doOnSubscribe { view.loading(true) }
                .doAfterTerminate { view.loading(false) }
                .subscribe({
                    if (it.isEmpty()) {
                        view.error(EmptyViewMode.MODE_NO_MOVIES)
                        return@subscribe
                    }
                    view.content(it)
                }, { view.error(EmptyViewMode.MODE_NO_MOVIES) }))
    }

    override fun moviesNext(movieId: Int, list: String) {
        if (NetworkUtil.isNetworkConnected().not()) return

        page++
        disposable.add(repository.movies(movieId, list, page).subscribe { view.content(it) })
    }

    override fun movies(keywordId: Int) {
        if (NetworkUtil.isNetworkConnected().not()) {
            view.error(EmptyViewMode.MODE_NO_CONNECTION)
            return
        }

        page = 1
        disposable.add(repository.movies(keywordId, page)
                .doOnSubscribe { view.loading(true) }
                .doAfterTerminate { view.loading(false) }
                .switchIfEmpty { view.error(EmptyViewMode.MODE_NO_MOVIES) }
                .subscribe({ view.content(it) }, { view.error(EmptyViewMode.MODE_NO_MOVIES) }))
    }

    override fun moviesNext(keywordId: Int) {
        if (NetworkUtil.isNetworkConnected().not()) {
            return
        }

        page++
        disposable.add(repository.movies(keywordId, page).subscribe { view.content(it) })
    }

    override fun moviesWatchlist(accountId: Int, sessionId: String) {
        /*if (!NetworkUtil.isNetworkConnected()) {
            view?.error(EmptyViewMode.MODE_NO_CONNECTION)
            return
        }*/

        page = 1
        disposable.add(repository.moviesWatchlist(accountId, sessionId, page)
                .doOnSubscribe { view.loading(true) }
                .doAfterTerminate { view.loading(false) }
                .subscribe({
                    if (it.isEmpty()) {
                        view.error(1)
                        return@subscribe
                    }
                    view.content(it) }, { view.error((it as HttpException).code()) }
                )
        )
    }

    override fun moviesWatchlistNext(accountId: Int, sessionId: String) {
        if (!NetworkUtil.isNetworkConnected()) return

        page++
        disposable.add(repository.moviesWatchlist(accountId, sessionId, page)
                .subscribe { view.content(it) })
    }

    override fun moviesFavorite(accountId: Int, sessionId: String) {
        /*if (NetworkUtil.isNetworkConnected().not()) {
            view?.error(Throwable("no connection"))
            return
        }*/

        page = 1
        disposable.add(repository.moviesFavorite(accountId, sessionId, page)
                .doOnSubscribe { view.loading(true) }
                .doAfterTerminate { view.loading(false) }
                .subscribe(
                        {
                            if (it.isEmpty()) {
                                view.error(1)
                                return@subscribe
                            }
                            view.content(it) },
                        { view.error((it as HttpException).code())}
                )
        )
    }

    override fun moviesFavoriteNext(accountId: Int, sessionId: String) {
        if (NetworkUtil.isNetworkConnected().not()) {
            return
        }

        page++
        disposable.add(repository.moviesFavorite(accountId, sessionId, page)
                .subscribe { view.content(it)})
    }

    override fun destroy() {
        disposable.dispose()
    }
}