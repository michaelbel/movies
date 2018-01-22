package org.michaelbel.moviemade.rest.api;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GUESTSESSIONS {

    @GET("guest_session/{guest_session_id}/rated/movies?")
    Observable<?> getRatedMovies(

    );

    @GET("guest_session/{guest_session_id}/rated/tv?")
    Observable<?> getRatedTVShows(

    );

    @GET("guest_session/{guest_session_id}/rated/tv/episodes?")
    Observable<?> getRatedTVEpisodes(

    );
}