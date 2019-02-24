package org.michaelbel.moviemade.core.remote

import io.reactivex.Observable
import retrofit2.http.GET

interface DiscoverService {

    @GET("discover/movie?")
    fun movieDiscover(): Observable<*>

    @GET("discover/tv?")
    fun tvDiscover(): Observable<*>
}