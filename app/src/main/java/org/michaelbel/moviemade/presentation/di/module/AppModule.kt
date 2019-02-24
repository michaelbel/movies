package org.michaelbel.moviemade.presentation.di.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import org.michaelbel.moviemade.core.utils.SP_NAME
import javax.inject.Singleton

@Module
class AppModule constructor(context: Context) {

    private val appContext = context.applicationContext

    @Singleton
    @Provides
    fun provideAppContext(): Context = appContext

    @Singleton
    @Provides
    fun sharedPreferences(): SharedPreferences = appContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
}