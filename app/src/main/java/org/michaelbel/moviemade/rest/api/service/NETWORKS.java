package org.michaelbel.moviemade.rest.api.service;

import org.michaelbel.moviemade.rest.model.v3.Network;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NETWORKS {

    @GET("network/{network_id}?")
    Observable<Network> getDetails(
        @Path("network_id") int id,
        @Query("api_key") String apiKey
    );
}