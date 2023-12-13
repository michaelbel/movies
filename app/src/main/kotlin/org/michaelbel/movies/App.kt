package org.michaelbel.movies

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import org.michaelbel.movies.common.BuildConfig
import org.michaelbel.movies.common.crashlytics.CrashlyticsTree
import org.michaelbel.movies.platform.app.AppService
import org.michaelbel.movies.platform.crashlytics.CrashlyticsService
import org.michaelbel.movies.ui.appicon.installLauncherIcon
import timber.log.Timber

@HiltAndroidApp
internal class App: Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory
    @Inject lateinit var appService: AppService
    @Inject lateinit var crashlyticsService: CrashlyticsService

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workerFactory).build()

    override fun onCreate() {
        super.onCreate()
        installLauncherIcon()
        appService.installApp()
        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else CrashlyticsTree(crashlyticsService))
    }
}