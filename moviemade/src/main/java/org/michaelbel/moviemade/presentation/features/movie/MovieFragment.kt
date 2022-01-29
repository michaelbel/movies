package org.michaelbel.moviemade.presentation.features.movie

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import dagger.hilt.android.AndroidEntryPoint
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.app.analytics.Analytics
import org.michaelbel.moviemade.app.ktx.isDarkTheme
import org.michaelbel.moviemade.databinding.FragmentMovieBinding
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment: Fragment(R.layout.fragment_movie) {

    @Inject lateinit var factory: MovieModel.Factory

    private val binding: FragmentMovieBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /**
         * windowLightStatusBar: если true иконки черного цвета, если false белого.
         */
        /*val appbarListener = object: AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarState) {
                when (state) {
                    AppBarState.EXPANDED, AppBarState.IDLE -> {
                        requireActivity().window.setLightStatusBar(false)
                    }
                    AppBarState.COLLAPSED -> {
                        requireActivity().window.setLightStatusBar(!requireContext().isDarkTheme)
                    }
                }
            }
        })*/
    }

    @Inject
    fun trackScreen(analytics: Analytics) {
        analytics.trackScreen(MovieFragment::class.simpleName)
    }

    /*private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.topMargin = requireContext().statusBarHeight
            WindowInsetsCompat.CONSUMED
        }
    }*/

    private fun extractPaletteColor(bitmap: Bitmap) {
        Palette.from(bitmap).generate { palette ->
            val defaultColor: Int = ContextCompat.getColor(requireContext(), R.color.Surface)
            val paletteColor: Int = if (requireContext().isDarkTheme) {
                palette?.getDarkVibrantColor(defaultColor)
            } else {
                palette?.getLightMutedColor(defaultColor)
            } ?: ContextCompat.getColor(requireContext(), R.color.Surface)
            binding.collapsingToolbarLayout.setContentScrimColor(paletteColor)
            binding.scrollView.setBackgroundColor(paletteColor)
        }
    }

    /*private fun setMovieInitialData() = with(args.movie) {
        val imageLoader = ImageLoader(requireContext())
        val imageRequest = ImageRequest.Builder(requireContext())
            .data(image(backdropPathSafe))
            .target {
                val bitmap = (it as BitmapDrawable).bitmap
                binding.backdropImageView.setImageBitmap(bitmap)

                val decodedBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                extractPaletteColor(decodedBitmap)
            }
            .build()
        imageLoader.enqueue(imageRequest)

        binding.titleTextView.text = title
        binding.detailsTextView.text = String.format(getString(R.string.movie_details_text, releaseYear, genres))
        binding.ratingTextView.text = voteAverage.toString()
        binding.overviewTextView.text = overviewSafe
    }*/

    /*private fun shareMovie(menuItem: MenuItem): Boolean {
        val shareIcon: Drawable = menuItem.icon
        if (shareIcon is Animatable) {
            (shareIcon as Animatable).start()
        }

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                String.format(Locale.US, TMDB_MOVIE, args.movie.id)
            )
        }
        startActivity(Intent.createChooser(intent, getString(R.string.share_via)))
        return true
    }*/

    private fun renderAdView() {
        val onInitializationCompleteListener = OnInitializationCompleteListener { status -> }

        val adViewListener = object: AdListener() {
            override fun onAdClosed() {}

            override fun onAdFailedToLoad(error: LoadAdError) {}

            override fun onAdOpened() {}

            override fun onAdLoaded() {
                binding.adView.isVisible = true
            }

            override fun onAdClicked() {}

            override fun onAdImpression() {}
        }

        MobileAds.initialize(requireContext(), onInitializationCompleteListener)

        val adRequest: AdRequest = AdRequest.Builder().build()
        binding.adView.run {
            loadAd(adRequest)
            adListener = adViewListener
        }
    }
}