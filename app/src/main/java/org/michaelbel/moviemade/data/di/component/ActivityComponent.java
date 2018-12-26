package org.michaelbel.moviemade.data.di.component;

import org.michaelbel.moviemade.data.di.module.ActivityModule;
import org.michaelbel.moviemade.data.di.scope.ActivityScoped;
import org.michaelbel.moviemade.ui.modules.main.MainActivity;
import org.michaelbel.moviemade.ui.modules.movie.MovieActivity;
import org.michaelbel.moviemade.ui.modules.reviews.activity.ReviewActivity;

import dagger.Subcomponent;

@ActivityScoped
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity target);
    void inject(MovieActivity target);
    void inject(ReviewActivity target);
}