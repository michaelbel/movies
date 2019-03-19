package org.michaelbel.moviemade.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_container.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.MoviesResponse.Companion.FAVORITE
import org.michaelbel.moviemade.core.entity.MoviesResponse.Companion.RECOMMENDATIONS
import org.michaelbel.moviemade.core.entity.MoviesResponse.Companion.SIMILAR
import org.michaelbel.moviemade.core.entity.MoviesResponse.Companion.WATCHLIST
import org.michaelbel.moviemade.presentation.base.BaseActivity
import org.michaelbel.moviemade.presentation.features.about.AboutFragment
import org.michaelbel.moviemade.presentation.features.keywords.KeywordsFragment
import org.michaelbel.moviemade.presentation.features.reviews.ReviewsFragment
import org.michaelbel.moviemade.presentation.features.trailers.TrailersFragment

class ContainerActivity: BaseActivity() {

    companion object {
        const val EXTRA_MOVIE = "movie"
        const val EXTRA_ACCOUNT_ID = "account_id"

        const val FRAGMENT_NAME = "fragment_name"

        const val TRAILERS = "trailers"
        const val REVIEWS = "reviews"
        const val KEYWORDS = "keywords"
        const val ABOUT = "about"
    }

    private var movie = Movie(id = 0, title = "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        val fragment = intent.getStringExtra(FRAGMENT_NAME)
        val accountId = intent.getIntExtra(EXTRA_ACCOUNT_ID, 0)
        if (intent.getSerializableExtra(EXTRA_MOVIE) != null) {
            movie = intent.getSerializableExtra(EXTRA_MOVIE) as Movie
        }

        if (savedInstanceState == null) {
            val screen: Fragment = when(fragment) {
                TRAILERS -> TrailersFragment.newInstance(movie)
                REVIEWS -> ReviewsFragment.newInstance(movie)
                KEYWORDS -> KeywordsFragment.newInstance(movie)
                ABOUT -> AboutFragment.newInstance()
                FAVORITE -> MoviesFragment2.newInstance(FAVORITE, accountId)
                WATCHLIST -> MoviesFragment2.newInstance(WATCHLIST, accountId)
                SIMILAR -> MoviesFragment2.newInstance(SIMILAR, movie)
                RECOMMENDATIONS -> MoviesFragment2.newInstance(RECOMMENDATIONS, movie)
                else -> TrailersFragment.newInstance(movie)
            }

            supportFragmentManager.beginTransaction().replace(container.id, screen).commit()
        }
    }
}