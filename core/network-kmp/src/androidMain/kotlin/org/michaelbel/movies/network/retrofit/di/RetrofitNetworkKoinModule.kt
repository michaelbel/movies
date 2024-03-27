package org.michaelbel.movies.network.retrofit.di

import org.koin.dsl.module
import org.michaelbel.movies.network.retrofit.RetrofitAccountService
import org.michaelbel.movies.network.retrofit.RetrofitAuthenticationService
import org.michaelbel.movies.network.retrofit.RetrofitMovieService
import org.michaelbel.movies.network.retrofit.RetrofitSearchService
import org.michaelbel.movies.network.retrofit.ktx.createService
import retrofit2.Retrofit

val retrofitNetworkKoinModule = module {
    includes(
        retrofitKoinModule
    )
    single { get<Retrofit>().createService<RetrofitAuthenticationService>() }
    single { get<Retrofit>().createService<RetrofitAccountService>() }
    single { get<Retrofit>().createService<RetrofitMovieService>() }
    single { get<Retrofit>().createService<RetrofitSearchService>() }
}