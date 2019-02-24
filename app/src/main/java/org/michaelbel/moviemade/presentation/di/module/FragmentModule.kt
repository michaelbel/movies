package org.michaelbel.moviemade.presentation.di.module

import dagger.Module
import dagger.Provides
import org.michaelbel.moviemade.core.remote.AccountService
import org.michaelbel.moviemade.core.remote.KeywordsService
import org.michaelbel.moviemade.core.remote.MoviesService
import org.michaelbel.moviemade.core.remote.SearchService
import org.michaelbel.moviemade.presentation.features.favorites.FavoriteRepository
import org.michaelbel.moviemade.presentation.features.favorites.FavoritesContract
import org.michaelbel.moviemade.presentation.features.favorites.FavoritesPresenter
import org.michaelbel.moviemade.presentation.features.keywords.*
import org.michaelbel.moviemade.presentation.features.main.MainContract
import org.michaelbel.moviemade.presentation.features.main.MainPresenter
import org.michaelbel.moviemade.presentation.features.main.MainRepository
import org.michaelbel.moviemade.presentation.features.recommendations.RcmdContract
import org.michaelbel.moviemade.presentation.features.recommendations.RcmdPresenter
import org.michaelbel.moviemade.presentation.features.recommendations.RcmdRepository
import org.michaelbel.moviemade.presentation.features.reviews.ReviewsContract
import org.michaelbel.moviemade.presentation.features.reviews.ReviewsPresenter
import org.michaelbel.moviemade.presentation.features.reviews.ReviewsRepository
import org.michaelbel.moviemade.presentation.features.search.SearchContract
import org.michaelbel.moviemade.presentation.features.search.SearchMoviesPresenter
import org.michaelbel.moviemade.presentation.features.search.SearchRepository
import org.michaelbel.moviemade.presentation.features.similar.SimilarContract
import org.michaelbel.moviemade.presentation.features.similar.SimilarMoviesPresenter
import org.michaelbel.moviemade.presentation.features.similar.SimilarRepository
import org.michaelbel.moviemade.presentation.features.trailers.TrailersContract
import org.michaelbel.moviemade.presentation.features.trailers.TrailersPresenter
import org.michaelbel.moviemade.presentation.features.trailers.TrailersRepository
import org.michaelbel.moviemade.presentation.features.watchlist.WatchlistContract
import org.michaelbel.moviemade.presentation.features.watchlist.WatchlistPresenter
import org.michaelbel.moviemade.presentation.features.watchlist.WatchlistRepository
import retrofit2.Retrofit

@Module
class FragmentModule {

    @Provides
    fun trailersPresenter(retrofit: Retrofit): TrailersContract.Presenter =
        TrailersPresenter(TrailersRepository(retrofit.create(MoviesService::class.java)))

    @Provides
    fun watchlistPresenter(retrofit: Retrofit): WatchlistContract.Presenter =
        WatchlistPresenter(WatchlistRepository(retrofit.create(AccountService::class.java)))

    @Provides
    fun similarPresenter(retrofit: Retrofit): SimilarContract.Presenter =
        SimilarMoviesPresenter(SimilarRepository(retrofit.create(MoviesService::class.java)))

    @Provides
    fun searchPresenter(retrofit: Retrofit): SearchContract.Presenter =
        SearchMoviesPresenter(SearchRepository(retrofit.create(SearchService::class.java)))

    @Provides
    fun reviewsPresenter(retrofit: Retrofit): ReviewsContract.Presenter =
        ReviewsPresenter(ReviewsRepository(retrofit.create(MoviesService::class.java)))

    @Provides
    fun rcmdPresenter(retrofit: Retrofit): RcmdContract.Presenter =
        RcmdPresenter(RcmdRepository(retrofit.create(MoviesService::class.java)))

    @Provides
    fun mainPresenter(retrofit: Retrofit): MainContract.Presenter =
        MainPresenter(MainRepository(retrofit.create(MoviesService::class.java)))

    @Provides
    fun keywordPresenter(retrofit: Retrofit): KeywordContract.Presenter =
        KeywordPresenter(KeywordRepository(retrofit.create(KeywordsService::class.java)))

    @Provides
    fun keywordsPresenter(retrofit: Retrofit): KeywordsContract.Presenter =
        KeywordsPresenter(KeywordsRepository(retrofit.create(MoviesService::class.java)))

    @Provides
    fun favoritePresenter(retrofit: Retrofit): FavoritesContract.Presenter =
        FavoritesPresenter(FavoriteRepository(retrofit.create(AccountService::class.java)))
}