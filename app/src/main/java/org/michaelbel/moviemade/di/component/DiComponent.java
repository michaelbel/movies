package org.michaelbel.moviemade.di.component;

import org.michaelbel.moviemade.di.module.RestModule;
import org.michaelbel.moviemade.di.module.SharedPrefsModule;
import org.michaelbel.moviemade.mvp.presenter.MoviePresenter;
import org.michaelbel.moviemade.ui.activity.MainActivity;
import org.michaelbel.moviemade.ui.fragment.NowPlayingFragment;
import org.michaelbel.moviemade.ui.fragment.SettingsFragment;
import org.michaelbel.moviemade.ui.fragment.TopRatedFragment;
import org.michaelbel.moviemade.ui.fragment.UpcomingFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    RestModule.class,
    SharedPrefsModule.class
})
public interface DiComponent {

    void injest(MainActivity activity);
    void injest(SettingsFragment fragment);
    void injest(NowPlayingFragment fragment);
    void injest(TopRatedFragment fragment);
    void injest(UpcomingFragment fragment);

    void injest(MoviePresenter fragment);
}