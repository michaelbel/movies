package org.michaelbel.moviemade;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.google.android.gms.ads.MobileAds;

import org.michaelbel.moviemade.eventbus.RxBus;
import org.michaelbel.moviemade.eventbus.RxBus2;
import org.michaelbel.moviemade.injection.component.AppComponent;
import org.michaelbel.moviemade.injection.component.DaggerAppComponent;
import org.michaelbel.moviemade.injection.module.AppModule;

import shortbread.Shortbread;

public class Moviemade extends Application {

    public RxBus rxBus;
    public RxBus2 rxBus2;
    public static volatile Context appContext;
    public static volatile Handler appHandler;

    @Deprecated
    private static AppComponent appComponent;

    private AppComponent component;

    public static Moviemade get(Context context) {
        return (Moviemade) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        rxBus = new RxBus();
        rxBus2 = new RxBus2();
        appContext = getApplicationContext();
        appHandler = new Handler(getApplicationContext().getMainLooper());
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

        Shortbread.create(this);
        MobileAds.initialize(getApplicationContext(), getString(R.string.ad_app_id));
    }

    @Deprecated
    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public RxBus eventBus() {
        return rxBus;
    }

    public AppComponent getComponent() {
        if (component == null) {
            component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        }
        return component;
    }

    // Needed to replace the component with a test specific one.
    public void setComponent(AppComponent appComponent) {
        this.component = appComponent;
    }
}