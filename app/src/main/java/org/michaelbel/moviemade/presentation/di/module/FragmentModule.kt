package org.michaelbel.moviemade.presentation.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.michaelbel.data.local.dao.KeywordsDao
import org.michaelbel.data.local.dao.MoviesDao
import org.michaelbel.data.local.dao.ReviewsDao
import org.michaelbel.data.local.dao.TrailersDao
import org.michaelbel.data.local.dao.UsersDao
import org.michaelbel.data.remote.Api
import org.michaelbel.domain.KeywordsRepository
import org.michaelbel.domain.MoviesRepository
import org.michaelbel.domain.ReviewsRepository
import org.michaelbel.domain.TrailersRepository
import org.michaelbel.domain.UsersRepository

@Module
@InstallIn(SingletonComponent::class)
object FragmentModule {

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
}