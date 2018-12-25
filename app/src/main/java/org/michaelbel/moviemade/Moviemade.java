package org.michaelbel.moviemade;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.michaelbel.moviemade.data.eventbus.RxBus;
import org.michaelbel.moviemade.data.injection.component.ActivityComponent;
import org.michaelbel.moviemade.data.injection.component.AppComponent;
import org.michaelbel.moviemade.data.injection.component.DaggerAppComponent;
import org.michaelbel.moviemade.data.injection.component.FragmentComponent;
import org.michaelbel.moviemade.data.injection.module.ActivityModule;
import org.michaelbel.moviemade.data.injection.module.AppModule;
import org.michaelbel.moviemade.data.injection.module.FragmentModule;
import org.michaelbel.moviemade.data.injection.module.NetworkModule;
import org.michaelbel.moviemade.utils.DeviceUtil;

import shortbread.Shortbread;

@SuppressWarnings("unused")
public class Moviemade extends Application {

    public RxBus rxBus;
    public static volatile Context appContext;
    public static volatile Handler appHandler;

    private AppComponent component;
    private ActivityComponent activityComponent;
    private FragmentComponent fragmentComponent;

    private GoogleAnalytics googleAnalytics;
    private Tracker tracker;

    public static Moviemade get(Context context) {
        return (Moviemade) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        rxBus = new RxBus();
        appContext = getApplicationContext();
        appHandler = new Handler(getApplicationContext().getMainLooper());

        Shortbread.create(this);
        MobileAds.initialize(getApplicationContext(), getString(R.string.ad_app_id));

        getTracker().send(new HitBuilders.EventBuilder().setCategory("Device Name").setAction(DeviceUtil.INSTANCE.getDeviceName()).build());
    }

    public RxBus eventBus() {
        return rxBus;
    }

    public AppComponent getComponent() {
        if (component == null) {
            component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
        }
        return component;
    }

    public ActivityComponent getActivityComponent() {
        activityComponent = getComponent().plus(new ActivityModule());
        return activityComponent;
    }

    public FragmentComponent getFragmentComponent() {
        fragmentComponent = getComponent().plus(new FragmentModule(this));
        return fragmentComponent;
    }

    private GoogleAnalytics getGoogleAnalytics() {
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
    }

    // Needed to replace the component with a test specific one.
    public void setComponent(AppComponent appComponent) {
        this.component = appComponent;
    }
}