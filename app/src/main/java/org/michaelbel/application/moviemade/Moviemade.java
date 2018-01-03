package org.michaelbel.application.moviemade;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import org.michaelbel.application.moviemade.eventbus.RxBus;

import io.realm.Realm;
import io.realm.RealmConfiguration;

@SuppressWarnings("all")
public class Moviemade extends Application {

    public static final String EMAIL = "michael-bel@outlook.com";
    public static final String TELEGRAM_URL = "https://t.me/michaelbel";
    public static final String GITHUB_URL = "https://github.com/michaelbel/moviemade";
    public static final String ACCOUNT_WEB = "https://play.google.com/store/dev?id=michaelbel24865";
    public static final String ACCOUNT_MARKET = "market://dev?id=michaelbel24865";
    public static final String APP_WEB = "https://play.google.com/store/details?id=org.michaelbel.moviemade";
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
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("MoviemadeDb4.realm")
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public RxBus eventBus() {
        return rxBus;
    }
}