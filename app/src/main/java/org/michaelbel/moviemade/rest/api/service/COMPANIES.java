package org.michaelbel.moviemade.rest.api.service;

import org.michaelbel.moviemade.rest.TmdbService;
import org.michaelbel.moviemade.rest.model.Company;
import org.michaelbel.moviemade.rest.response.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface COMPANIES extends TmdbService {

    @GET("company/{company_id}?")
    Call<Company> getDetails(
        @Path("company_id") int id,
        @Query("api_key") String apiKey
    );

    @GET("company/{company_id}/movies?")
    Call<MoviesResponse> getMovies(
        @Path("company_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String language
    );
}