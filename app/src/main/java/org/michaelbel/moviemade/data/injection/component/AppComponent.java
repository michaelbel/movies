package org.michaelbel.moviemade.data.injection.component;

import org.michaelbel.moviemade.data.injection.module.AppModule;
import org.michaelbel.moviemade.ui.modules.account.AccountFragment;
import org.michaelbel.moviemade.ui.modules.favorites.FavoritesFragment;
import org.michaelbel.moviemade.ui.modules.keywords.fragment.KeywordFragment;
import org.michaelbel.moviemade.ui.modules.keywords.fragment.KeywordsFragment;
import org.michaelbel.moviemade.ui.modules.main.MainActivity;
import org.michaelbel.moviemade.ui.modules.main.fragments.NowPlayingFragment;
import org.michaelbel.moviemade.ui.modules.main.fragments.TopRatedFragment;
import org.michaelbel.moviemade.ui.modules.main.fragments.UpcomingFragment;
import org.michaelbel.moviemade.ui.modules.movie.BackdropDialog;
import org.michaelbel.moviemade.ui.modules.movie.MovieActivity;
import org.michaelbel.moviemade.ui.modules.movie.MovieFragment;
import org.michaelbel.moviemade.ui.modules.recommendations.RcmdMoviesFragment;
import org.michaelbel.moviemade.ui.modules.reviews.fragment.ReviewFragment;
import org.michaelbel.moviemade.ui.modules.reviews.fragment.ReviewsFragment;
import org.michaelbel.moviemade.ui.modules.search.SearchMoviesFragment;
import org.michaelbel.moviemade.ui.modules.settings.SettingsFragment;
import org.michaelbel.moviemade.ui.modules.similar.SimilarMoviesFragment;
import org.michaelbel.moviemade.ui.modules.trailers.TrailersFragment;
import org.michaelbel.moviemade.ui.modules.watchlist.WatchlistFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    /*@ApplicationContext
    Context context();

    Application application();*/

    void injest(MainActivity activity);
    void injest(MovieActivity activity);

    void injest(MovieFragment fragment);
    void injest(ReviewFragment fragment);
    void injest(AccountFragment fragment);
    void injest(FavoritesFragment fragment);
    void injest(SettingsFragment fragment);
    void injest(WatchlistFragment fragment);
    void injest(SimilarMoviesFragment fragment);
    void injest(RcmdMoviesFragment fragment);
    void injest(TrailersFragment fragment);
    void injest(NowPlayingFragment fragment);
    void injest(TopRatedFragment fragment);
    void injest(UpcomingFragment fragment);
    void injest(SearchMoviesFragment fragment);
    void injest(ReviewsFragment fragment);
    void injest(KeywordFragment fragment);
    void injest(KeywordsFragment fragment);

    void injest(BackdropDialog dialog);
}