package org.michaelbel.moviemade;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import org.michaelbel.moviemade.di.component.DaggerDiComponent;
import org.michaelbel.moviemade.di.component.DiComponent;
import org.michaelbel.moviemade.di.module.RestModule;
import org.michaelbel.moviemade.di.module.RoomModule;
import org.michaelbel.moviemade.di.module.SharedPrefsModule;
import org.michaelbel.moviemade.eventbus.RxBus;

public class Moviemade extends Application {

    public RxBus rxBus;
    public static volatile Context AppContext;
    public static volatile Handler AppHandler;

    private static DiComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        rxBus = new RxBus();
        AppContext = getApplicationContext();
        AppHandler = new Handler(getApplicationContext().getMainLooper());

        component = DaggerDiComponent.builder()
            .restModule(new RestModule())
            .sharedPrefsModule(new SharedPrefsModule(AppContext))
            .roomModule(new RoomModule(AppContext))
            .build();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static DiComponent getComponent() {
        return component;
    }

    public RxBus eventBus() {
        return rxBus;
    }
}