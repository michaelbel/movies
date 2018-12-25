package org.michaelbel.moviemade.data.injection.module;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.michaelbel.moviemade.data.service.AccountService;
import org.michaelbel.moviemade.data.service.AuthService;
import org.michaelbel.moviemade.data.service.MoviesService;
import org.michaelbel.moviemade.ui.modules.account.AccountRepository;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    Retrofit retrofit() {
        return new Retrofit.Builder()
            .baseUrl(TmdbConfigKt.TMDB_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient())
            .build();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor httpLoggingInterceptor() {
        // Logging request and response information.
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    OkHttpClient okHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(httpLoggingInterceptor());
        return okHttpClient.build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.setDateFormat(TmdbConfigKt.GSON_DATE_FORMAT);
        return gsonBuilder.create();
    }

//--Services----------------------------------------------------------------------------------------

    @Provides
    @Singleton
    MoviesService provideMoviesApi() {
        return retrofit().create(MoviesService.class);
    }

    @Provides
    @Singleton
    AccountService provideAccountApi() {
        return retrofit().create(AccountService.class);
    }

//--Repositories------------------------------------------------------------------------------------

    @Provides
    @Singleton
    AccountRepository provideAccountRepository() {
        return new AccountRepository(retrofit().create(AuthService.class), retrofit().create(AccountService.class));
    }
}