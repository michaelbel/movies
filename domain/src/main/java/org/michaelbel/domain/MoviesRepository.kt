package org.michaelbel.domain

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.data.Movie
import org.michaelbel.data.local.dao.MoviesDao
import org.michaelbel.data.remote.Api
import org.michaelbel.data.remote.model.AccountStates
import org.michaelbel.data.remote.model.Fave
import org.michaelbel.data.remote.model.Mark
import org.michaelbel.data.remote.model.Watch

class MoviesRepository(private val api: Api, private val dao: MoviesDao) {

    fun movies(movieId: Int, list: String, apiKey: String, language: String, page: Int): Observable<List<Movie>> {
        val response = if (movieId == 0)
            api.movies(list, apiKey, language, page)
        else
            api.moviesById(movieId, list, apiKey, language, page)

        return response
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    add(it.movies)
                    return@flatMap Observable.just(it)
                }
                .map { it.movies }
    }

    fun movies(keywordId: Int, apiKey: String, language: String, adult: Boolean, page: Int): Observable<List<Movie>>  {
        return api.moviesByKeyword(keywordId, apiKey, language, adult, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    //add(it.movies)
                    return@flatMap Observable.just(it)
                }
                .map { it.movies }
    }

    fun moviesWatchlist(accountId: Int, apiKey: String, sessionId: String, language: String, sort: String, page: Int): Observable<List<Movie>> {
        return api.moviesWatchlist(accountId, apiKey, sessionId, language, sort, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    //add(it.movies)
                    return@flatMap Observable.just(it)
                }
                .map { it.movies }
    }

    fun moviesFavorite(accountId: Int, apiKey: String, sessionId: String, language: String, sort: String, page: Int): Observable<List<Movie>> {
        return api.moviesFavorite(accountId, apiKey, sessionId, language, sort, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    //add(it.movies)
                    return@flatMap Observable.just(it)
                }
                .map { it.movies }
    }

    fun searchMovies(apiKey: String, language: String, query: String, page: Int, adult: Boolean): Observable<List<Movie>> {
        return api.searchMovies(apiKey, language, query, page, adult, "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    //add(it.movies)
                    return@flatMap Observable.just(it)
                }
                .map { it.movies }
    }

    fun details(movieId: Int, apiKey: String, language: String, addToResponse: String): Observable<Movie> {
        return api.getDetails(movieId, apiKey, language, addToResponse)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun markFavorite(contentType: String, accountId: Int, apiKey: String, sessionId: String, mediaId: Int, favorite: Boolean): Observable<Mark> {
        return api.markAsFavorite(contentType, accountId, apiKey, sessionId, Fave(Movie.MOVIE, mediaId, favorite))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun addWatchlist(contentType: String, accountId: Int, sessionId: String, apiKey: String, mediaId: Int, watchlist: Boolean): Observable<Mark> {
        return api.addToWatchlist(contentType, accountId, apiKey, sessionId, Watch(Movie.MOVIE, mediaId, watchlist))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun accountStates(movieId: Int, apiKey: String, sessionId: String): Observable<AccountStates> {
        return api.getAccountStates(movieId, apiKey, sessionId, "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun add(items: List<Movie>) {
        /*dao.insert(items)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()*/
    }
}