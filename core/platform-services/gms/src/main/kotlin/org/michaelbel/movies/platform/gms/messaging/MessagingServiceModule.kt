package org.michaelbel.movies.platform.gms.messaging

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.michaelbel.movies.platform.main.messaging.MessagingService

@Module
@InstallIn(SingletonComponent::class)
internal interface MessagingServiceModule {

    @Binds
    @Singleton
    fun provideMessagingService(service: MessagingServiceImpl): MessagingService
}