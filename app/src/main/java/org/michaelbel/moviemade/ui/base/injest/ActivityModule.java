package org.michaelbel.moviemade.ui.base.injest;

import android.content.Context;

import org.michaelbel.moviemade.ui.base.BaseActivity;

import javax.inject.Singleton;

import androidx.fragment.app.FragmentManager;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private BaseActivity baseActivity;

    public ActivityModule(BaseActivity activity) {
        baseActivity = activity;
    }

    @Provides
    @Singleton
    public BaseActivity provideActivity(){
        return baseActivity;
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return baseActivity;
    }

    @Provides
    @Singleton
    public FragmentManager provideFragmentManager(){
        return baseActivity.getSupportFragmentManager();
    }
}