package org.michaelbel.moviemade.data.injection.component;

import org.michaelbel.moviemade.data.injection.module.ActivityModule;
import org.michaelbel.moviemade.data.injection.module.AppModule;
import org.michaelbel.moviemade.data.injection.module.FragmentModule;
import org.michaelbel.moviemade.data.injection.module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    ActivityComponent plus(ActivityModule activityModule);
    FragmentComponent plus(FragmentModule fragmentModule);
}