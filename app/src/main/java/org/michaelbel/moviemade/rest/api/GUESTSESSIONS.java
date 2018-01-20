package org.michaelbel.moviemade.rest.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GUESTSESSIONS {

    @GET("guest_session/{guest_session_id}/rated/movies?")
    Call<?> getRatedMovies(

    );

    @GET("guest_session/{guest_session_id}/rated/tv?")
    Call<?> getRatedTVShows(

    );

    @GET("guest_session/{guest_session_id}/rated/tv/episodes?")
    Call<?> getRatedTVEpisodes(

    );
}