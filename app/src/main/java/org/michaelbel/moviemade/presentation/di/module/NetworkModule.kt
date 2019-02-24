package org.michaelbel.moviemade.presentation.di.module

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.michaelbel.moviemade.BuildConfig.DEBUG
import org.michaelbel.moviemade.core.remote.AccountService
import org.michaelbel.moviemade.core.remote.AuthService
import org.michaelbel.moviemade.core.remote.MoviesService
import org.michaelbel.moviemade.core.utils.GSON_DATE_FORMAT
import org.michaelbel.moviemade.presentation.features.account.AccountRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
class NetworkModule(private val context: Context, private val baseUrl: String) {

    @Singleton
    @Provides
    fun retrofit(): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient())
                .baseUrl(baseUrl)
                .build()
    }

    @Singleton
    @Provides
    fun movieService(): MoviesService {
        return retrofit().create(MoviesService::class.java)
    }

    @Singleton
    @Provides
    fun accountService(): AccountService {
        return retrofit().create(AccountService::class.java)
    }

    @Singleton
    @Provides
    fun authService(): AuthService {
        return retrofit().create(AuthService::class.java)
    }

    @Singleton
    @Provides
    fun accountRepository(authService: AuthService, accountService: AccountService): AccountRepository {
        return AccountRepository(authService, accountService)
    }

    private fun okHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        if (DEBUG) {
            okHttpClient.interceptors().add(ChuckInterceptor(context))
            okHttpClient.interceptors().add(httpLoggingInterceptor())
            okHttpClient.networkInterceptors().add(StethoInterceptor())
        }
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