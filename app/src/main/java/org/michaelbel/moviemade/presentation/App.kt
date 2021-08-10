package org.michaelbel.moviemade.presentation

import android.app.Application
import android.content.Context
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import com.facebook.stetho.Stetho
import com.singhajit.sherlock.core.Sherlock
import com.tspoon.traceur.Traceur
import org.michaelbel.core.BuildConfig
import org.michaelbel.core.CrashlyticsTree
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
    }

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        appHandler = Handler(applicationContext.mainLooper)
        initDI()
        setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        //Analytics.initialize(this)
        Stetho.initializeWithDefaults(this)

        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else CrashlyticsTree())
        Timber.tag(TAG)

        if (DEBUG) {
            Sherlock.init(this)
            Traceur.enableLogging()
            //LeakCanary.install(this)
        }


    }

    private fun initDI() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule(this, TMDB_API_ENDPOINT))
                .build()
    }

    val createActivityComponent: ActivityComponent
        get() = appComponent.plus(ActivityModule())

    val createFragmentComponent: FragmentComponent
        get() = appComponent.plus(FragmentModule())
}