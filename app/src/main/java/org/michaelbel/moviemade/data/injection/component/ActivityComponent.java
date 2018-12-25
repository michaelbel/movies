package org.michaelbel.moviemade.data.injection.component;

import org.michaelbel.moviemade.data.injection.module.ActivityModule;
import org.michaelbel.moviemade.data.injection.scope.ActivityScoped;
import org.michaelbel.moviemade.ui.modules.main.MainActivity;
import org.michaelbel.moviemade.ui.modules.movie.MovieActivity;

import dagger.Subcomponent;

@ActivityScoped
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity target);
    void inject(MovieActivity target);
}