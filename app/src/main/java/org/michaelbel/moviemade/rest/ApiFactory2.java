package org.michaelbel.moviemade.rest;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory2 {

    private static final String GSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final String TMDB_API_ENDPOINT = "https://api.themoviedb.org/3/";
    private static Gson GSON = new GsonBuilder().setDateFormat(GSON_DATE_FORMAT).create();

    @NonNull
    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
            .baseUrl(TMDB_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(GSON))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .build();
    }

    public static <S> S createService(Class<S> serviceClass) {
        return getRetrofit().create(serviceClass);
    }
}