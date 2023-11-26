package org.michaelbel.movies

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import org.michaelbel.movies.common.config.ktx.installFirebaseApp
import org.michaelbel.movies.ui.appicon.installLauncherIcon

@HiltAndroidApp
internal class App: Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workerFactory).build()

    override fun onCreate() {
        super.onCreate()
        installLauncherIcon()
        installFirebaseApp()
    }
}