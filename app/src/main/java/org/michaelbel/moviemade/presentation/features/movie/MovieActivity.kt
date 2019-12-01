package org.michaelbel.moviemade.presentation.features.movie

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View.*
import android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.commitNow
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie.*
import org.michaelbel.data.remote.model.Movie
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.TmdbConfig
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_BACKDROP
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_SESSION_ID
import org.michaelbel.moviemade.ktx.displayWidth
import org.michaelbel.moviemade.ktx.statusBarHeight
import org.michaelbel.moviemade.ktx.toDp
import org.michaelbel.moviemade.ktx.toast
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.EXTRA_MOVIE
import org.michaelbel.moviemade.presentation.common.appbar.AppBarStateChangeListener
import org.michaelbel.moviemade.presentation.common.base.BaseActivity
import org.michaelbel.moviemade.presentation.listitem.TextListItem
import org.michaelbel.moviemade.presentation.widget.BottomSheetDialog
import javax.inject.Inject

class MovieActivity: BaseActivity(R.layout.activity_movie) {

    private lateinit var movie: Movie
    private lateinit var fragment: MovieFragment

    @Inject
    lateinit var preferences: SharedPreferences

    private val isSystemStatusBarShown: Boolean
        get() = window.decorView.systemUiVisibility and SYSTEM_UI_FLAG_FULLSCREEN == 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[application].createActivityComponent.inject(this)

        movie = intent.getSerializableExtra(EXTRA_MOVIE) as Movie

        fragment = MovieFragment.newInstance(movie)
        supportFragmentManager.commitNow { replace(container.id, fragment) }

        window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent40)

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {finish()}
        toolbar.title = null

        appBar.addOnOffsetChangedListener(object: AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarState) {
                toolbarTitle.text = if (state === AppBarState.COLLAPSED) movie.title else null
                toolbarTitle.isSelected = state === AppBarState.COLLAPSED
            }
        })

        /**
         * todo
         * supportActionBar?.title пока не получается использовать,
         * так как его перехватывает collapsingToolbarLayout.title.
         */
        toolbarTitle.text = movie.title

        if (!movie.backdropPath.isNullOrEmpty()) {
            Picasso.get().load(TmdbConfig.image(movie.backdropPath as String)).resize(displayWidth, 220F.toDp(applicationContext)).into(cover)
        }

        collapsingLayout.setContentScrimColor(ContextCompat.getColor(this, R.color.primary))

        val params = fullToolbar.layoutParams as FrameLayout.LayoutParams
        params.topMargin = statusBarHeight

        fullToolbar.setNavigationOnClickListener { onBackPressed() }

        fullImage.setOnClickListener {
            fullToolbar.visibility = if (isSystemStatusBarShown) GONE else VISIBLE
            showSystemStatusBar(isSystemStatusBarShown.not())
        }

        cover.setOnLongClickListener {
            val sessionId: String? = preferences.getString(KEY_SESSION_ID, "")

            if (!sessionId.isNullOrEmpty() && !movie.backdropPath.isNullOrEmpty()) {
                val dialog = BottomSheetDialog(top = 8F, bottom = 8F)

                val listItem = TextListItem(TextListItem.Data(text = R.string.set_as_background, divider = false, medium = false))
                listItem.listener = object: TextListItem.Listener {
                    override fun onClick() {
                        preferences.edit().putString(KEY_ACCOUNT_BACKDROP, TmdbConfig.image(movie.backdropPath as String)).apply()
                        toast(R.string.msg_done)
                        dialog.dismiss()
                    }
                }

                dialog.addItem(listItem)
                dialog.show(supportFragmentManager, dialog.tag + "1")
                return@setOnLongClickListener true
            }
            return@setOnLongClickListener false
        }
    }

    override fun onBackPressed() {
        if (fragment.imageAnimator != null && fragment.imageAnimator?.isLeaving == false) {
            fragment.imageAnimator?.exit(true)
        } else {
            super.onBackPressed()
        }
    }

    fun showSystemStatusBar(state: Boolean) {
        val flags = SYSTEM_UI_FLAG_HIDE_NAVIGATION or SYSTEM_UI_FLAG_FULLSCREEN or SYSTEM_UI_FLAG_IMMERSIVE
        window.decorView.systemUiVisibility = if (state) 0 else flags
    }
}