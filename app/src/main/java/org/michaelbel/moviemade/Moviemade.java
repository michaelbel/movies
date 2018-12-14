package org.michaelbel.moviemade;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import org.michaelbel.moviemade.di.component.AppComponent;
import org.michaelbel.moviemade.di.component.DaggerAppComponent;
import org.michaelbel.moviemade.di.module.AppModule;
import org.michaelbel.moviemade.eventbus.RxBus;
import org.michaelbel.moviemade.eventbus.RxBus2;

import shortbread.Shortbread;

public class Moviemade extends Application {

    public RxBus rxBus;
    public RxBus2 rxBus2;
    public static volatile Context appContext;
    public static volatile Handler appHandler;
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        rxBus = new RxBus();
        rxBus2 = new RxBus2();
        appContext = getApplicationContext();
        appHandler = new Handler(getApplicationContext().getMainLooper());
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

        Shortbread.create(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static AppComponent getComponent() {
        return appComponent;
    }

    public RxBus eventBus() {
        return rxBus;
    }
}