package org.michaelbel.moviemade.presentation.features.reviews.activity

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_frame.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.utils.MOVIE
import org.michaelbel.moviemade.presentation.base.BaseActivity
import org.michaelbel.moviemade.presentation.features.reviews.fragment.ReviewsFragment

class ReviewsActivity: BaseActivity() {

    fun getToolbar(): Toolbar = toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)

        val movie = intent.getSerializableExtra(MOVIE) as Movie

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.setTitle(R.string.reviews)
        supportActionBar?.subtitle = movie.title

        if (savedInstanceState == null) {
            startFragment(ReviewsFragment.newInstance(movie), R.id.fragment_view)
        }
    }
}