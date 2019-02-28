package org.michaelbel.moviemade.presentation.di.component

import dagger.Subcomponent
import org.michaelbel.moviemade.presentation.di.module.ActivityModule
import org.michaelbel.moviemade.presentation.di.scope.ActivityScope
import org.michaelbel.moviemade.presentation.features.main.MainActivity
import org.michaelbel.moviemade.presentation.features.movie.MovieActivity

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(target: MainActivity)
    fun inject(target: MovieActivity)
}