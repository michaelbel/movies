package org.michaelbel.movies.platform.impl

import android.content.Context
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object PlayModule {

    @Provides
    fun provideReviewManager(
        @ApplicationContext context: Context
    ): ReviewManager = ReviewManagerFactory.create(context)

    @Provides
    fun provideAppUpdateManager(
        @ApplicationContext context: Context
    ): AppUpdateManager = AppUpdateManagerFactory.create(context)
}