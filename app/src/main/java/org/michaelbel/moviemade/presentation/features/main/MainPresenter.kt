package org.michaelbel.moviemade.presentation.features.main

import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.core.utils.NetworkUtil

import java.util.ArrayList

import io.reactivex.disposables.CompositeDisposable
import org.michaelbel.moviemade.presentation.base.Presenter

class MainPresenter(repository: MainRepository): Presenter(), MainContract.Presenter {

    private var page: Int = 0
    private var view: MainContract.View? = null
    private val repository: MainContract.Repository = repository

    override fun attach(view: MainContract.View) {
        this.view = view
    }

    override fun getNowPlaying() {
        if (!NetworkUtil.isNetworkConnected()) {
            view!!.setError(EmptyViewMode.MODE_NO_CONNECTION)
            return
        }

        page = 1
        disposable.add(repository.getNowPlaying(page)
                .subscribe({ (_, movies) ->
                    val results = ArrayList(movies)
                    if (results.isEmpty()) {
                        view!!.setError(EmptyViewMode.MODE_NO_MOVIES)
                        return@subscribe
                    }
                    view!!.setContent(results)
                }, { view!!.setError(EmptyViewMode.MODE_NO_MOVIES) }))
    }

    override fun getNowPlayingNext() {
        if (!NetworkUtil.isNetworkConnected()) return

        page++
        disposable.add(repository.getNowPlaying(page).subscribe { (_, movies) -> view!!.setContent(movies) })
    }

    override fun getTopRated() {
        if (!NetworkUtil.isNetworkConnected()) {
            view!!.setError(EmptyViewMode.MODE_NO_CONNECTION)
            return
        }

        page = 1
        disposable.add(repository.getTopRated(page)
                .subscribe({ (_, movies) ->
                    val results = ArrayList(movies)
                    if (results.isEmpty()) {
                        view?.setError(EmptyViewMode.MODE_NO_MOVIES)
                        return@subscribe
                    }
                    view?.setContent(results)
                }, { view?.setError(EmptyViewMode.MODE_NO_MOVIES) }))
    }

    override fun getTopRatedNext() {
        if (!NetworkUtil.isNetworkConnected()) return

        page++
        disposable.add(repository.getTopRated(page).subscribe { (_, movies) -> view!!.setContent(movies) })
    }

    override fun getUpcoming() {
        if (!NetworkUtil.isNetworkConnected()) {
            view?.setError(EmptyViewMode.MODE_NO_CONNECTION)
            return
        }

        page = 1
        disposable.add(repository.getUpcoming(page)
                .subscribe({ (_, movies) ->
                    val results = ArrayList(movies)
                    if (results.isEmpty()) {
                        view?.setError(EmptyViewMode.MODE_NO_MOVIES)
                        return@subscribe
                    }
                    view?.setContent(results)
                }, { view?.setError(EmptyViewMode.MODE_NO_MOVIES) }))
    }

    override fun getUpcomingNext() {
        if (!NetworkUtil.isNetworkConnected()) return

        page++
        disposable.add(repository.getUpcoming(page).subscribe { (_, movies) -> view?.setContent(movies) })
    }

    override fun destroy() {
        disposable.dispose()
    }
}