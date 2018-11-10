package org.michaelbel.moviemade.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;

@Module
public class SharedPrefsModule {

    private static final String SHARED_PREFERENCES_NAME = "mainconfig";

    private Context context;

    public SharedPrefsModule(Context context) {
        this.context = context;
    }

    @NonNull
    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
}