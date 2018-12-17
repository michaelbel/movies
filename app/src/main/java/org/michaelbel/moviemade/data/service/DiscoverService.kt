package org.michaelbel.moviemade.data.service

import io.reactivex.Observable
import retrofit2.http.GET

interface DiscoverService {

    @GET("discover/movie?")
    fun movieDiscover(): Observable<*>

    @GET("discover/tv?")
    fun tvDiscover(): Observable<*>
}