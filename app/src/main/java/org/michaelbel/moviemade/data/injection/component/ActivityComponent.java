package org.michaelbel.moviemade.data.injection.component;

import org.michaelbel.moviemade.data.injection.PerActivity;
import org.michaelbel.moviemade.data.injection.module.ActivityModule;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    // oid inject(MainActivity activity);
}