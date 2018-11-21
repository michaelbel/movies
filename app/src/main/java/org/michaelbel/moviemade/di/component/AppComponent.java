package org.michaelbel.moviemade.di.component;

import org.michaelbel.moviemade.di.module.AppModule;
import org.michaelbel.moviemade.ui.modules.account.AccountFragment;
import org.michaelbel.moviemade.ui.modules.account.AccountPresenter;
import org.michaelbel.moviemade.ui.modules.favorite.FaveFragment;
import org.michaelbel.moviemade.ui.modules.favorite.FavePresenter;
import org.michaelbel.moviemade.ui.modules.main.MainActivity;
import org.michaelbel.moviemade.ui.modules.main.MainPresenter;
import org.michaelbel.moviemade.ui.modules.movie.MovieFragment;
import org.michaelbel.moviemade.ui.modules.movie.MoviePresenter;
import org.michaelbel.moviemade.ui.modules.reviews.ReviewsPresenter;
import org.michaelbel.moviemade.ui.modules.reviews.fragment.ReviewFragment;
import org.michaelbel.moviemade.ui.modules.search.SearchMoviesPresenter;
import org.michaelbel.moviemade.ui.modules.settings.SettingsFragment;
import org.michaelbel.moviemade.ui.modules.trailers.TrailersPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void injest(MainActivity activity);

    void injest(MovieFragment fragment);
    void injest(ReviewFragment fragment);
    void injest(AccountFragment fragment);
    void injest(FaveFragment fragment);
    void injest(SettingsFragment fragment);

    void injest(MainPresenter presenter);
    void injest(MoviePresenter presenter);
    void injest(ReviewsPresenter presenter);
    void injest(TrailersPresenter presenter);
    void injest(SearchMoviesPresenter presenter);
    void injest(AccountPresenter presenter);
    void injest(FavePresenter presenter);
}