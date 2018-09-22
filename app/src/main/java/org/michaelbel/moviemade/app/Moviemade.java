package org.michaelbel.moviemade.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import org.michaelbel.moviemade.app.eventbus.RxBus;

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

    public RxBus rxBus;
    public static volatile Context AppContext;
    public static volatile Handler AppHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        AppContext = getApplicationContext();
        AppHandler = new Handler(getApplicationContext().getMainLooper());

        rxBus = new RxBus();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("Moviemade10.realm").build();
        Realm.setDefaultConfiguration(config);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public RxBus eventBus() {
        return rxBus;
    }
}