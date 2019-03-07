package org.michaelbel.moviemade.presentation.features.reviews.activity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_default.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.Review
import org.michaelbel.moviemade.core.local.Intents.EXTRA_MOVIE
import org.michaelbel.moviemade.core.local.Intents.EXTRA_REVIEW
import org.michaelbel.moviemade.core.text.SpannableUtil
import org.michaelbel.moviemade.presentation.base.BaseActivity
import org.michaelbel.moviemade.presentation.features.reviews.fragment.ReviewFragment

class ReviewActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)

        val movie = intent.getSerializableExtra(EXTRA_MOVIE) as Movie
        val review = intent.getSerializableExtra(EXTRA_REVIEW) as Review

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.title = movie.title
        supportActionBar?.subtitle = SpannableUtil.boldText(getString(R.string.review_by), getString(R.string.review_by, review.author))

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(container.id, ReviewFragment.newInstance(review))
                    .commit()
        }
    }
}