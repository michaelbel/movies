package org.michaelbel.moviemade.presentation.features.movie

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.SharedPreferences
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MenuItem.SHOW_AS_ACTION_IF_ROOM
import android.view.MenuItem.SHOW_AS_ACTION_NEVER
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.michaelbel.core.Browser
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.data.remote.model.Country
import org.michaelbel.data.remote.model.Genre
import org.michaelbel.data.remote.model.Mark
import org.michaelbel.data.remote.model.Movie
import org.michaelbel.data.remote.model.Movie.Companion.RECOMMENDATIONS
import org.michaelbel.data.remote.model.Movie.Companion.SIMILAR
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.TmdbConfig.IMDB_MOVIE
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_IMAGE
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_MOVIE
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_ID
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_SESSION_ID
import org.michaelbel.moviemade.core.time.DateUtil
import org.michaelbel.moviemade.databinding.FragmentMovieOldBinding
import org.michaelbel.moviemade.ktx.launchAndRepeatWithViewLifecycle
import org.michaelbel.moviemade.ktx.loadImage
import org.michaelbel.moviemade.ktx.setIcon
import org.michaelbel.moviemade.ktx.startActivity
import org.michaelbel.moviemade.presentation.ContainerActivity
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.FRAGMENT_NAME
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.KEYWORDS
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.REVIEWS
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.TRAILERS
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.search.SearchActivity
import org.michaelbel.moviemade.presentation.features.search.SearchActivity.Companion.EXTRA_QUERY
import org.michaelbel.moviemade.presentation.listitem.CrewListItem
import org.michaelbel.moviemade.presentation.listitem.GenreListItem
import org.michaelbel.moviemade.presentation.widget.FaveButton
import java.util.Locale
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

@AndroidEntryPoint
class MovieFragment: BaseFragment(R.layout.fragment_movie_old) {

    private var sessionId: String = ""

    private var favorite: Boolean = false
    private var watchlist: Boolean = false
    private var actionMenu: Menu? = null
    private var menuShare: MenuItem? = null
    private var menuTmdb: MenuItem? = null
    private var menuImdb: MenuItem? = null
    private var menuHomepage: MenuItem? = null

    private var imdbId: String? = null
    private var homepage: String? = null
    private var posterPath: String? = null

    private lateinit var movie: Movie
    private var crewAdapter: ListAdapter? = null
    private var genresAdapter: ListAdapter? = null

    @Inject lateinit var preferences: SharedPreferences

