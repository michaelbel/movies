package org.michaelbel.moviemade.ui.modules.main

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.Moviemade
import org.michaelbel.moviemade.data.entity.MoviesResponse
import org.michaelbel.moviemade.data.service.MoviesService
import org.michaelbel.moviemade.utils.LangUtil

class MainRepository internal constructor(private val service: MoviesService) : MainContract.Repository {

    override fun getNowPlaying(page: Int): Observable<MoviesResponse> {
        return service.getNowPlaying(BuildConfig.TMDB_API_KEY, LangUtil.getLanguage(Moviemade.appContext), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getTopRated(page: Int): Observable<MoviesResponse> {
        return service.getTopRated(BuildConfig.TMDB_API_KEY, LangUtil.getLanguage(Moviemade.appContext), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getUpcoming(page: Int): Observable<MoviesResponse> {
        return service.getUpcoming(BuildConfig.TMDB_API_KEY, LangUtil.getLanguage(Moviemade.appContext), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}