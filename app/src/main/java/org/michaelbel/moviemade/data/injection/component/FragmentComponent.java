package org.michaelbel.moviemade.data.injection.component;

import org.michaelbel.moviemade.data.injection.module.FragmentModule;
import org.michaelbel.moviemade.data.injection.scope.FragmentScoped;
import org.michaelbel.moviemade.ui.modules.account.AccountFragment;
import org.michaelbel.moviemade.ui.modules.favorites.FavoritesFragment;
import org.michaelbel.moviemade.ui.modules.keywords.fragment.KeywordFragment;
import org.michaelbel.moviemade.ui.modules.keywords.fragment.KeywordsFragment;
import org.michaelbel.moviemade.ui.modules.main.fragments.NowPlayingFragment;
import org.michaelbel.moviemade.ui.modules.main.fragments.TopRatedFragment;
import org.michaelbel.moviemade.ui.modules.main.fragments.UpcomingFragment;
import org.michaelbel.moviemade.ui.modules.movie.BackdropDialog;
import org.michaelbel.moviemade.ui.modules.movie.MovieFragment;
import org.michaelbel.moviemade.ui.modules.recommendations.RcmdMoviesFragment;
import org.michaelbel.moviemade.ui.modules.reviews.fragment.ReviewFragment;
import org.michaelbel.moviemade.ui.modules.reviews.fragment.ReviewsFragment;
import org.michaelbel.moviemade.ui.modules.search.SearchMoviesFragment;
import org.michaelbel.moviemade.ui.modules.settings.SettingsFragment;
import org.michaelbel.moviemade.ui.modules.similar.SimilarMoviesFragment;
import org.michaelbel.moviemade.ui.modules.trailers.TrailersFragment;
import org.michaelbel.moviemade.ui.modules.watchlist.WatchlistFragment;

import dagger.Subcomponent;

@FragmentScoped
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(MovieFragment fragment);
    void inject(ReviewFragment fragment);
    void inject(AccountFragment fragment);
    void inject(FavoritesFragment fragment);
    void inject(SettingsFragment fragment);
    void inject(WatchlistFragment fragment);
    void inject(SimilarMoviesFragment fragment);
    void inject(RcmdMoviesFragment fragment);
    void inject(TrailersFragment fragment);
    void inject(NowPlayingFragment fragment);
    void inject(TopRatedFragment fragment);
    void inject(UpcomingFragment fragment);
    void inject(SearchMoviesFragment fragment);
    void inject(ReviewsFragment fragment);
    void inject(KeywordFragment fragment);
    void inject(KeywordsFragment fragment);
    void inject(BackdropDialog target);
}