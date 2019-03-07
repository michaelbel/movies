package org.michaelbel.moviemade.presentation.di.component

import dagger.Subcomponent
import org.michaelbel.moviemade.presentation.di.module.FragmentModule
import org.michaelbel.moviemade.presentation.di.scope.FragmentScope
import org.michaelbel.moviemade.presentation.features.account.AccountFragment
import org.michaelbel.moviemade.presentation.features.favorites.FavoritesFragment
import org.michaelbel.moviemade.presentation.features.keywords.fragment.KeywordFragment
import org.michaelbel.moviemade.presentation.features.keywords.fragment.KeywordsFragment
import org.michaelbel.moviemade.presentation.features.main.MoviesFragment
import org.michaelbel.moviemade.presentation.features.movie.BackdropDialog
import org.michaelbel.moviemade.presentation.features.movie.MovieFragment
import org.michaelbel.moviemade.presentation.features.reviews.fragment.ReviewFragment
import org.michaelbel.moviemade.presentation.features.reviews.fragment.ReviewsFragment
import org.michaelbel.moviemade.presentation.features.search.SearchMoviesFragment
import org.michaelbel.moviemade.presentation.features.trailers.TrailersFragment
import org.michaelbel.moviemade.presentation.features.watchlist.WatchlistFragment

@FragmentScope
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(target: MovieFragment)
    fun inject(target: ReviewFragment)
    fun inject(target: AccountFragment)
    fun inject(target: FavoritesFragment)
    fun inject(target: WatchlistFragment)
    fun inject(target: TrailersFragment)
    fun inject(target: MoviesFragment)
    fun inject(target: SearchMoviesFragment)
    fun inject(target: ReviewsFragment)
    fun inject(target: KeywordFragment)
    fun inject(target: KeywordsFragment)
    fun inject(target: BackdropDialog)
}