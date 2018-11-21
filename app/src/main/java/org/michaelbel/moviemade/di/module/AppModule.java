package org.michaelbel.moviemade.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.utils.SharedPrefsKt;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    private Moviemade application;

    public AppModule(Moviemade application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Moviemade provideApplication() {
        return application;
    }

    @NonNull
    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return application.getApplicationContext().getSharedPreferences(SharedPrefsKt.SP_NAME, Context.MODE_PRIVATE);
    }

    @NonNull
    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
            .baseUrl(TmdbConfigKt.TMDB_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(provideOkHttpClient())
            .build();
    }

    @NonNull
    private HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        // Logging request and response information.
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @NonNull
    private OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(provideHttpLoggingInterceptor());
        return okHttpClient.build();
    }

    @NonNull
    private Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.setDateFormat(TmdbConfigKt.GSON_DATE_FORMAT);
        return gsonBuilder.create();
    }
}