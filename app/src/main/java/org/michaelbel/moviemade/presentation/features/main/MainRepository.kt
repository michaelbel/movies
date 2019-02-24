package org.michaelbel.moviemade.presentation.features.main

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.entity.MoviesResponse
import org.michaelbel.moviemade.core.remote.MoviesService
import org.michaelbel.moviemade.core.utils.LocalUtil
import org.michaelbel.moviemade.presentation.App

class MainRepository internal constructor(
        private val service: MoviesService
): MainContract.Repository {

    override fun getNowPlaying(page: Int): Observable<MoviesResponse> =
        service.getNowPlaying(TMDB_API_KEY, LocalUtil.getLanguage(App.appContext), page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getTopRated(page: Int): Observable<MoviesResponse> =
        service.getTopRated(TMDB_API_KEY, LocalUtil.getLanguage(App.appContext), page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getUpcoming(page: Int): Observable<MoviesResponse> =
        service.getUpcoming(TMDB_API_KEY, LocalUtil.getLanguage(App.appContext), page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}