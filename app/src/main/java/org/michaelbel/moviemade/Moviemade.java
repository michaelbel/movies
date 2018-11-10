package org.michaelbel.moviemade;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import org.michaelbel.moviemade.di.component.DaggerDiComponent;
import org.michaelbel.moviemade.di.component.DiComponent;
import org.michaelbel.moviemade.di.module.RestModule;
import org.michaelbel.moviemade.di.module.SharedPrefsModule;
import org.michaelbel.moviemade.eventbus.RxBus;
import org.michaelbel.moviemade.realm.MoviemadeMigration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Moviemade extends Application {

    public static final String EMAIL = "michael-bel@outlook.com";
    public static final String TELEGRAM_URL = "https://t.me/michaelbel";
    public static final String PAYPAL_ME = "https://paypal.me/michaelbel";
    public static final String GITHUB_URL = "https://github.com/michaelbel/moviemade";
    public static final String ACCOUNT_WEB = "https://play.google.com/store/apps/developer?id=Michael+Bel";
    public static final String ACCOUNT_MARKET = "market://developer?id=Michael+Bel";
    public static final String APP_WEB = "https://play.google.com/store/apps/details?id=org.michaelbel.moviemade";
    public static final String APP_MARKET = "market://details?id=org.michaelbel.moviemade";

    private static final String REALM_NAME = "moviemadeDb.realm";

    public RxBus rxBus;
    public static volatile Context AppContext;
    public static volatile Handler AppHandler;

    private static DiComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        AppContext = getApplicationContext();
        AppHandler = new Handler(getApplicationContext().getMainLooper());

        rxBus = new RxBus();

        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
            .name(REALM_NAME)
            .schemaVersion(0)
            .migration(new MoviemadeMigration())
            .build();
        Realm.setDefaultConfiguration(config);

        component = DaggerDiComponent.builder()
            .restModule(new RestModule())
            .sharedPrefsModule(new SharedPrefsModule(AppContext))
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