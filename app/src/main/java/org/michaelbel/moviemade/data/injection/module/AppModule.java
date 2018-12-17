package org.michaelbel.moviemade.data.injection.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.michaelbel.moviemade.data.service.AccountService;
import org.michaelbel.moviemade.data.service.AuthService;
import org.michaelbel.moviemade.data.service.KeywordsService;
import org.michaelbel.moviemade.data.service.MoviesService;
import org.michaelbel.moviemade.data.service.SearchService;
import org.michaelbel.moviemade.data.injection.ApplicationContext;
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

@Module(includes = {ApiModule.class})
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return application.getSharedPreferences(SharedPrefsKt.SP_NAME, Context.MODE_PRIVATE);
    }

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

//--ApiModule---------------------------------------------------------------------------------------

    @Provides
    @Singleton
    MoviesService provideMoviesApi() {
        return provideRetrofit().create(MoviesService.class);
    }

    @Provides
    @Singleton
    KeywordsService provideKeywordsApi() {
        return provideRetrofit().create(KeywordsService.class);
    }

    @Provides
    @Singleton
    AccountService provideAccountApi() {
        return provideRetrofit().create(AccountService.class);
    }

    @Provides
    @Singleton
    SearchService provideSearchApi() {
        return provideRetrofit().create(SearchService.class);
    }

    @Provides
    @Singleton
    AuthService provideAuthApi() {
        return provideRetrofit().create(AuthService.class);
    }
}