package org.michaelbel.moviemade.presentation.features.favorites

import org.michaelbel.moviemade.core.net.NetworkUtil
import org.michaelbel.moviemade.presentation.base.Presenter
import retrofit2.HttpException

class FavoritesPresenter(
        private val repository: FavoritesContract.Repository
): Presenter(), FavoritesContract.Presenter {

    private var page: Int = 0
    private lateinit var view: FavoritesContract.View

    override fun attach(view: FavoritesContract.View) {
        this.view = view
    }

    override fun getFavoriteMovies(accountId: Int, sessionId: String) {
        /*if (NetworkUtil.isNetworkConnected().not()) {
            view?.error(Throwable("no connection"))
            return
        }*/

        page = 1
        disposable.add(repository.getFavoriteMovies(accountId, sessionId, page)
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

    override fun getFavoriteMoviesNext(accountId: Int, sessionId: String) {
        if (NetworkUtil.isNetworkConnected().not()) {
            return
        }

        page++
        disposable.add(repository.getFavoriteMovies(accountId, sessionId, page)
                .subscribe { view.content(it)})
    }

    override fun destroy() {
        disposable.dispose()
    }
}