package org.michaelbel.domain

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.data.Video
import org.michaelbel.data.local.dao.TrailersDao
import org.michaelbel.data.remote.Api

class TrailersRepository(private val api: Api, private val dao: TrailersDao): Repository() {

    fun trailers(movieId: Int, apiKey: String, language: String): Observable<List<Video>> {
        return api.getVideos(movieId, apiKey, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    add(movieId, it.trailers)
                    return@flatMap Observable.just(it)
                }
                .map { it.trailers }

    }

    fun add(movieId: Int, items: List<Video>) {
        val trailers = ArrayList<Video>()
        for (item in items) {
            trailers.add(item.copy(movieId = movieId))
        }

        /*dao.insert(trailers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()*/
    }
}