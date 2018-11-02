package org.michaelbel.moviemade.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static final String GSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final String TMDB_API_ENDPOINT = "https://api.themoviedb.org/3/";
    private static Gson GSON = new GsonBuilder().setDateFormat(GSON_DATE_FORMAT).create();

    private static OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        return builder.build();
    }

    @NonNull
    private static Retrofit getRetrofit2() {
        return new Retrofit.Builder()
                .baseUrl(TMDB_API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(GSON))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient())
                .build();
    }

    public static <S> S createService2(Class<S> serviceClass) {
        return getRetrofit2().create(serviceClass);
    }

    /*@NonNull
    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
            .baseUrl(TMDB_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(GSON)) // todo Change date!
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .build();
    }*/

    /*public static <S> S createService(Class<S> serviceClass) {
        return getRetrofit().create(serviceClass);
    }*/
}