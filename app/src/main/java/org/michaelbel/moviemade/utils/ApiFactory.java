package org.michaelbel.moviemade.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.internal.http.CallServerInterceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static final String GSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final String TMDB_API_ENDPOINT = "https://api.themoviedb.org/3/";
    private static Gson GSON = new GsonBuilder().setDateFormat(GSON_DATE_FORMAT).create();

    private static OkHttpClient okHttpClient() {
        // to show request and response information.
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(httpLoggingInterceptor);

        return okHttpClient.build();
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
}