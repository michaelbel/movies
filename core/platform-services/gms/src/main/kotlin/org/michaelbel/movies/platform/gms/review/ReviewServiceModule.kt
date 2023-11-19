package org.michaelbel.movies.platform.gms.review

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.michaelbel.movies.platform.main.review.ReviewService

@Module
@InstallIn(SingletonComponent::class)
internal interface ReviewServiceModule {

    @Binds
    @Singleton
    fun provideReviewService(service: ReviewServiceImpl): ReviewService
}