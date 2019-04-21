package org.michaelbel.moviemade.presentation

import android.app.Application
import android.content.Context
import android.os.Handler
import android.util.Log
import com.facebook.stetho.Stetho
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
import shortbread.Shortbread
import timber.log.Timber

class App: Application() {

    companion object {
        private const val TAG = "2580"

        //private GoogleAnalytics googleAnalytics;
        //private Tracker tracker;

        operator fun get(context: Context): App {
            return context as App
        }

        lateinit var appHandler: Handler
        lateinit var appContext: Context

        @JvmStatic fun d(msg: String) {
            Log.e(TAG, msg)
        }
    }

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appHandler = Handler(applicationContext.mainLooper)
        appContext = applicationContext

        Shortbread.create(this)
        Stetho.initializeWithDefaults(this)

        if (DEBUG) {
            //Traceur.enableLogging()
            //Sherlock.init(this)
            //LeakCanary.install(this)
            Timber.plant(Timber.DebugTree())
            Timber.tag(TAG)
            Stetho.initializeWithDefaults(this)
        }

        initDI()

        //MobileAds.initialize(getApplicationContext(), getString(R.string.ad_app_id));
        //getTracker().send(new HitBuilders.EventBuilder().setCategory("Device Name").setAction(DeviceUtil.INSTANCE.getDeviceName()).build());
    }

    private fun initDI() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule(this, TMDB_API_ENDPOINT))
                .build()
    }

    fun createActivityComponent(): ActivityComponent = appComponent.plus(ActivityModule())

    fun createFragmentComponent(): FragmentComponent = appComponent.plus(FragmentModule())

    /*private GoogleAnalytics getGoogleAnalytics() {
        if (googleAnalytics == null) {
            googleAnalytics = GoogleAnalytics.getInstance(getApplicationContext());
        }
        return googleAnalytics;
    }

    synchronized public Tracker getTracker() {
        if (tracker == null) {
            tracker = getGoogleAnalytics().newTracker(R.xml.analytics);
        }
        return tracker;
    }*/
}