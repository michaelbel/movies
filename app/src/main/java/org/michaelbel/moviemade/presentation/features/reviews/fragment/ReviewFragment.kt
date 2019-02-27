package org.michaelbel.moviemade.presentation.features.reviews.fragment

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.fragment_review.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Review
import org.michaelbel.moviemade.core.utils.*
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.reviews.activity.ReviewActivity
import javax.inject.Inject

class ReviewFragment: BaseFragment() {

    companion object {
        private const val THEME_LIGHT = 0
        private const val THEME_SEPIA = 1
        private const val THEME_NIGHT = 2

        fun newInstance(review: Review): ReviewFragment {
            val args = Bundle()
            args.putSerializable(REVIEW, review)

            val fragment = ReviewFragment()
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var review: Review

    @Inject
    lateinit var preferences: SharedPreferences

    private var backgroundLight: Int = 0
    private var backgroundSepia: Int = 0
    private var backgroundNight: Int = 0
    private var textLight: Int = 0
    private var textSepia: Int = 0
    private var textNight: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        App[requireActivity().application as App].createFragmentComponent().inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_review, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when {
            item.itemId == R.id.item_url -> Browser.openUrl(requireContext(), review.url)
            item.itemId == R.id.item_light -> {
                val current = preferences.getInt(KEY_REVIEW_THEME, THEME_NIGHT)
                preferences.edit().putInt(KEY_REVIEW_THEME, THEME_LIGHT).apply()
                changeTheme(current, THEME_LIGHT)
            }
            item.itemId == R.id.item_sepia -> {
                val current = preferences.getInt(KEY_REVIEW_THEME, THEME_NIGHT)
                preferences.edit().putInt(KEY_REVIEW_THEME, THEME_SEPIA).apply()
                changeTheme(current, THEME_SEPIA)
            }
            item.itemId == R.id.item_night -> {
                val current = preferences.getInt(KEY_REVIEW_THEME, THEME_NIGHT)
                preferences.edit().putInt(KEY_REVIEW_THEME, THEME_NIGHT).apply()
                changeTheme(current, THEME_NIGHT)
            }
        }
        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_review, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as ReviewActivity).getToolbar().setOnClickListener { scroll_layout.fullScroll(View.FOCUS_UP) }
        (requireActivity() as ReviewActivity).getToolbar().setOnLongClickListener { changePinning() }

        backgroundLight = ContextCompat.getColor(requireContext(), R.color.textColorPrimary)
        backgroundSepia = ContextCompat.getColor(requireContext(), R.color.sepiaBackground)
        backgroundNight = ContextCompat.getColor(requireContext(), R.color.backgroundColor)
        textLight = ContextCompat.getColor(requireContext(), R.color.md_black)
        textSepia = ContextCompat.getColor(requireContext(), R.color.md_black)
        textNight = ContextCompat.getColor(requireContext(), R.color.nightText)

        review_text.controller.settings.enableGestures()
        review = arguments?.getSerializable(REVIEW) as Review
        review_text.text = review.content

        when (preferences.getInt(KEY_REVIEW_THEME, THEME_NIGHT)) {
            THEME_LIGHT -> {
                scroll_layout.setBackgroundColor(backgroundLight)
                review_text.setTextColor(textLight)
            }
            THEME_SEPIA -> {
                scroll_layout.setBackgroundColor(backgroundSepia)
                review_text.setTextColor(textSepia)
            }
            THEME_NIGHT -> {
                scroll_layout.setBackgroundColor(backgroundNight)
                review_text.setTextColor(textNight)
            }
        }
    }

    private fun changeTheme(oldTheme: Int, newTheme: Int) {
        var backgroundColorStart = 0
        var backgroundColorEnd = 0
        var textColorStart = 0
        var textColorEnd = 0

        val evaluator = ArgbEvaluator()
        val backgroundAnim: ObjectAnimator
        val textAnim: ObjectAnimator
        val animatorSet = AnimatorSet()

        if (oldTheme == THEME_NIGHT && newTheme == THEME_SEPIA) {
            backgroundColorStart = backgroundNight
            backgroundColorEnd = backgroundSepia
            textColorStart = textNight
            textColorEnd = textSepia
        } else if (oldTheme == THEME_NIGHT && newTheme == THEME_LIGHT) {
            backgroundColorStart = backgroundNight
            backgroundColorEnd = backgroundLight
            textColorStart = textNight
            textColorEnd = textLight
        } else if (oldTheme == THEME_SEPIA && newTheme == THEME_LIGHT) {
            backgroundColorStart = backgroundSepia
            backgroundColorEnd = backgroundLight
            textColorStart = textSepia
            textColorEnd = textLight
        } else if (oldTheme == THEME_SEPIA && newTheme == THEME_NIGHT) {
            backgroundColorStart = backgroundSepia
            backgroundColorEnd = backgroundNight
            textColorStart = textSepia
            textColorEnd = textNight
        } else if (oldTheme == THEME_LIGHT && newTheme == THEME_SEPIA) {
            backgroundColorStart = backgroundLight
            backgroundColorEnd = backgroundSepia
            textColorStart = textLight
            textColorEnd = textSepia
        } else if (oldTheme == THEME_LIGHT && newTheme == THEME_NIGHT) {
            backgroundColorStart = backgroundLight
            backgroundColorEnd = backgroundNight
            textColorStart = textLight
            textColorEnd = textNight
        }

        backgroundAnim = ObjectAnimator.ofObject(scroll_layout, "backgroundColor", evaluator, 0, 0)
        backgroundAnim.setObjectValues(backgroundColorStart, backgroundColorEnd)

        textAnim = ObjectAnimator.ofObject(review_text, "textColor", evaluator, 0, 0)
        textAnim.setObjectValues(textColorStart, textColorEnd)

        animatorSet.playTogether(backgroundAnim, textAnim)
        animatorSet.duration = 300
        animatorSet.interpolator = DecelerateInterpolator(2F)
        AndroidUtil.runOnUIThread(Runnable { animatorSet.start() }, 0)
    }

    private fun changePinning(): Boolean {
        val pin = preferences.getBoolean(KEY_TOOLBAR_PINNED, false)
        preferences.edit().putBoolean(KEY_TOOLBAR_PINNED, !pin).apply()
        (requireActivity() as ReviewActivity).changePinning(!pin)
        Toast.makeText(requireContext(), if (!pin) R.string.msg_toolbar_pinned else R.string.msg_toolbar_unpinned, Toast.LENGTH_SHORT).show()
        return true
    }
}