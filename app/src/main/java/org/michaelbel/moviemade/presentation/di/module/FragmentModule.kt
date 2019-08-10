package org.michaelbel.moviemade.presentation.di.module

import dagger.Module
import dagger.Provides
import org.michaelbel.data.local.dao.*
import org.michaelbel.data.remote.Api
import org.michaelbel.domain.*

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
}