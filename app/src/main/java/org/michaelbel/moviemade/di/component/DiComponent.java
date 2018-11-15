package org.michaelbel.moviemade.di.component;

import org.michaelbel.moviemade.di.module.RestModule;
import org.michaelbel.moviemade.di.module.RoomModule;
import org.michaelbel.moviemade.di.module.SharedPrefsModule;
import org.michaelbel.moviemade.ui.modules.movie.MoviePresenter;
import org.michaelbel.moviemade.ui.modules.main.MainActivity;
import org.michaelbel.moviemade.ui.modules.movie.MovieFragment;
import org.michaelbel.moviemade.ui.modules.main.fragments.NowPlayingFragment;
import org.michaelbel.moviemade.ui.modules.reviews.activity.ReviewActivity;
import org.michaelbel.moviemade.ui.modules.reviews.fragment.ReviewFragment;
import org.michaelbel.moviemade.ui.modules.settings.SettingsFragment;
import org.michaelbel.moviemade.ui.modules.main.fragments.TopRatedFragment;
import org.michaelbel.moviemade.ui.modules.main.fragments.UpcomingFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    RestModule.class,
    SharedPrefsModule.class,
    RoomModule.class
})
public interface DiComponent {
    void injest(MainActivity activity);
    void injest(ReviewActivity activity);
    void injest(SettingsFragment fragment);
    void injest(NowPlayingFragment fragment);
    void injest(TopRatedFragment fragment);
    void injest(UpcomingFragment fragment);
    void injest(MovieFragment fragment);
    void injest(MoviePresenter fragment);
    void injest(ReviewFragment fragment);
}