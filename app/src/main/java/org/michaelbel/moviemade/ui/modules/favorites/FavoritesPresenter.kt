package org.michaelbel.moviemade.ui.modules.favorites

import io.reactivex.disposables.CompositeDisposable
import org.michaelbel.moviemade.utils.EmptyViewMode
import org.michaelbel.moviemade.utils.NetworkUtil
import java.util.*

class FavoritesPresenter(repository: FavoriteRepository) : FavoritesContract.Presenter {

    private var page: Int = 0
    private var view: FavoritesContract.View? = null
    private val repository: FavoritesContract.Repository
    private val disposables = CompositeDisposable()

    init {
        this.repository = repository
    }

    override fun setView(view: FavoritesContract.View) {
        this.view = view
    }

    override fun getFavoriteMovies(accountId: Int, sessionId: String) {
        if (NetworkUtil.notConnected()) {
            view!!.setError(EmptyViewMode.MODE_NO_CONNECTION)
            return
        }

        page = 1
        // todo make map to check results.isEmpty()
        disposables.add(repository.getFavoriteMovies(accountId, sessionId, page)
            .subscribe({ (_, movies) ->
                val results = ArrayList(movies)
                if (results.isEmpty()) {
                    view!!.setError(EmptyViewMode.MODE_NO_MOVIES)
                    return@subscribe
                }
                view!!.setMovies(results)
            }, { view!!.setError(EmptyViewMode.MODE_NO_MOVIES) }))

        /*disposables.add(service.getFavoriteMovies(accountId, BuildConfig.TMDB_API_KEY, sessionId, TmdbConfigKt.LANG_EN_US, OrderKt.ASC, page)
            .doOnSubscribe(disposable -> getViewState().showLoading())
            .doAfterTerminate(() -> getViewState().hideLoading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> getViewState().setContent(new ArrayList<>(response.getMovies())), e -> getViewState().setError(EmptyViewMode.MODE_NO_MOVIES)));*/
    }

    override fun getFavoriteMoviesNext(accountId: Int, sessionId: String) {
        page++
        disposables.add(repository.getFavoriteMovies(accountId, sessionId, page)
            .subscribe { (_, movies) ->
                // Fixme.
                val results = ArrayList(movies)
                if (results.isEmpty()) {
                    view!!.setError(EmptyViewMode.MODE_NO_MOVIES)
                    return@subscribe
                }
                view!!.setMovies(results)
            })
    }

    override fun onDestroy() {
        disposables.dispose()
    }
}