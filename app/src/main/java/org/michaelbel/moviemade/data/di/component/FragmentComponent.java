package org.michaelbel.moviemade.data.di.component;

import org.michaelbel.moviemade.data.di.module.FragmentModule;
import org.michaelbel.moviemade.data.di.scope.FragmentScoped;
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
    void inject(MovieFragment target);
    void inject(ReviewFragment target);
    void inject(AccountFragment target);
    void inject(FavoritesFragment target);
    void inject(SettingsFragment target);
    void inject(WatchlistFragment target);
    void inject(SimilarMoviesFragment target);
    void inject(RcmdMoviesFragment target);
    void inject(TrailersFragment target);
    void inject(NowPlayingFragment target);
    void inject(TopRatedFragment target);
    void inject(UpcomingFragment target);
    void inject(SearchMoviesFragment target);
    void inject(ReviewsFragment target);
    void inject(KeywordFragment target);
    void inject(KeywordsFragment target);
    void inject(BackdropDialog target);
}