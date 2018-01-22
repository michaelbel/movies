package org.michaelbel.moviemade.rest.api.service;

import org.michaelbel.moviemade.rest.model.v3.Collection;
import org.michaelbel.moviemade.rest.model.v3.CollectionImages;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface COLLECTIONS {

    @GET("collection/{collection_id}?")
    Observable<Collection> getDetails(
        @Path("collection_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String language
    );

    @GET("collection/{collection_id}/images?")
    Observable<CollectionImages> getImages(
        @Path("collection_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String language
    );
}