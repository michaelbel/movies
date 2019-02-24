package org.michaelbel.moviemade.core.remote

import io.reactivex.Observable
import retrofit2.http.GET

interface GuestSessionsService {

    @GET("guest_session/{guest_session_id}/rated/parts?")
    fun getRatedMovies(): Observable<*>

    @GET("guest_session/{guest_session_id}/rated/tv?")
    fun getRatedTVShows(): Observable<*>

    @GET("guest_session/{guest_session_id}/rated/tv/episodes?")
    fun getRatedTVEpisodes(): Observable<*>
}