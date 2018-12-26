package org.michaelbel.moviemade.data.di.component;

import org.michaelbel.moviemade.data.di.module.ActivityModule;
import org.michaelbel.moviemade.data.di.module.AppModule;
import org.michaelbel.moviemade.data.di.module.FragmentModule;
import org.michaelbel.moviemade.data.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    ActivityComponent plus(ActivityModule target);
    FragmentComponent plus(FragmentModule target);
}