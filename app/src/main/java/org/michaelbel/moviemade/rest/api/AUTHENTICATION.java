package org.michaelbel.moviemade.rest.api;

import org.michaelbel.moviemade.rest.model.Auth;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

@SuppressWarnings("all")
public interface AUTHENTICATION {

    @GET("authentication/token/new?")
    Call<Auth.CreatedRequest> createRequestToken(
            @Query("api_key") String apiKey
    );

    @GET("authentication/token/validate_with_login?")
    Call<Auth.ValidatedRequest> validateRequestToken(
            @Query("api_key") String apiKey,
            @Query("username") String username,
            @Query("password") String password,
            @Query("request_token") String requestToken
    );

    @GET("authentication/session/new?")
    Call<Auth.Session> createSession(
            @Query("api_key") String apiKey,
            @Query("request_token") String requestToken
    );

    @GET("authentication/guest_session/new?")
    Call<Auth.GuestSession> createGuestSession(
            @Query("api_key") String apiKey
    );
}