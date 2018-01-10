package org.michaelbel.moviemade.rest.api.v3;

import org.michaelbel.moviemade.rest.model.v3.Collection;
import org.michaelbel.moviemade.rest.model.v3.CollectionImages;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface COLLECTIONS {

    @GET("/collection/{collection_id}?")
    Call<Collection> getDetails(
            @Path("collection_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("/collection/{collection_id}/images?")
    Call<CollectionImages> getImages(
            @Path("collection_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
}