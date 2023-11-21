package org.michaelbel.movies.network.service.image

import org.michaelbel.movies.network.model.ImagesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageService {

    @GET("movie/{movie_id}/images")
    suspend fun images(
        @Path("movie_id") id: Int
    ): ImagesResponse
}