package org.michaelbel.moviemade.presentation.di.component

import dagger.Subcomponent
import org.michaelbel.moviemade.presentation.common.BottomSheetDialog
import org.michaelbel.moviemade.presentation.di.module.FragmentModule
import org.michaelbel.moviemade.presentation.di.scope.FragmentScope
import org.michaelbel.moviemade.presentation.features.keywords.KeywordsFragment
import org.michaelbel.moviemade.presentation.features.login.LoginFragment
import org.michaelbel.moviemade.presentation.features.main.MoviesFragment
import org.michaelbel.moviemade.presentation.features.main.MoviesFragment2
import org.michaelbel.moviemade.presentation.features.movie.MovieFragment
import org.michaelbel.moviemade.presentation.features.review.ReviewFragment
import org.michaelbel.moviemade.presentation.features.reviews.ReviewsFragment
import org.michaelbel.moviemade.presentation.features.search.SearchMoviesFragment
import org.michaelbel.moviemade.presentation.features.trailers.TrailersFragment
import org.michaelbel.moviemade.presentation.features.user.UserFragment

@FragmentScope
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(target: MovieFragment)
    fun inject(target: ReviewFragment)
    fun inject(target: LoginFragment)
    fun inject(target: TrailersFragment)
    fun inject(target: MoviesFragment)
    fun inject(target: MoviesFragment2)
    fun inject(target: SearchMoviesFragment)
    fun inject(target: ReviewsFragment)
    fun inject(target: KeywordsFragment)
    fun inject(target: BottomSheetDialog)
    fun inject(target: UserFragment)
}