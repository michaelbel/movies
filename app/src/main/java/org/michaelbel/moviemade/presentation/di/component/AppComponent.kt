package org.michaelbel.moviemade.presentation.di.component

import dagger.Component
import org.michaelbel.moviemade.presentation.di.module.ActivityModule
import org.michaelbel.moviemade.presentation.di.module.AppModule
import org.michaelbel.moviemade.presentation.di.module.FragmentModule
import org.michaelbel.moviemade.presentation.di.module.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {
    fun plus(target: ActivityModule): ActivityComponent
    fun plus(target: FragmentModule): FragmentComponent
}