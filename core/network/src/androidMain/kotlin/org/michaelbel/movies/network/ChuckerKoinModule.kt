package org.michaelbel.movies.network

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val chuckerKoinModule = module {
    single<ChuckerInterceptor> {
        val chuckerCollector = ChuckerCollector(
            context = androidContext(),
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
        val maxContentLength = 250_000L
        ChuckerInterceptor.Builder(androidContext())
            .collector(chuckerCollector)
            .maxContentLength(maxContentLength)
            .alwaysReadResponseBody(true)
            .build()
    }
}