package org.michaelbel.movies.platform.gms.config

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.michaelbel.movies.platform.main.config.ConfigService

@Module
@InstallIn(SingletonComponent::class)
internal interface ConfigServiceModule {

    @Binds
    @Singleton
    fun provideConfigService(service: ConfigServiceImpl): ConfigService
}