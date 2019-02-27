package org.michaelbel.moviemade.presentation.features.similar

import org.michaelbel.moviemade.core.utils.Error
import org.michaelbel.moviemade.core.utils.NetworkUtil

import io.reactivex.disposables.CompositeDisposable
import org.michaelbel.moviemade.presentation.base.Presenter
import retrofit2.HttpException

class SimilarMoviesPresenter(repository: SimilarRepository): Presenter(), SimilarContract.Presenter {

    private var page: Int = 0
    private var view: SimilarContract.View? = null
    private val repository: SimilarContract.Repository = repository

    override fun attach(view: SimilarContract.View) {
        this.view = view
    }

    override fun getSimilarMovies(movieId: Int) {
        if (!NetworkUtil.isNetworkConnected()) {
            view?.setError(Throwable("No connection"), Error.ERR_NO_CONNECTION)
            return
        }

        page = 1
        disposable.add(repository.getSimilarMovies(movieId, page)
                .subscribe(
                        { (_, movies) ->
                            if (movies.isEmpty()) {
                                view!!.setError(Throwable("No movies"), Error.ERR_NO_MOVIES)
                                return@subscribe
                            }
                            view?.setMovies(movies)
                        },
                        { e -> view!!.setError(e, (e as HttpException).code()) }
                )
        )
    }

    override fun getSimilarMoviesNext(movieId: Int) {
        if (!NetworkUtil.isNetworkConnected()) {
            return
        }

        page++
        disposable.add(repository.getSimilarMovies(movieId, page)
                .subscribe { (_, movies) -> view!!.setMovies(movies) })
    }

    override fun destroy() {
        disposable.dispose()
    }
}