    private val viewModel: MovieModel by viewModels()
    private val binding: FragmentMovieOldBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionId = preferences.getString(KEY_SESSION_ID, "") ?: ""
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        actionMenu = menu
        menuShare = menu.add(R.string.share).setIcon(R.drawable.ic_anim_share).setShowAsActionFlags(SHOW_AS_ACTION_IF_ROOM)
        menuTmdb = menu.add(R.string.view_on_tmdb).setShowAsActionFlags(SHOW_AS_ACTION_NEVER)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item) {
            menuShare -> {
                val icon = actionMenu?.getItem(0)?.icon
                if (icon is Animatable) {
                    (icon as Animatable).start()
                }

                val intent = Intent(ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, String.format(Locale.US, TMDB_MOVIE, movie.id))
                }
                startActivity(Intent.createChooser(intent, getString(R.string.share_via)))
            }
            menuTmdb -> Browser.openUrl(requireContext(), String.format(Locale.US, TMDB_MOVIE, movie.id))
            menuImdb -> {
                val imdbId = imdbId
                imdbId?.let { Browser.openUrl(requireContext(), String.format(Locale.US, IMDB_MOVIE, imdbId)) }
            }
            menuHomepage ->  {
                val homepageUrl = homepage
                homepageUrl?.let { Browser.openUrl(requireContext(), homepageUrl) }
            }
        }

        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.runtimeIcon.setIcon(R.drawable.ic_clock, R.color.iconActiveColor)
        binding.runtimeText.setText(R.string.loading)
        binding.langIcon.setIcon(R.drawable.ic_earth, R.color.iconActiveColor)
        binding.langText.setText(R.string.loading)
        binding.taglineText.setText(R.string.loading_tagline)
        binding.taglineText.customSelectionActionModeCallback = SelectableActionMode(binding.taglineText)

        genresAdapter = ListAdapter()
        binding.genresList.adapter = genresAdapter
        binding.genresList.layoutManager = ChipsLayoutManager.newBuilder(requireContext()).setOrientation(ChipsLayoutManager.HORIZONTAL).build()

        crewAdapter = ListAdapter()
        binding.crewList.adapter = crewAdapter
        binding.crewList.layoutManager = LinearLayoutManager(requireContext())

        binding.trailersText.setOnClickListener {
            requireActivity().startActivity<ContainerActivity> {
                putExtra(FRAGMENT_NAME, TRAILERS)
                putExtra(EXTRA_MOVIE, movie)
            }
        }
        binding.reviewsText.setOnClickListener {
            requireActivity().startActivity<ContainerActivity> {
                putExtra(FRAGMENT_NAME, REVIEWS)
                putExtra(EXTRA_MOVIE, movie)
            }
        }
        binding.keywordsText.setOnClickListener {
            requireActivity().startActivity<ContainerActivity> {
                putExtra(FRAGMENT_NAME, KEYWORDS)
                putExtra(EXTRA_MOVIE, movie)
            }
        }
        binding.similarText.setOnClickListener {
            requireActivity().startActivity<ContainerActivity> {
                putExtra(FRAGMENT_NAME, SIMILAR)
                putExtra(EXTRA_MOVIE, movie)
            }
        }
        binding.recommendationsText.setOnClickListener {
            requireActivity().startActivity<ContainerActivity> {
                putExtra(FRAGMENT_NAME, RECOMMENDATIONS)
                putExtra(EXTRA_MOVIE, movie)
            }
        }

        movie = arguments?.getSerializable(EXTRA_MOVIE) as Movie
        movieExtra(movie)
        viewModel.movie(sessionId, movie.id.toLong())

        renderAdView()

        launchAndRepeatWithViewLifecycle {
            launch {
                viewModel.movie.collect {
                    if (it.runtime == 0) {
                        //parent.removeView(runtimeIcon)
                        //parent.removeView(runtimeText)
                        binding.infoLayout.removeView(binding.runtimeLayout) // old layout
                    } else {
                        binding.runtimeText.text = getString(R.string.runtime, DateUtil.formatRuntime(it.runtime), it.runtime)
                    }

                    if (it.countries.isNullOrEmpty()) {
                        binding.langLayout.isGone = true
                    } else {
                        binding.langLayout.isVisible = true
                        binding.langText.text = formatCountries(it.countries)
                    }

                    if (TextUtils.isEmpty(it.tagline)) {
                        //parent.removeView(taglineText)
                        binding.titleLayout.removeView(binding.taglineText) // old layout
                    } else {
                        binding.taglineText.text = it.tagline
                    }
                }
            }
            launch {
                viewModel.imdb.collect {
                    if (!it.isNullOrEmpty()) {
                        imdbId = it
                        menuImdb = actionMenu?.add(R.string.view_on_imdb)?.setShowAsActionFlags(SHOW_AS_ACTION_NEVER)
                    }
                }
            }
            launch {
                viewModel.homepage.collect {
                    if (it.isNullOrEmpty().not()) {
                        homepage = it
                        menuHomepage = actionMenu?.add(R.string.view_homepage)?.setShowAsActionFlags(SHOW_AS_ACTION_NEVER)
                    }
                }
            }
            launch {
                viewModel.connectionError.collect {
                    Snackbar.make(binding.parent, R.string.error_no_connection, Snackbar.LENGTH_SHORT).show()
                }
            }
            launch {
                viewModel.favoriteChange.collect {
                    when (it.statusCode) {
                        Mark.ADDED -> {
                            favorite = true
                            binding.favoritesBtn.setData(FaveButton.Data(R.drawable.ic_heart, R.string.favorites, true), true)
                        }
                        Mark.DELETED -> {
                            favorite = false
                            binding.favoritesBtn.setData(FaveButton.Data(R.drawable.ic_heart_outline, R.string.favorites, false), true)
                        }
                    }
                }
            }
            launch {
                viewModel.watchlistChange.collect {
                    when (it.statusCode) {
                        Mark.ADDED -> {
                            watchlist = true
                            binding.watchlistBtn.setData(FaveButton.Data(R.drawable.ic_bookmark, R.string.watchlist, true), true)
                        }
                        Mark.DELETED -> {
                            watchlist = false
                            binding.watchlistBtn.setData(FaveButton.Data(R.drawable.ic_bookmark_outline, R.string.watchlist, false), true)
                        }
                    }
                }
            }
            launch {
                viewModel.accountStates.collect {
                    favorite = it.favorite
                    binding.favoritesBtn.setData(FaveButton.Data(if (favorite) R.drawable.ic_heart else R.drawable.ic_heart_outline, R.string.favorites, favorite), false)
                    binding.favoritesBtn.setOnClickListener { viewModel.markFavorite(sessionId, preferences.getLong(KEY_ACCOUNT_ID, 0L), movie.id.toLong(), !favorite) }
                    binding.favoritesBtn.visibility = if (sessionId.isEmpty()) GONE else VISIBLE

                    watchlist = it.watchlist
                    binding.watchlistBtn.setData(FaveButton.Data(if (watchlist) R.drawable.ic_bookmark else R.drawable.ic_bookmark_outline, R.string.watchlist, watchlist), false)
                    binding.watchlistBtn.setOnClickListener { viewModel.addWatchlist(sessionId, preferences.getLong(KEY_ACCOUNT_ID, 0L), movie.id.toLong(), !watchlist) }
                    binding.watchlistBtn.visibility = if (sessionId.isEmpty()) GONE else VISIBLE
                }
            }
            launch {
                viewModel.credit.collect {
                    val actors: String? = it.get(MovieModel.KEY_ACTORS)
                    val directors: String? = it.get(MovieModel.KEY_DIRECTORS)
                    val writers: String? = it.get(MovieModel.KEY_WRITERS)
                    val producers: String? = it.get(MovieModel.KEY_PRODUCERS)

                    val items = mutableListOf<ListItem>(
                        CrewListItem(CrewListItem.Data(R.string.starring_only, getString(R.string.starring, actors))),
                        CrewListItem(CrewListItem.Data(R.string.directed_only, getString(R.string.directed, directors))),
                        CrewListItem(CrewListItem.Data(R.string.written_only, getString(R.string.written, writers))),
                        CrewListItem(CrewListItem.Data(R.string.produced_only, getString(R.string.produced, producers)))
                    )
                    crewAdapter?.setItems(items)
                }
            }
        }
    }

    private fun movieExtra(movie: Movie) {
        posterPath = movie.posterPath
        if (posterPath.isNullOrEmpty().not()) {
            binding.poster.isVisible = true
            binding.poster.loadImage(String.format(Locale.US, TMDB_IMAGE, "w342", posterPath))
        }

        binding.titleText.text = movie.title
        binding.titleText.customSelectionActionModeCallback = SelectableActionMode(binding.titleText)

        if (movie.overview.isNullOrEmpty()) {
            binding.overviewText.text = getString(R.string.no_overview)
        } else {
            binding.overviewText.text = movie.overview
            binding.overviewText.customSelectionActionModeCallback = SelectableActionMode(binding.overviewText)
        }

        binding.ratingView.setRating(movie.voteAverage)
        binding.ratingText.text = movie.voteAverage.toString()

        binding.voteCountText.text = movie.voteCount.toString()

        if (movie.releaseDate.isNullOrEmpty()) {
            //parent.removeView(dateIcon)
            //parent.removeView(dateText)
            binding.infoLayout.removeView(binding.dateLayout) // old layout
        } else {
            movie.releaseDate?.let {
                binding.dateIcon.setIcon(R.drawable.ic_calendar, R.color.iconActiveColor)
                binding.dateText.text = DateUtil.formatReleaseDate(it)
            }
        }

        val items = ArrayList<ListItem>()
        movie.genreIds.forEach {
            val genre: Genre? = Genre.getGenreById(it)
            if (genre != null) {
                items.add(GenreListItem(genre))
            }
        }
        genresAdapter?.setItems(items)
    }

    private fun formatCountries(countries: List<Country>): String {
        if (countries.isEmpty()) {
            return ""
        }

        val text = StringBuilder()
        countries.forEach { text.append(it.name).append(", ") }

        //text.delete(text.toString().length - 2, text.toString().length)
        text.delete(text.indexOf(","), text.length)
        return text.toString()
    }

    private fun renderAdView() {
        val onInitializationCompleteListener = OnInitializationCompleteListener { status ->

        }

        val adViewListener = object: AdListener() {
            override fun onAdClosed() {
                Log.e("2580", "onAdClosed")
            }

            override fun onAdFailedToLoad(error: LoadAdError) {
                Log.e("2580", "onAdFailedToLoad $error")
            }

            override fun onAdOpened() {
                Log.e("2580", "onAdOpened")
            }

            override fun onAdLoaded() {
                Log.e("2580", "onAdLoaded")
            }

            override fun onAdClicked() {
                Log.e("2580", "onAdClicked")
            }

            override fun onAdImpression() {
                Log.e("2580", "onAdImpression")
            }
        }

        MobileAds.initialize(requireContext(), onInitializationCompleteListener)

        val adRequest: AdRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        binding.adView.adListener = adViewListener
    }

    private inner class SelectableActionMode(private val view: TextView): ActionMode.Callback {
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when (item?.itemId) {
                R.id.action_search -> {
                    var min = 0
                    var max = view.text.length

                    if (view.isFocused) {
                        val start: Int = view.selectionStart
                        val end: Int = view.selectionEnd

                        min = max(0, min(start, end))
                        max = max(0, max(start, end))
                    }

                    val selectedText = view.text.subSequence(min, max).toString()

                    requireActivity().startActivity<SearchActivity> { putExtra(EXTRA_QUERY, selectedText) }

                    mode?.finish()
                    return true
                }
            }
            return false
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.action_search, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

        override fun onDestroyActionMode(mode: ActionMode?) {}
    }

    companion object {
        private const val EXTRA_MOVIE = "movie"

        fun newInstance(movie: Movie): MovieFragment {
            return MovieFragment().apply {
                arguments = bundleOf(EXTRA_MOVIE to movie)
            }
        }
    }
}