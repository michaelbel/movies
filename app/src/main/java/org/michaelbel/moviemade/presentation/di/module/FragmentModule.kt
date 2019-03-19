package org.michaelbel.moviemade.presentation.di.module

import dagger.Module
import dagger.Provides
import org.michaelbel.moviemade.core.remote.Api
import org.michaelbel.moviemade.presentation.features.account.AccountContract
import org.michaelbel.moviemade.presentation.features.account.AccountPresenter
import org.michaelbel.moviemade.presentation.features.account.AccountRepository
import org.michaelbel.moviemade.presentation.features.keywords.KeywordsContract
import org.michaelbel.moviemade.presentation.features.keywords.KeywordsPresenter
import org.michaelbel.moviemade.presentation.features.keywords.KeywordsRepository
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

@Module
class FragmentModule {

    @Provides
    fun trailersPresenter(service: Api): TrailersContract.Presenter =
            TrailersPresenter(TrailersRepository(service))

    @Provides
    fun searchPresenter(service: Api): SearchContract.Presenter =
            SearchMoviesPresenter(SearchRepository(service))

    @Provides
    fun reviewsPresenter(service: Api): ReviewsContract.Presenter =
            ReviewsPresenter(ReviewsRepository(service))

    @Provides
    fun mainPresenter(service: Api): MainContract.Presenter =
            MainPresenter(MainRepository(service))

    @Provides
    fun moviePresenter(service: Api): MovieContract.Presenter =
            MoviePresenter(MovieRepository(service))

    @Provides
    fun keywordsPresenter(service: Api): KeywordsContract.Presenter =
            KeywordsPresenter(KeywordsRepository(service))

    @Provides
    fun accountPresenter(service: Api): AccountContract.Presenter =
            AccountPresenter(AccountRepository(service))
}