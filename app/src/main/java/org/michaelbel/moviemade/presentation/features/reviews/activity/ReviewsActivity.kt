package org.michaelbel.moviemade.presentation.features.reviews.activity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_default.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.utils.EXTRA_MOVIE
import org.michaelbel.moviemade.presentation.base.BaseActivity
import org.michaelbel.moviemade.presentation.features.reviews.fragment.ReviewsFragment

class ReviewsActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)

        val movie = intent.getSerializableExtra(EXTRA_MOVIE) as Movie

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.setTitle(R.string.reviews)
        supportActionBar?.subtitle = movie.title

        if (savedInstanceState == null) {
            startFragment(ReviewsFragment.newInstance(movie), container.id)
        }
    }
}