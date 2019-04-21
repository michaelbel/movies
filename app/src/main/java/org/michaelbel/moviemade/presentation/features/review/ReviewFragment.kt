package org.michaelbel.moviemade.presentation.features.review

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.FOCUS_UP
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import kotlinx.android.synthetic.main.fragment_review.*
import org.michaelbel.core.customtabs.Browser
import org.michaelbel.data.Movie
import org.michaelbel.data.Review
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.ViewUtil
import org.michaelbel.moviemade.core.text.SpannableUtil
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import javax.inject.Inject

class ReviewFragment: BaseFragment() {

    companion object {
        private const val ARG_REVIEW = "review"
        private const val ARG_MOVIE = "movie"

        private const val KEY_TOOLBAR_PINNED = "pinned"
        private const val KEY_REVIEW_THEME = "review_theme"

        private const val THEME_LIGHT = 0
        private const val THEME_SEPIA = 1
        private const val THEME_NIGHT = 2

        private const val TOOLBAR_PINNED = 0
        private const val TOOLBAR_UNPINNED = SCROLL_FLAG_SCROLL or SCROLL_FLAG_ENTER_ALWAYS

        private const val ANIM_DURATION = 300L

        private var EVALUATOR = ArgbEvaluator()
        private var INTERPOLATOR = DecelerateInterpolator(2F)

        internal fun newInstance(review: Review, movie: Movie): ReviewFragment {
            val args = Bundle()
            args.putSerializable(ARG_REVIEW, review)
            args.putSerializable(ARG_MOVIE, movie)

            val fragment = ReviewFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var preferences: SharedPreferences

    private lateinit var movie: Movie
    private lateinit var review: Review

    private var textLight: Int = 0
    private var textSepia: Int = 0
    private var textNight: Int = 0
    private var backgroundLight: Int = 0
    private var backgroundSepia: Int = 0
    private var backgroundNight: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application as App].createFragmentComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textLight = ContextCompat.getColor(requireContext(), R.color.md_black)
        textSepia = ContextCompat.getColor(requireContext(), R.color.md_black)
        textNight = ContextCompat.getColor(requireContext(), R.color.nightText)
        backgroundLight = ContextCompat.getColor(requireContext(), R.color.textColorPrimary)
        backgroundSepia = ContextCompat.getColor(requireContext(), R.color.sepiaBackground)
        backgroundNight = ContextCompat.getColor(requireContext(), R.color.backgroundColor)

        movie = arguments?.getSerializable(ARG_MOVIE) as Movie
        review = arguments?.getSerializable(ARG_REVIEW) as Review

        toolbar.title = movie.title
        toolbar.subtitle = SpannableUtil.boldText(getString(R.string.review_by), getString(R.string.review_by, review.author))
        toolbar.navigationIcon = ViewUtil.getIcon(requireContext(), R.drawable.ic_arrow_back, R.color.iconActiveColor)
        toolbar.setOnClickListener { onScrollToTop() }
        toolbar.setOnLongClickListener { changePinning() }
        toolbar.setNavigationOnClickListener { requireFragmentManager().popBackStack() }
        toolbar.inflateMenu(R.menu.menu_review)
        toolbar.setOnMenuItemClickListener {
            when {
                it.itemId == R.id.item_url -> Browser.openUrl(requireContext(), review.url)
                it.itemId == R.id.item_light -> {
                    val current = preferences.getInt(KEY_REVIEW_THEME, THEME_NIGHT)
                    preferences.edit().putInt(KEY_REVIEW_THEME, THEME_LIGHT).apply()
                    changeTheme(current, THEME_LIGHT)
                }
                it.itemId == R.id.item_sepia -> {
                    val current = preferences.getInt(KEY_REVIEW_THEME, THEME_NIGHT)
                    preferences.edit().putInt(KEY_REVIEW_THEME, THEME_SEPIA).apply()
                    changeTheme(current, THEME_SEPIA)
                }
                it.itemId == R.id.item_night -> {
                    val current = preferences.getInt(KEY_REVIEW_THEME, THEME_NIGHT)
                    preferences.edit().putInt(KEY_REVIEW_THEME, THEME_NIGHT).apply()
                    changeTheme(current, THEME_NIGHT)
                }
            }
            return@setOnMenuItemClickListener true
        }

        reviewText.text = review.content
        reviewText.controller.settings.enableGestures()

        //val pin = preferences.getBoolean(KEY_TOOLBAR_PINNED, false)
        //val params = toolbar.layoutParams as AppBarLayout.LayoutParams
        //params.scrollFlags = if (pin) TOOLBAR_PINNED else TOOLBAR_UNPINNED
        //params.scrollFlags = TOOLBAR_PINNED

        when (preferences.getInt(KEY_REVIEW_THEME, THEME_NIGHT)) {
            THEME_LIGHT -> {
                reviewText.setTextColor(textLight)
                scrollView.setBackgroundColor(backgroundLight)
            }
            THEME_SEPIA -> {
                reviewText.setTextColor(textSepia)
                scrollView.setBackgroundColor(backgroundSepia)
            }
            THEME_NIGHT -> {
                reviewText.setTextColor(textNight)
                scrollView.setBackgroundColor(backgroundNight)
            }
        }
    }

