package org.michaelbel.moviemade.presentation.di.module

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.michaelbel.moviemade.core.TmdbConfig.GSON_DATE_FORMAT
import org.michaelbel.moviemade.core.remote.*
import org.michaelbel.moviemade.presentation.features.account.AccountRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
class NetworkModule(private val context: Context, private val baseUrl: String) {

    @Provides
    @Singleton
    fun retrofit(): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient())
                .baseUrl(baseUrl)
                .build()
    }

    @Provides
    @Singleton
    fun movieService(): MoviesService = retrofit().create(MoviesService::class.java)

    @Provides
    @Singleton
    fun searchService(): SearchService = retrofit().create(SearchService::class.java)

    @Provides
    @Singleton
    fun keywordsService(): KeywordsService = retrofit().create(KeywordsService::class.java)

    @Provides
    @Singleton
    fun accountService(): AccountService = retrofit().create(AccountService::class.java)

    @Provides
    @Singleton
    fun authService(): AuthService = retrofit().create(AuthService::class.java)

    @Provides
    @Singleton
    fun accountRepository(authService: AuthService, accountService: AccountService): AccountRepository {
        return AccountRepository(authService, accountService)
    }

    private fun okHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        /*if (DEBUG) {
            okHttpClient.interceptors().add(ChuckInterceptor(context))
            okHttpClient.interceptors().add(httpLoggingInterceptor())
            okHttpClient.networkInterceptors().add(StethoInterceptor())
        }*/
        return okHttpClient.build()
    }

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor{message -> Timber.d(message)}
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    private fun gson(): Gson =
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat(GSON_DATE_FORMAT)
            .create()

    @Suppress("unused")
    inline fun <reified T> createService(): T = retrofit().create(T::class.java)
}