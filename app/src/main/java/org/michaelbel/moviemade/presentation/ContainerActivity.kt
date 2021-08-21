package org.michaelbel.moviemade.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import org.michaelbel.data.remote.model.Movie
import org.michaelbel.data.remote.model.Movie.Companion.FAVORITE
import org.michaelbel.data.remote.model.Movie.Companion.RECOMMENDATIONS
import org.michaelbel.data.remote.model.Movie.Companion.SIMILAR
import org.michaelbel.data.remote.model.Movie.Companion.WATCHLIST
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.databinding.ActivityParentBinding
import org.michaelbel.moviemade.presentation.common.base.BaseActivity
import org.michaelbel.moviemade.presentation.features.about.AboutFragment
import org.michaelbel.moviemade.presentation.features.keywords.KeywordsFragment
import org.michaelbel.moviemade.presentation.features.main.MoviesFragment2
import org.michaelbel.moviemade.presentation.features.reviews.ReviewsFragment
import org.michaelbel.moviemade.presentation.features.settings.SettingsFragment
import org.michaelbel.moviemade.presentation.features.trailers.TrailersFragment

@AndroidEntryPoint
class ContainerActivity: BaseActivity(R.layout.activity_parent) {

    private val binding: ActivityParentBinding by viewBinding(R.id.parentLayout)

    private var movie: Movie? = null

    val containerId: Int
        get() = binding.container.id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragment = intent.getStringExtra(FRAGMENT_NAME)
        val accountId = intent.getIntExtra(EXTRA_ACCOUNT_ID, 0)
        if (intent.getSerializableExtra(EXTRA_MOVIE) != null) {
            movie = intent.getSerializableExtra(EXTRA_MOVIE) as Movie
        }

        if (savedInstanceState == null) {
            val screen: Fragment = when(fragment) {
                TRAILERS -> TrailersFragment.newInstance(movie!!)
                REVIEWS -> ReviewsFragment.newInstance(movie!!)
                KEYWORDS -> KeywordsFragment.newInstance(movie!!)
                ABOUT -> AboutFragment()
                SETTINGS -> SettingsFragment()
                FAVORITE -> MoviesFragment2.newInstance(FAVORITE, accountId)
                WATCHLIST -> MoviesFragment2.newInstance(WATCHLIST, accountId)
                SIMILAR -> MoviesFragment2.newInstance(SIMILAR, movie!!)
                RECOMMENDATIONS -> MoviesFragment2.newInstance(RECOMMENDATIONS, movie!!)
                else -> TrailersFragment.newInstance(movie!!)
            }

            supportFragmentManager.commitNow { replace(containerId, screen) }
        }
    }

    companion object {
        const val EXTRA_MOVIE = "movie"
        const val EXTRA_ACCOUNT_ID = "account_id"

        const val FRAGMENT_NAME = "fragment_name"

        const val TRAILERS = "trailers"
        const val REVIEWS = "reviews"
        const val KEYWORDS = "keywords"
        const val ABOUT = "about"
        const val SETTINGS = "settings"
    }
}