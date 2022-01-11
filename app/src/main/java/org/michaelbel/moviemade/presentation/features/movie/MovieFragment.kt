package org.michaelbel.moviemade.presentation.features.movie

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Animatable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import androidx.transition.Transition
import androidx.transition.TransitionListenerAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.ImageLoader
import coil.request.ImageRequest
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.michaelbel.data.remote.model.Movie
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.TmdbConfig
import org.michaelbel.moviemade.databinding.FragmentMovieBinding
import org.michaelbel.moviemade.isDarkTheme
import org.michaelbel.moviemade.ktx.assistedViewModels
import org.michaelbel.moviemade.ktx.launchAndRepeatWithViewLifecycle
import org.michaelbel.moviemade.ktx.statusBarHeight
import org.michaelbel.moviemade.presentation.features.main.AppBarStateChangeListener
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment: Fragment(R.layout.fragment_movie) {

    @Inject lateinit var factory: MovieModel.Factory

    private val args: MovieFragmentArgs by navArgs()
    private val binding: FragmentMovieBinding by viewBinding()
    private val viewModel: MovieModel by assistedViewModels { factory.create(args.movie.id) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSharedElementTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyWindowInsets()
        setMovieInitialData()
        renderAdView()

        binding.appBar.addOnOffsetChangedListener(object: AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, expanded: Boolean) {
                //WindowInsetsControllerCompat(requireActivity().window, binding.root).isAppearanceLightStatusBars = state == AppBarState.COLLAPSED
                if (!requireContext().isDarkTheme) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        requireActivity().window.decorView.systemUiVisibility = if (expanded) 0 else View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    }
                    binding.toolbar.setNavigationIconTint(ContextCompat.getColor(requireContext(), if (expanded) android.R.color.white else android.R.color.black))
                    binding.toolbar.menu.getItem(0).icon.setTint(ContextCompat.getColor(requireContext(), if (expanded) android.R.color.white else android.R.color.black))
                }
            }
        })

        binding.toolbar.run {
            setNavigationOnClickListener { findNavController().popBackStack() }
            setOnMenuItemClickListener { menuItem -> shareMovie(menuItem) }
            menu.getItem(0).icon.setTint(ContextCompat.getColor(requireContext(), R.color.toolbarIconTint))
        }
        binding.toolbar.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = requireContext().statusBarHeight
        }
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = systemBars.top
            }
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun setupSharedElementTransition() {
        //sharedElementEnterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
            //TransitionInflater.from(context).inflateTransition(android.R.transition.fade)
                //.addListener(onTransitionEndListener())
    }

    private fun onTransitionEndListener() = object : TransitionListenerAdapter() {
        override fun onTransitionEnd(transition: Transition) {
            super.onTransitionEnd(transition)
            setupListeners()
        }
    }

    private fun extractPaletteColor(bitmap: Bitmap) {
        Palette.from(bitmap).generate { palette ->
            val defaultColor: Int = ContextCompat.getColor(requireContext(), R.color.contentScrimColor)
            val paletteColor: Int = if (requireContext().isDarkTheme) {
                palette?.getDarkVibrantColor(defaultColor)
            } else {
                palette?.getLightMutedColor(defaultColor)
            } ?: ContextCompat.getColor(requireContext(), R.color.contentScrimColor)
            binding.collapsingToolbarLayout.setContentScrimColor(paletteColor)
            binding.scrollView.setBackgroundColor(paletteColor)
        }
    }

    private fun setupListeners() {
        launchAndRepeatWithViewLifecycle {
            launch {
                viewModel.movieFlow.collect {
                    setData(it)
                }
            }
        }
    }

    private fun setMovieInitialData() = with(args.movie) {
        val imageLoader = ImageLoader(requireContext())
        val imageRequest = ImageRequest.Builder(requireContext())
            .data(TmdbConfig.image(backdropPathSafe))
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
    }

    private fun shareMovie(menuItem: MenuItem): Boolean {
        val shareIcon: Drawable = menuItem.icon
        if (shareIcon is Animatable) {
            (shareIcon as Animatable).start()
        }

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                String.format(Locale.US, TmdbConfig.TMDB_MOVIE, args.movie.id)
            )
        }
        startActivity(Intent.createChooser(intent, getString(R.string.share_via)))
        return true
    }

    private fun setData(movie: Movie) = with(movie) {

    }

    private fun renderAdView() {
        val onInitializationCompleteListener = OnInitializationCompleteListener { status ->

        }

        val adViewListener = object: AdListener() {
            override fun onAdClosed() {}

            override fun onAdFailedToLoad(error: LoadAdError) {}

            override fun onAdOpened() {}

            override fun onAdLoaded() {}

            override fun onAdClicked() {}

            override fun onAdImpression() {}
        }

        MobileAds.initialize(requireContext(), onInitializationCompleteListener)

        val adRequest: AdRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        binding.adView.adListener = adViewListener
    }
}