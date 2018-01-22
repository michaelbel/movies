package org.michaelbel.moviemade.rest.api;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface DISCOVER {

    @GET("discover/movie?")
    Observable<?> movieDiscover(

    );

    @GET("discover/tv?")
    Observable<?> tvDiscover(

    );
}