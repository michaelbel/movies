package org.michaelbel.moviemade.rest.api.service;

import org.michaelbel.moviemade.rest.model.v3.Company;
import org.michaelbel.moviemade.rest.response.MoviesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface COMPANIES {

    @GET("company/{company_id}?")
    Observable<Company> getDetails(
        @Path("company_id") int id,
        @Query("api_key") String apiKey
    );

    @GET("company/{company_id}/movies?")
    Observable<MoviesResponse> getMovies(
        @Path("company_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String language
    );
}