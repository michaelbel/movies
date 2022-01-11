package org.michaelbel.core.network

import android.content.Context
import com.ashokvarma.gander.Gander
import com.ashokvarma.gander.GanderInterceptor
import com.ashokvarma.gander.imdb.GanderIMDB
import com.ashokvarma.gander.persistence.GanderPersistence
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GanderModule {

    @Provides
    @Singleton
    fun provideGanderInterceptor(@ApplicationContext context: Context): GanderInterceptor {
        Gander.setGanderStorage(GanderPersistence.getInstance(context))
        Gander.setGanderStorage(GanderIMDB.getInstance())
        return GanderInterceptor(context)
            .showNotification(true)
            .maxContentLength(250000L)
            .retainDataFor(GanderInterceptor.Period.ONE_HOUR)
    }
}