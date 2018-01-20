package org.michaelbel.moviemade.rest.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DISCOVER {

    @GET("discover/movie?")
    Call<?> movieDiscover(

    );

    @GET("discover/tv?")
    Call<?> tvDiscover(

    );
}