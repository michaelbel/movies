package org.michaelbel.movies.platform.gms.app

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.michaelbel.movies.platform.main.app.AppService

@Module
@InstallIn(SingletonComponent::class)
internal interface AppServiceModule {

    @Binds
    @Singleton
    fun provideAppService(service: AppServiceImpl): AppService
}