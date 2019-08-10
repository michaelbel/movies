package org.michaelbel.moviemade.presentation

import android.app.Application
import android.content.Context
import android.os.Handler
import android.util.Log
import com.facebook.stetho.Stetho
import com.singhajit.sherlock.core.Sherlock
import com.tspoon.traceur.Traceur
import org.michaelbel.core.analytics.Analytics
import org.michaelbel.moviemade.BuildConfig.DEBUG
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_API_ENDPOINT
import org.michaelbel.moviemade.presentation.di.component.ActivityComponent
import org.michaelbel.moviemade.presentation.di.component.AppComponent
import org.michaelbel.moviemade.presentation.di.component.DaggerAppComponent
import org.michaelbel.moviemade.presentation.di.component.FragmentComponent
import org.michaelbel.moviemade.presentation.di.module.ActivityModule
import org.michaelbel.moviemade.presentation.di.module.AppModule
import org.michaelbel.moviemade.presentation.di.module.FragmentModule
import org.michaelbel.moviemade.presentation.di.module.NetworkModule
import timber.log.Timber

class App: Application() {

    companion object {
        private const val TAG = "2580"

        operator fun get(context: Context): App {
            return context as App
        }

        lateinit var appHandler: Handler
        lateinit var appContext: Context

        @JvmStatic fun d(msg: String) {
            Log.d(TAG, msg)
        }

        @JvmStatic fun e(msg: String) {
            Log.e(TAG, msg)
        }
    }

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        appHandler = Handler(applicationContext.mainLooper)

        Analytics.initialize(this)
        Stetho.initializeWithDefaults(this)

        Timber.plant(Timber.DebugTree())
        Timber.tag(TAG)

        if (DEBUG) {
            Sherlock.init(this)
            Traceur.enableLogging()
            //LeakCanary.install(this)
        }

        initDI()
    }

    private fun initDI() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule(this, TMDB_API_ENDPOINT))
                .build()
    }

    fun createActivityComponent(): ActivityComponent = appComponent.plus(ActivityModule())

    fun createFragmentComponent(): FragmentComponent = appComponent.plus(FragmentModule())
}