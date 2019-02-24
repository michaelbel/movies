package org.michaelbel.moviemade.presentation.features.reviews.activity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_frame.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.Review
import org.michaelbel.moviemade.core.utils.KEY_TOOLBAR_PINNED
import org.michaelbel.moviemade.core.utils.MOVIE
import org.michaelbel.moviemade.core.utils.REVIEW
import org.michaelbel.moviemade.core.utils.SpannableUtil
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.base.BaseActivity
import org.michaelbel.moviemade.presentation.features.reviews.fragment.ReviewFragment
import javax.inject.Inject

class ReviewActivity: BaseActivity() {

    @Inject
    lateinit var preferences: SharedPreferences

    fun getToolbar(): Toolbar = toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)
        App[application].createActivityComponent().inject(this)

        val movie = intent.getSerializableExtra(MOVIE) as Movie
        val review = intent.getSerializableExtra(REVIEW) as Review

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.title = movie.title
        supportActionBar?.subtitle = SpannableUtil.boldText(getString(R.string.review_by), getString(R.string.review_by, review.author))

        val params = toolbar.layoutParams as AppBarLayout.LayoutParams
        val pin = preferences.getBoolean(KEY_TOOLBAR_PINNED, false)

        if (pin) {
            params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
        } else {
            params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
        }

        if (savedInstanceState == null) {
            startFragment(ReviewFragment.newInstance(review), R.id.fragment_view)
        }
    }

    fun changePinning(pin: Boolean) {
        val params = toolbar.layoutParams as AppBarLayout.LayoutParams
        if (pin) {
            params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
        } else {
            params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
        }
        toolbar.layoutParams = params
    }
}