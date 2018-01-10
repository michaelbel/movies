package org.michaelbel.moviemade.app;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static Gson GSON = new GsonBuilder().setDateFormat(Url.GSON_DATE_FORMAT).create();

    @NonNull
    public static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.TMDB_API_V3)
                .addConverterFactory(GsonConverterFactory.create(GSON))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit;
    }
}