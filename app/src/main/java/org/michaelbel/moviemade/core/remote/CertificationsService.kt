package org.michaelbel.moviemade.core.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CertificationsService {

    @GET("certification/movie/list?")
    fun getMovieCertifications(@Query("api_key") apiKey: String): Observable<*>

    @GET("certification/tv/list?")
    fun getTVCertifications(@Query("api_key") apiKey: String): Observable<*>
}