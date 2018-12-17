package org.michaelbel.moviemade.data.injection.component;

import org.michaelbel.moviemade.data.injection.module.AppModule;
import org.michaelbel.moviemade.ui.modules.account.AccountFragment;
import org.michaelbel.moviemade.ui.modules.account.AccountPresenter;
import org.michaelbel.moviemade.ui.modules.favorites.FavoritesFragment;
import org.michaelbel.moviemade.ui.modules.favorites.FavoritesPresenter;
import org.michaelbel.moviemade.ui.modules.keywords.KeywordPresenter;
import org.michaelbel.moviemade.ui.modules.keywords.KeywordsPresenter;
import org.michaelbel.moviemade.ui.modules.main.MainActivity;
import org.michaelbel.moviemade.ui.modules.main.MainPresenter;
import org.michaelbel.moviemade.ui.modules.movie.BackdropDialog;
import org.michaelbel.moviemade.ui.modules.movie.MovieActivity;
import org.michaelbel.moviemade.ui.modules.movie.MovieFragment;
import org.michaelbel.moviemade.ui.modules.movie.MoviePresenter;
import org.michaelbel.moviemade.ui.modules.recommendations.RecommendationsMoviesFragment;
import org.michaelbel.moviemade.ui.modules.recommendations.RecommendationsMoviesPresenter;
import org.michaelbel.moviemade.ui.modules.reviews.ReviewsPresenter;
import org.michaelbel.moviemade.ui.modules.reviews.fragment.ReviewFragment;
import org.michaelbel.moviemade.ui.modules.search.SearchMoviesPresenter;
import org.michaelbel.moviemade.ui.modules.settings.SettingsFragment;
import org.michaelbel.moviemade.ui.modules.similar.SimilarMoviesFragment;
import org.michaelbel.moviemade.ui.modules.similar.SimilarMoviesPresenter;
import org.michaelbel.moviemade.ui.modules.trailers.TrailersFragment;
import org.michaelbel.moviemade.ui.modules.trailers.TrailersPresenter;
import org.michaelbel.moviemade.ui.modules.watchlist.WatchlistFragment;
import org.michaelbel.moviemade.ui.modules.watchlist.WatchlistPresenter;

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
    void injest(RecommendationsMoviesFragment fragment);
    void injest(TrailersFragment fragment);

    void injest(MainPresenter presenter);
    void injest(MoviePresenter presenter);
    void injest(ReviewsPresenter presenter);
    void injest(SearchMoviesPresenter presenter);
    void injest(AccountPresenter presenter);
    void injest(FavoritesPresenter presenter);
    void injest(KeywordsPresenter presenter);
    void injest(KeywordPresenter presenter);
    void injest(WatchlistPresenter presenter);
    void injest(SimilarMoviesPresenter presenter);
    void injest(RecommendationsMoviesPresenter presenter);

    void injest(BackdropDialog dialog);
}