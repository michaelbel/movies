package org.michaelbel.moviemade.ui.modules.reviews.activity

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_review.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.data.entity.Movie
import org.michaelbel.moviemade.data.entity.Review
import org.michaelbel.moviemade.ui.base.BaseActivity
import org.michaelbel.moviemade.ui.modules.reviews.fragment.ReviewFragment
import org.michaelbel.moviemade.utils.MOVIE
import org.michaelbel.moviemade.utils.REVIEW
import org.michaelbel.moviemade.utils.SpannableUtil

class ReviewActivity : BaseActivity() {

    fun getToolbar() : Toolbar {
        return toolbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        val movie = intent.getSerializableExtra(MOVIE) as Movie
        val review = intent.getSerializableExtra(REVIEW) as Review

        setSupportActionBar(toolbar)
        toolbar!!.setNavigationOnClickListener { finish() }
        toolbar_title!!.text = movie.title
        toolbar_subtitle!!.text = SpannableUtil.boldText(getString(R.string.review_by), getString(R.string.review_by, review.author))

        val params = toolbar!!.layoutParams as AppBarLayout.LayoutParams
        params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS

        if (savedInstanceState == null) {
            startFragment(ReviewFragment.newInstance(review), R.id.fragment_view)
        }
    }
}