    override fun onScrollToTop() {
        scrollView.fullScroll(FOCUS_UP)
    }

    private fun changeTheme(oldTheme: Int, newTheme: Int) {
        if (oldTheme == newTheme) {
            return
        }

        var textColorStart = 0
        var textColorEnd = 0

        var backgroundColorStart = 0
        var backgroundColorEnd = 0

        if (oldTheme == THEME_NIGHT && newTheme == THEME_SEPIA) {
            textColorStart = textNight
            textColorEnd = textSepia
            backgroundColorStart = backgroundNight
            backgroundColorEnd = backgroundSepia
        } else if (oldTheme == THEME_NIGHT && newTheme == THEME_LIGHT) {
            textColorStart = textNight
            textColorEnd = textLight
            backgroundColorStart = backgroundNight
            backgroundColorEnd = backgroundLight
        } else if (oldTheme == THEME_SEPIA && newTheme == THEME_LIGHT) {
            textColorStart = textSepia
            textColorEnd = textLight
            backgroundColorStart = backgroundSepia
            backgroundColorEnd = backgroundLight
        } else if (oldTheme == THEME_SEPIA && newTheme == THEME_NIGHT) {
            textColorStart = textSepia
            textColorEnd = textNight
            backgroundColorStart = backgroundSepia
            backgroundColorEnd = backgroundNight
        } else if (oldTheme == THEME_LIGHT && newTheme == THEME_SEPIA) {
            textColorStart = textLight
            textColorEnd = textSepia
            backgroundColorStart = backgroundLight
            backgroundColorEnd = backgroundSepia
        } else if (oldTheme == THEME_LIGHT && newTheme == THEME_NIGHT) {
            textColorStart = textLight
            textColorEnd = textNight
            backgroundColorStart = backgroundLight
            backgroundColorEnd = backgroundNight
        }

        val backgroundAnim = ObjectAnimator.ofObject(scrollView, "backgroundColor", EVALUATOR, 0, 0)
        backgroundAnim.setObjectValues(backgroundColorStart, backgroundColorEnd)

        val textAnim = ObjectAnimator.ofObject(reviewText, "textColor", EVALUATOR, 0, 0)
        textAnim.setObjectValues(textColorStart, textColorEnd)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(backgroundAnim, textAnim)
        animatorSet.duration = ANIM_DURATION
        animatorSet.interpolator = INTERPOLATOR
        animatorSet.start()
    }

    // fixme do not work.
    private fun changePinning(): Boolean {
        val pin = preferences.getBoolean(KEY_TOOLBAR_PINNED, false)
        preferences.edit().putBoolean(KEY_TOOLBAR_PINNED, !pin).apply()

        val params = toolbar.layoutParams as AppBarLayout.LayoutParams
        params.scrollFlags = if (pin) TOOLBAR_PINNED else TOOLBAR_UNPINNED
        toolbar.layoutParams = params

        //Toast.makeText(requireContext(), if (!pin) R.string.msg_toolbar_pinned else R.string.msg_toolbar_unpinned, Toast.LENGTH_SHORT).show()
        return true
    }
}