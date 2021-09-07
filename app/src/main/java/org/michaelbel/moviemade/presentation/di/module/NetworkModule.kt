package org.michaelbel.moviemade.presentation.di.module

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.michaelbel.data.remote.Api
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.core.TmdbConfig
import org.michaelbel.moviemade.core.TmdbConfig.GSON_DATE_FORMAT
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun retrofit(@ApplicationContext context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(TmdbConfig.TMDB_API_ENDPOINT)
            .client(okHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun apiService(@ApplicationContext context: Context): Api = retrofit(context).create(Api::class.java)

    private fun okHttpClient(context: Context): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        if (BuildConfig.DEBUG) {
            okHttpClient.interceptors().add(ChuckInterceptor(context))
            okHttpClient.interceptors().add(httpLoggingInterceptor)
            okHttpClient.networkInterceptors().add(StethoInterceptor())
        }
        return okHttpClient.build()
    }

    private val httpLoggingInterceptor: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor { message -> Timber.d(message) }
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return httpLoggingInterceptor
        }

    private val gson: Gson =
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat(GSON_DATE_FORMAT)
            .create()

    @Suppress("unused")
    inline fun <reified T> createService(context: Context): T = retrofit(context).create(T::class.java)
}