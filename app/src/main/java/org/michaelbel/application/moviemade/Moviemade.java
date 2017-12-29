package org.michaelbel.application.moviemade;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import io.realm.Realm;
import io.realm.RealmConfiguration;

@SuppressWarnings("all")
public class Moviemade extends Application {

    public static final String EMAIL = "michael-bel@outlook.com";
    public static final String TELEGRAM_URL = "https://t.me/michaelbel";
    public static final String GITHUB_URL = "https://github.com/michaelbel/moviemade";

    public static volatile Context AppContext;
    public static volatile Handler AppHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        AppContext = getApplicationContext();
        AppHandler = new Handler(getApplicationContext().getMainLooper());

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("MoviemadeDb.realm")
                .build();
        Realm.setDefaultConfiguration(config);
    }
}