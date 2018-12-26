package org.michaelbel.moviemade.data.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import org.michaelbel.moviemade.data.di.scope.FragmentScoped;
import org.michaelbel.moviemade.data.service.AccountService;
import org.michaelbel.moviemade.data.service.KeywordsService;
import org.michaelbel.moviemade.data.service.MoviesService;
import org.michaelbel.moviemade.data.service.SearchService;
import org.michaelbel.moviemade.ui.modules.favorites.FavoriteRepository;
import org.michaelbel.moviemade.ui.modules.favorites.FavoritesContract;
import org.michaelbel.moviemade.ui.modules.favorites.FavoritesPresenter;
import org.michaelbel.moviemade.ui.modules.keywords.KeywordContract;
import org.michaelbel.moviemade.ui.modules.keywords.KeywordPresenter;
import org.michaelbel.moviemade.ui.modules.keywords.KeywordRepository;
import org.michaelbel.moviemade.ui.modules.keywords.KeywordsContract;
import org.michaelbel.moviemade.ui.modules.keywords.KeywordsPresenter;
import org.michaelbel.moviemade.ui.modules.keywords.KeywordsRepository;
import org.michaelbel.moviemade.ui.modules.main.MainContract;
import org.michaelbel.moviemade.ui.modules.main.MainPresenter;
import org.michaelbel.moviemade.ui.modules.main.MainRepository;
import org.michaelbel.moviemade.ui.modules.recommendations.RcmdContract;
import org.michaelbel.moviemade.ui.modules.recommendations.RcmdPresenter;
import org.michaelbel.moviemade.ui.modules.recommendations.RcmdRepository;
import org.michaelbel.moviemade.ui.modules.reviews.ReviewsContract;
import org.michaelbel.moviemade.ui.modules.reviews.ReviewsPresenter;
import org.michaelbel.moviemade.ui.modules.reviews.ReviewsRepository;
import org.michaelbel.moviemade.ui.modules.search.SearchContract;
import org.michaelbel.moviemade.ui.modules.search.SearchMoviesPresenter;
import org.michaelbel.moviemade.ui.modules.search.SearchRepository;
import org.michaelbel.moviemade.ui.modules.similar.SimilarContract;
import org.michaelbel.moviemade.ui.modules.similar.SimilarMoviesPresenter;
import org.michaelbel.moviemade.ui.modules.similar.SimilarRepository;
import org.michaelbel.moviemade.ui.modules.trailers.TrailersContract;
import org.michaelbel.moviemade.ui.modules.trailers.TrailersPresenter;
import org.michaelbel.moviemade.ui.modules.trailers.TrailersRepository;
import org.michaelbel.moviemade.ui.modules.watchlist.WatchlistContract;
import org.michaelbel.moviemade.ui.modules.watchlist.WatchlistPresenter;
import org.michaelbel.moviemade.ui.modules.watchlist.WatchlistRepository;
import org.michaelbel.moviemade.utils.SharedPrefsKt;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class FragmentModule {

    @Provides
    @FragmentScoped
    SharedPreferences sharedPreferences(Context context) {
        return context.getSharedPreferences(SharedPrefsKt.SP_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @FragmentScoped
    TrailersContract.Presenter trailersPresenter(Retrofit retrofit) {
        return new TrailersPresenter(new TrailersRepository(retrofit.create(MoviesService.class)));
    }

    @Provides
    @FragmentScoped
    WatchlistContract.Presenter watchlistPresenter(Retrofit retrofit) {
        return new WatchlistPresenter(new WatchlistRepository(retrofit.create(AccountService.class)));
    }

    @Provides
    @FragmentScoped
    SimilarContract.Presenter similarPresenter(Retrofit retrofit) {
        return new SimilarMoviesPresenter(new SimilarRepository(retrofit.create(MoviesService.class)));
    }

    @Provides
    @FragmentScoped
    SearchContract.Presenter searchPresenter(Retrofit retrofit) {
        return new SearchMoviesPresenter(new SearchRepository(retrofit.create(SearchService.class)));
    }

    @Provides
    @FragmentScoped
    ReviewsContract.Presenter reviewsPresenter(Retrofit retrofit) {
        return new ReviewsPresenter(new ReviewsRepository(retrofit.create(MoviesService.class)));
    }

    @Provides
    @FragmentScoped
    RcmdContract.Presenter rcmdPresenter(Retrofit retrofit) {
        return new RcmdPresenter(new RcmdRepository(retrofit.create(MoviesService.class)));
    }

    @Provides
    @FragmentScoped
    MainContract.Presenter mainPresenter(Retrofit retrofit) {
        return new MainPresenter(new MainRepository(retrofit.create(MoviesService.class)));
    }

    @Provides
    @FragmentScoped
    KeywordContract.Presenter keywordPresenter(Retrofit retrofit) {
        return new KeywordPresenter(new KeywordRepository(retrofit.create(KeywordsService.class)));
    }

    @Provides
    @FragmentScoped
    KeywordsContract.Presenter keywordsPresenter(Retrofit retrofit) {
        return new KeywordsPresenter(new KeywordsRepository(retrofit.create(MoviesService.class)));
    }

    @Provides
    @FragmentScoped
    FavoritesContract.Presenter favoritePresenter(Retrofit retrofit) {
        return new FavoritesPresenter(new FavoriteRepository(retrofit.create(AccountService.class)));
    }
}