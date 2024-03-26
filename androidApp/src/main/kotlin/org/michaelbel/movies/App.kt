package org.michaelbel.movies

import android.app.Application
import androidx.work.Configuration
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.factory.KoinWorkerFactory
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import org.michaelbel.movies.common.crashlytics.CrashlyticsTree
import org.michaelbel.movies.common_kmp.BuildConfig
import org.michaelbel.movies.di.appKoinModule
import org.michaelbel.movies.platform.app.AppService
import org.michaelbel.movies.platform.crashlytics.CrashlyticsService
import org.michaelbel.movies.ui.appicon.installLauncherIcon
import timber.log.Timber

internal class App: Application(), Configuration.Provider {

    private val workerFactory: KoinWorkerFactory by inject()
    private val appService: AppService by inject()
    private val crashlyticsService: CrashlyticsService by inject()

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workerFactory).build()

    override fun onCreate() {
        super.onCreate()
        installLauncherIcon()
        startKoin {
            androidLogger()
            androidContext(this@App)
            workManagerFactory()
            modules(appKoinModule)
        }
        appService.installApp()
        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else CrashlyticsTree(crashlyticsService))
    }
}