package org.michaelbel.movies.network.chucker

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val chuckerKoinModule = module {
    single {
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