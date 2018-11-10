package org.michaelbel.moviemade.di.module;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RestModule {

    private static final String GSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final String TMDB_API_ENDPOINT = "https://api.themoviedb.org/3/";

    @NonNull
    @Provides
    @Singleton
    OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        return builder.build();
    }

    @NonNull
    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
            .baseUrl(TMDB_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient())
            .build();
    }

    @NonNull
    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.setDateFormat(GSON_DATE_FORMAT);
        return gsonBuilder.create();
    }
}