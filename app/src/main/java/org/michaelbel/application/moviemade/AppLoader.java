package org.michaelbel.application.moviemade;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

@SuppressWarnings("all")
public class AppLoader extends Application {

    public static volatile Context AppContext;
    public static volatile Handler AppHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        AppContext = getApplicationContext();
        AppHandler = new Handler(getApplicationContext().getMainLooper());
    }
}