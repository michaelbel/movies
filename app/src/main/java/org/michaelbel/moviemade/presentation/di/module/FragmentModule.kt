package org.michaelbel.moviemade.presentation.di.module

import dagger.Module
import dagger.Provides
import org.michaelbel.data.local.dao.*
import org.michaelbel.data.remote.Api
import org.michaelbel.domain.*
import org.michaelbel.moviemade.presentation.features.keywords.KeywordsFactory
import org.michaelbel.moviemade.presentation.features.login.LoginFactory
import org.michaelbel.moviemade.presentation.features.main.MoviesFactory
import org.michaelbel.moviemade.presentation.features.movie.MovieFactory
import org.michaelbel.moviemade.presentation.features.reviews.ReviewsFactory
import org.michaelbel.moviemade.presentation.features.trailers.TrailersFactory
import org.michaelbel.moviemade.presentation.features.user.UserFactory

@Module
class FragmentModule {

    //region Repositories

    @Provides
    fun trailersRepository(api: Api, dao: TrailersDao) = TrailersRepository(api, dao)

    @Provides
    fun reviewsRepository(api: Api, dao: ReviewsDao) = ReviewsRepository(api, dao)

    @Provides
    fun keywordsRepository(api: Api, dao: KeywordsDao) = KeywordsRepository(api, dao)

    @Provides
    fun moviesRepository(api: Api, dao: MoviesDao) = MoviesRepository(api, dao)

    @Provides
    fun usersRepository(api: Api, dao: UsersDao) = UsersRepository(api, dao)

    //endregion

    //region Factories

    @Provides
    fun trailersFactory(repository: TrailersRepository) = TrailersFactory(repository)

    @Provides
    fun reviewsFactory(repository: ReviewsRepository) = ReviewsFactory(repository)

    @Provides
    fun keywordsFactory(repository: KeywordsRepository) = KeywordsFactory(repository)

    @Provides
    fun movieFactory(repository: MoviesRepository) = MovieFactory(repository)

    @Provides
    fun moviesFactory(repository: MoviesRepository) = MoviesFactory(repository)

    @Provides
    fun loginFactory(repository: UsersRepository) = LoginFactory(repository)

    @Provides
    fun userFactory(repository: UsersRepository) = UserFactory(repository)

    //endregion
}