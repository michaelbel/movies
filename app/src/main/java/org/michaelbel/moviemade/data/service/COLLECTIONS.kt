package org.michaelbel.moviemade.data.service

import io.reactivex.Observable
import org.michaelbel.moviemade.data.entity.Collection
import org.michaelbel.moviemade.data.entity.ImagesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface COLLECTIONS {

    @GET("collection/{collection_id}?")
    fun getDetails(
        @Path("collection_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<Collection>

    @GET("collection/{collection_id}/images?")
    fun getImages(
        @Path("collection_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<ImagesResponse>

    // getTranslations
}