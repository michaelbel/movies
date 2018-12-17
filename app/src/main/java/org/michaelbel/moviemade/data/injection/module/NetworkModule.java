package org.michaelbel.moviemade.data.injection.module;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.michaelbel.moviemade.utils.TmdbConfigKt;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.setDateFormat(TmdbConfigKt.GSON_DATE_FORMAT);
        return gsonBuilder.create();
    }

    /*@Provides
    @Singleton
    Retrofit provideRetrofit2(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
    }*/

    /*@Provides
    @Singleton
    OkHttpClient provideOkHttpClient2(HttpLoggingInterceptor httpLoggingInterceptor*//*, StethoInterceptor stethoInterceptor, ChuckInterceptor chuckInterceptor*//*) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            //httpClientBuilder.addInterceptor(chuckInterceptor);
            httpClientBuilder.addInterceptor(httpLoggingInterceptor);
            //httpClientBuilder.addNetworkInterceptor(stethoInterceptor);
        }
        return httpClientBuilder.build();
    }*/

    /*@Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor2() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(*//*message -> Timber.d(message)*//*);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }*/

    /*@Provides
    @Singleton
    StethoInterceptor provideStethoInterceptor() {
        return new StethoInterceptor();
    }*/

    /*@Provides
    @Singleton
    ChuckInterceptor provideChuckInterceptor() {
        return new ChuckInterceptor(context);
    }*/

    /*@Provides
    @Singleton
    Gson provideGson2() {
        return new GsonBuilder()
            .setDateFormat(TmdbConfigKt.GSON_DATE_FORMAT)
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
    }*/
}