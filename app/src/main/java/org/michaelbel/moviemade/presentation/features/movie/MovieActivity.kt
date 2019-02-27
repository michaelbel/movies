package org.michaelbel.moviemade.presentation.features.movie

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.alexvasilkov.gestures.views.GestureImageView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_movie.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.utils.DeviceUtil
import org.michaelbel.moviemade.core.utils.KEY_SESSION_ID
import org.michaelbel.moviemade.core.utils.MOVIE
import org.michaelbel.moviemade.core.utils.TMDB_IMAGE
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.base.BaseActivity
import org.michaelbel.moviemade.presentation.features.main.appbar.AppBarStateChangeListener
import java.util.*
import javax.inject.Inject

class MovieActivity: BaseActivity() {

    lateinit var movie: Movie
    lateinit var fragment: MovieFragment

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    fun getFullImage(): GestureImageView = full_image

    fun getFullBackground(): View = full_background

    private val isSystemStatusBarShown: Boolean
        get() = window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        App[application].createActivityComponent().inject(this)

        movie = intent.getSerializableExtra(MOVIE) as Movie

        fragment = supportFragmentManager.findFragmentById(R.id.fragment) as MovieFragment
        fragment.presenter.setDetailExtra(movie)
        fragment.presenter.getDetails(movie.id)

        window.statusBarColor = ContextCompat.getColor(this, R.color.primary)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent40)

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {finish()}
        toolbar.title = null

        app_bar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarState) {
                if (state === AppBarState.COLLAPSED) {
                    toolbar_title.text = movie.title
                } else {
                    toolbar_title.text = null
                }
            }
        })

        toolbar_title.text = movie.title
        Glide.with(this).load(String.format(Locale.US, TMDB_IMAGE, "original", movie.backdropPath)).thumbnail(0.1F).into(backdrop_image)

        collapsing_layout.setContentScrimColor(ContextCompat.getColor(this, R.color.primary))
        collapsing_layout.setStatusBarScrimColor(ContextCompat.getColor(this, android.R.color.transparent))

        val params = full_image_toolbar.layoutParams as FrameLayout.LayoutParams
        params.topMargin = DeviceUtil.getStatusBarHeight(this)

        full_image_toolbar.setNavigationOnClickListener { onBackPressed() }

        full_image.setOnClickListener {
            full_image_toolbar!!.visibility = if (isSystemStatusBarShown) View.INVISIBLE else View.VISIBLE
            showSystemStatusBar(!isSystemStatusBarShown)
        }

        backdrop_image.setOnLongClickListener {
            if (!sharedPreferences.getString(KEY_SESSION_ID, "")!!.isEmpty()) {
                DeviceUtil.vibrate(this@MovieActivity, 15)
                val dialog = BackdropDialog.newInstance(String.format(Locale.US, TMDB_IMAGE, "original", movie.backdropPath))
                dialog.show(supportFragmentManager, "tag")
                return@setOnLongClickListener true
            }
            false
        }
    }

    override fun onBackPressed() {
        if (fragment.imageAnimator != null && !fragment.imageAnimator!!.isLeaving) {
            fragment.imageAnimator!!.exit(true)
        } else {
            super.onBackPressed()
        }
    }

    fun showSystemStatusBar(state: Boolean) {
        // If Version SDK >= KITKAT.
        val flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE
        window.decorView.systemUiVisibility = if (state) 0 else flags
    }
}