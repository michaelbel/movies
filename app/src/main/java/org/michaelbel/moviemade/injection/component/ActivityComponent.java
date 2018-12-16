package org.michaelbel.moviemade.injection.component;

import org.michaelbel.moviemade.injection.PerActivity;
import org.michaelbel.moviemade.injection.module.ActivityModule;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    // oid inject(MainActivity activity);
}