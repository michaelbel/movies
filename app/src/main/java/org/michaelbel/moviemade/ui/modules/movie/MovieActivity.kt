package org.michaelbel.moviemade.ui.modules.movie

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
import org.michaelbel.moviemade.data.entity.Movie
import org.michaelbel.moviemade.ui.base.BaseActivity
import org.michaelbel.moviemade.ui.modules.main.appbar.AppBarState
import org.michaelbel.moviemade.ui.modules.main.appbar.AppBarStateChangeListener
import org.michaelbel.moviemade.utils.DeviceUtil
import org.michaelbel.moviemade.utils.KEY_SESSION_ID
import org.michaelbel.moviemade.utils.MOVIE
import org.michaelbel.moviemade.utils.TMDB_IMAGE
import java.util.*

class MovieActivity : BaseActivity() {

    var movie: Movie? = null

    // Fixme
    private set

    private var fragment: MovieFragment? = null

    fun getFullImage() : GestureImageView {
        return full_image
    }

    fun getFullBackground() : View {
        return full_background
    }

    private val isSystemStatusBarShown: Boolean

    get() = window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        movie = intent.getSerializableExtra(MOVIE) as Movie

        fragment = supportFragmentManager.findFragmentById(R.id.fragment) as MovieFragment?
        fragment!!.presenter.setDetailExtra(movie!!)
        fragment!!.presenter.getDetails(movie!!.id)

        window.statusBarColor = ContextCompat.getColor(this, R.color.primary)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent40)

        toolbar!!.setNavigationIcon(R.drawable.ic_arrow_back)
        setSupportActionBar(toolbar)
        toolbar!!.setNavigationOnClickListener { view -> finish() }
        toolbar!!.title = null

        app_bar!!.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarState) {
                if (state === AppBarState.COLLAPSED) {
                    toolbar_title!!.text = movie!!.title
                } else {
                    toolbar_title!!.text = null
                }
            }
        })

        toolbar_title!!.text = movie!!.title
        Glide.with(this).load(String.format(Locale.US, TMDB_IMAGE, "original", movie!!.backdropPath)).thumbnail(0.1f).into(backdrop_image!!)

        collapsing_layout!!.setContentScrimColor(ContextCompat.getColor(this, R.color.primary))
        collapsing_layout!!.setStatusBarScrimColor(ContextCompat.getColor(this, android.R.color.transparent))

        val params = full_image_toolbar!!.layoutParams as FrameLayout.LayoutParams
        params.topMargin = DeviceUtil.getStatusBarHeight(this)

        full_image_toolbar!!.setNavigationOnClickListener { onBackPressed() }

        full_image!!.setOnClickListener {
            full_image_toolbar!!.visibility = if (isSystemStatusBarShown) View.INVISIBLE else View.VISIBLE
            showSystemStatusBar(!isSystemStatusBarShown)
        }

        backdrop_image!!.setOnLongClickListener {
            if (!getSharedPreferences().getString(KEY_SESSION_ID, "")!!.isEmpty()) {
                DeviceUtil.vibrate(this@MovieActivity, 15)
                val dialog = BackdropDialog.newInstance(String.format(Locale.US, TMDB_IMAGE, "original", movie!!.backdropPath))
                dialog.show(supportFragmentManager, "tag")
                return@setOnLongClickListener true
            }
            false
        }
    }

    override fun onBackPressed() {
        if (fragment!!.imageAnimator != null && !fragment!!.imageAnimator.isLeaving) {
            fragment!!.imageAnimator.exit(true)
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