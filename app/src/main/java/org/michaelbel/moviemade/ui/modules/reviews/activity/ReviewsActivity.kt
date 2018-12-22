package org.michaelbel.moviemade.ui.modules.reviews.activity

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_reviews.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.data.entity.Movie
import org.michaelbel.moviemade.ui.base.BaseActivity
import org.michaelbel.moviemade.ui.modules.reviews.fragment.ReviewsFragment
import org.michaelbel.moviemade.utils.MOVIE

class ReviewsActivity : BaseActivity() {

    fun getToolbar() : Toolbar {
        return toolbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)

        val movie = intent.getSerializableExtra(MOVIE) as Movie

        setSupportActionBar(toolbar)
        toolbar!!.setNavigationOnClickListener { finish() }
        toolbar_subtitle!!.text = movie.title

        if (savedInstanceState == null) {
            startFragment(ReviewsFragment.newInstance(movie), R.id.fragment_view)
        }
    }
}