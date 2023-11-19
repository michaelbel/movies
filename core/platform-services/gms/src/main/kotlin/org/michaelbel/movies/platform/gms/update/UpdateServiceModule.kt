package org.michaelbel.movies.platform.gms.update

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.michaelbel.movies.platform.main.update.UpdateService

@Module
@InstallIn(SingletonComponent::class)
internal interface UpdateServiceModule {

    @Binds
    @Singleton
    fun provideUpdateService(service: UpdateServiceImpl): UpdateService
}