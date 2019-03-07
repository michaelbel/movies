package org.michaelbel.moviemade.presentation.features.movie

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_movie.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.DeviceUtil
import org.michaelbel.moviemade.core.local.Intents.EXTRA_MOVIE
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_SESSION_ID
import org.michaelbel.moviemade.core.TmdbConfig
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_IMAGE
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.base.BaseActivity
import org.michaelbel.moviemade.presentation.features.main.appbar.AppBarStateChangeListener
import java.util.*
import javax.inject.Inject

class MovieActivity: BaseActivity() {

    lateinit var movie: Movie
    lateinit var fragment: MovieFragment

    @Inject
    lateinit var preferences: SharedPreferences

    private val isSystemStatusBarShown: Boolean
        get() = window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        App[application].createActivityComponent().inject(this)

        movie = intent.getSerializableExtra(EXTRA_MOVIE) as Movie

        fragment = MovieFragment.newInstance(movie)
        supportFragmentManager.beginTransaction().replace(container.id, fragment).commit()

        window.statusBarColor = ContextCompat.getColor(this, R.color.primary)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent40)

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {finish()}
        toolbar.title = null

        appBar.addOnOffsetChangedListener(object: AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarState) {
                toolbarTitle.text = if (state === AppBarState.COLLAPSED) movie.title else null
            }
        })

        /**
         * todo
         * supportActionBar?.title пока не получается использовать,
         * так как его перехватывает collapsingToolbarLayout.title.
         */
        toolbarTitle.text = movie.title
        Glide.with(this)
             .load(TmdbConfig.image(movie.backdropPath))
             .thumbnail(0.1F)
             .into(cover)

        collapsingLayout.setContentScrimColor(ContextCompat.getColor(this, R.color.primary))
        collapsingLayout.setStatusBarScrimColor(ContextCompat.getColor(this, android.R.color.transparent))

        val params = fullToolbar.layoutParams as FrameLayout.LayoutParams
        params.topMargin = DeviceUtil.statusBarHeight(this)

        fullToolbar.setNavigationOnClickListener { onBackPressed() }

        fullImage.setOnClickListener {
            fullToolbar.visibility = if (isSystemStatusBarShown) GONE else VISIBLE
            showSystemStatusBar(isSystemStatusBarShown.not())
        }

        cover.setOnLongClickListener {
            val sessionId = preferences.getString(KEY_SESSION_ID, "") ?: ""
            if (sessionId.isNotEmpty()) {
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
            fragment.imageAnimator?.exit(true)
        } else {
            super.onBackPressed()
        }
    }

    fun showSystemStatusBar(state: Boolean) {
        val flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE
        window.decorView.systemUiVisibility = if (state) 0 else flags
    }
}