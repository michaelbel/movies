package org.michaelbel.moviemade.presentation.di.module

import dagger.Module
import dagger.Provides
import org.michaelbel.moviemade.core.remote.*
import org.michaelbel.moviemade.presentation.features.account.AccountContract
import org.michaelbel.moviemade.presentation.features.account.AccountPresenter
import org.michaelbel.moviemade.presentation.features.account.AccountRepository
import org.michaelbel.moviemade.presentation.features.favorites.FavoriteRepository
import org.michaelbel.moviemade.presentation.features.favorites.FavoritesContract
import org.michaelbel.moviemade.presentation.features.favorites.FavoritesPresenter
import org.michaelbel.moviemade.presentation.features.keywords.*
import org.michaelbel.moviemade.presentation.features.main.MainContract
import org.michaelbel.moviemade.presentation.features.main.MainPresenter
import org.michaelbel.moviemade.presentation.features.main.MainRepository
import org.michaelbel.moviemade.presentation.features.movie.MovieContract
import org.michaelbel.moviemade.presentation.features.movie.MoviePresenter
import org.michaelbel.moviemade.presentation.features.movie.MovieRepository
import org.michaelbel.moviemade.presentation.features.reviews.ReviewsContract
import org.michaelbel.moviemade.presentation.features.reviews.ReviewsPresenter
import org.michaelbel.moviemade.presentation.features.reviews.ReviewsRepository
import org.michaelbel.moviemade.presentation.features.search.SearchContract
import org.michaelbel.moviemade.presentation.features.search.SearchMoviesPresenter
import org.michaelbel.moviemade.presentation.features.search.SearchRepository
import org.michaelbel.moviemade.presentation.features.trailers.TrailersContract
import org.michaelbel.moviemade.presentation.features.trailers.TrailersPresenter
import org.michaelbel.moviemade.presentation.features.trailers.TrailersRepository
import org.michaelbel.moviemade.presentation.features.watchlist.WatchlistContract
import org.michaelbel.moviemade.presentation.features.watchlist.WatchlistPresenter
import org.michaelbel.moviemade.presentation.features.watchlist.WatchlistRepository

@Module
class FragmentModule {

    @Provides
    fun trailersPresenter(service: MoviesService): TrailersContract.Presenter =
            TrailersPresenter(TrailersRepository(service))

    @Provides
    fun watchlistPresenter(service: AccountService): WatchlistContract.Presenter =
            WatchlistPresenter(WatchlistRepository(service))

    @Provides
    fun searchPresenter(service: SearchService): SearchContract.Presenter =
            SearchMoviesPresenter(SearchRepository(service))

    @Provides
    fun reviewsPresenter(service: MoviesService): ReviewsContract.Presenter =
            ReviewsPresenter(ReviewsRepository(service))

    @Provides
    fun mainPresenter(service: MoviesService): MainContract.Presenter =
            MainPresenter(MainRepository(service))

    @Provides
    fun moviePresenter(movieService: MoviesService, accountService: AccountService): MovieContract.Presenter =
            MoviePresenter(MovieRepository(movieService, accountService))

    @Provides
    fun keywordPresenter(service: KeywordsService): KeywordContract.Presenter =
            KeywordPresenter(KeywordRepository(service))

    @Provides
    fun keywordsPresenter(service: MoviesService): KeywordsContract.Presenter =
            KeywordsPresenter(KeywordsRepository(service))

    @Provides
    fun favoritePresenter(service: AccountService): FavoritesContract.Presenter =
            FavoritesPresenter(FavoriteRepository(service))

    @Provides
    fun accountPresenter(authService: AuthService, accountService: AccountService): AccountContract.Presenter =
            AccountPresenter(AccountRepository(authService, accountService))
}