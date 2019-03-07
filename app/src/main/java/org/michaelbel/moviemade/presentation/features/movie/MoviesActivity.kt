package org.michaelbel.moviemade.presentation.features.movie

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_default.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.MoviesResponse.Companion.FAVORITE
import org.michaelbel.moviemade.core.entity.MoviesResponse.Companion.RECOMMENDATIONS
import org.michaelbel.moviemade.core.entity.MoviesResponse.Companion.SIMILAR
import org.michaelbel.moviemade.core.entity.MoviesResponse.Companion.WATCHLIST
import org.michaelbel.moviemade.core.local.Intents
import org.michaelbel.moviemade.core.local.Intents.EXTRA_ACCOUNT_ID
import org.michaelbel.moviemade.core.local.Intents.EXTRA_LIST
import org.michaelbel.moviemade.presentation.base.BaseActivity
import org.michaelbel.moviemade.presentation.features.favorites.FavoritesFragment
import org.michaelbel.moviemade.presentation.features.main.MoviesFragment
import org.michaelbel.moviemade.presentation.features.watchlist.WatchlistFragment

class MoviesActivity: BaseActivity() {

    private var movie: Movie = Movie(id = 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)

        val list = intent.getStringExtra(EXTRA_LIST)
        val accountId = intent.getIntExtra(EXTRA_ACCOUNT_ID, 0)
        if (accountId == 0) {
            movie = intent.getSerializableExtra(Intents.EXTRA_MOVIE) as Movie
        }

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        when (list) {
            SIMILAR -> supportActionBar?.setTitle(R.string.similar_movies)
            RECOMMENDATIONS -> supportActionBar?.setTitle(R.string.recommendations)
            FAVORITE -> supportActionBar?.setTitle(R.string.favorites)
            WATCHLIST -> supportActionBar?.setTitle(R.string.watchlist)
        }

        if (list != FAVORITE && list != WATCHLIST) {
            supportActionBar?.subtitle = movie.title
        }

        if (savedInstanceState == null) {
            when (list) {
                FAVORITE ->
                    supportFragmentManager
                        .beginTransaction()
                        .replace(container.id, FavoritesFragment.newInstance(accountId))
                        .commit()
                WATCHLIST ->
                    supportFragmentManager
                        .beginTransaction()
                        .replace(container.id, WatchlistFragment.newInstance(accountId))
                        .commit()
                else ->
                    supportFragmentManager
                        .beginTransaction()
                        .replace(container.id, MoviesFragment.newInstance(list, movie.id))
                        .commit()
            }
        }
    }
}