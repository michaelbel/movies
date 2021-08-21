package org.michaelbel.moviemade.presentation.features.movie

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View.*
import android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
import androidx.core.content.ContextCompat
import androidx.fragment.app.commitNow
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.ads.MobileAds
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import org.michaelbel.data.remote.model.Movie
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.TmdbConfig
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_BACKDROP
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_SESSION_ID
import org.michaelbel.moviemade.databinding.ActivityMovieBinding
import org.michaelbel.moviemade.ktx.displayWidth
import org.michaelbel.moviemade.ktx.toDp
import org.michaelbel.moviemade.ktx.toast
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.EXTRA_MOVIE
import org.michaelbel.moviemade.presentation.common.appbar.AppBarStateChangeListener
import org.michaelbel.moviemade.presentation.common.base.BaseActivity
import org.michaelbel.moviemade.presentation.listitem.TextListItem
import org.michaelbel.moviemade.presentation.widget.BottomSheetDialog
import javax.inject.Inject

@AndroidEntryPoint
class MovieActivity: BaseActivity(R.layout.activity_movie) {

    val binding: ActivityMovieBinding by viewBinding(R.id.parentLayout)

    private lateinit var movie: Movie
    private lateinit var fragment: MovieFragment

    @Inject lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this)

        movie = intent.getSerializableExtra(EXTRA_MOVIE) as Movie

        fragment = MovieFragment.newInstance(movie)
        supportFragmentManager.commitNow { replace(binding.container.id, fragment) }

        window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent40)

        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {finish()}
        binding.toolbar.title = null

        binding.appBar.addOnOffsetChangedListener(object: AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarState) {
                binding.toolbarTitle.text = if (state === AppBarState.COLLAPSED) movie.title else null
                binding.toolbarTitle.isSelected = state === AppBarState.COLLAPSED
            }
        })

        /**
         * todo
         * supportActionBar?.title пока не получается использовать,
         * так как его перехватывает collapsingToolbarLayout.title.
         */
        binding.toolbarTitle.text = movie.title

        if (!movie.backdropPath.isNullOrEmpty()) {
            Picasso.get().load(TmdbConfig.image(movie.backdropPath as String)).resize(displayWidth, 220F.toDp(applicationContext)).into(binding.cover)
        }

        binding.collapsingLayout.setContentScrimColor(ContextCompat.getColor(this, R.color.primary))

        binding.cover.setOnLongClickListener {
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
}