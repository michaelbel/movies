package org.michaelbel.moviemade.data.injection.module;

import android.content.Context;
import android.content.SharedPreferences;

import org.michaelbel.moviemade.data.injection.scope.ActivityScoped;
import org.michaelbel.moviemade.utils.SharedPrefsKt;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    @Provides
    @ActivityScoped
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(SharedPrefsKt.SP_NAME, Context.MODE_PRIVATE);
    }
}