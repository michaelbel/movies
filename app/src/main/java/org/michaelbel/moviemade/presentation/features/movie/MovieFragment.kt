package org.michaelbel.moviemade.presentation.features.movie

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.MenuItem.SHOW_AS_ACTION_NEVER
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexvasilkov.gestures.Settings
import com.alexvasilkov.gestures.transition.GestureTransitions
import com.alexvasilkov.gestures.transition.ViewsTransitionAnimator
import com.alexvasilkov.gestures.views.GestureImageView
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.fragment_movie_old.*
import kotlinx.android.synthetic.main.listitem_crew.*
import kotlinx.android.synthetic.main.listitem_genre.*
import org.michaelbel.core.customtabs.Browser
import org.michaelbel.data.Movie
import org.michaelbel.data.Movie.Companion.RECOMMENDATIONS
import org.michaelbel.data.Movie.Companion.SIMILAR
import org.michaelbel.data.remote.model.Country
import org.michaelbel.data.remote.model.Genre
import org.michaelbel.data.remote.model.Mark
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.TmdbConfig.IMDB_MOVIE
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_IMAGE
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_MOVIE
import org.michaelbel.moviemade.core.ViewUtil
import org.michaelbel.moviemade.core.isNetworkAvailable
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_ID
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_SESSION_ID
import org.michaelbel.moviemade.core.startActivity
import org.michaelbel.moviemade.core.text.SpannableUtil
import org.michaelbel.moviemade.core.time.DateUtil
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.ContainerActivity
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.FRAGMENT_NAME
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.KEYWORDS
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.REVIEWS
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.TRAILERS
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.search.SearchActivity
import org.michaelbel.moviemade.presentation.features.search.SearchActivity.Companion.EXTRA_QUERY
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MovieFragment: BaseFragment() {

    companion object {
        private const val EXTRA_MOVIE = "movie"

        internal fun newInstance(movie: Movie): MovieFragment {
            val args = Bundle()
            args.putSerializable(EXTRA_MOVIE, movie)

            val fragment = MovieFragment()
            fragment.arguments = args
            return fragment
        }
    }

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
    private lateinit var crewAdapter: CrewAdapter
    private lateinit var genresAdapter: GenresAdapter
    private lateinit var viewModel: MovieModel

    var imageAnimator: ViewsTransitionAnimator<*>? = null
    private lateinit var fullBackground: View
    private lateinit var fullToolbar: Toolbar
    private lateinit var fullImage: GestureImageView

    @Inject
    lateinit var preferences: SharedPreferences

    @Inject
    lateinit var factory: MovieFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application as App].createFragmentComponent().inject(this)
        sessionId = preferences.getString(KEY_SESSION_ID, "") ?: ""
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        actionMenu = menu
        menuShare = menu.add(R.string.share).setIcon(R.drawable.ic_anim_share).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        menuTmdb = menu.add(R.string.view_on_tmdb).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item) {
            menuShare -> {
                val icon = actionMenu?.getItem(0)?.icon
                if (icon is Animatable) {
                    (icon as Animatable).start()
                }

                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, String.format(Locale.US, TMDB_MOVIE, movie.id))
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fullBackground = (requireActivity() as MovieActivity).fullBackground
        fullToolbar = (requireActivity() as MovieActivity).fullToolbar
        fullImage = (requireActivity() as MovieActivity).fullImage

        setHasOptionsMenu(true)
        viewModel = ViewModelProviders.of(requireActivity(), factory).get(MovieModel::class.java)
        return inflater.inflate(R.layout.fragment_movie_old, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        runtimeIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_clock, R.color.iconActiveColor))
        runtimeText.setText(R.string.loading)

        langIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_earth, R.color.iconActiveColor))
        langText.setText(R.string.loading)

        taglineText.setText(R.string.loading_tagline)
        taglineText.customSelectionActionModeCallback = SelectableActionMode(taglineText)

        genresAdapter = GenresAdapter()
        genresList.adapter = genresAdapter
        genresList.layoutManager = ChipsLayoutManager.newBuilder(requireContext()).setOrientation(ChipsLayoutManager.HORIZONTAL).build()

        crewAdapter = CrewAdapter()
        crewList.adapter = crewAdapter
        crewList.layoutManager = LinearLayoutManager(requireContext())

        favoritesBtn.visibility = if (sessionId.isEmpty()) GONE else VISIBLE
        favoritesBtn.setOnClickListener {
            viewModel.markFavorite(sessionId, preferences.getInt(KEY_ACCOUNT_ID, 0), movie.id, !favorite)
        }

        watchlistBtn.visibility = if (sessionId.isEmpty()) GONE else VISIBLE
        watchlistBtn.setOnClickListener {
            viewModel.addWatchlist(sessionId, preferences.getInt(KEY_ACCOUNT_ID, 0), movie.id, !watchlist)
        }

        poster.setOnClickListener {
            if (requireContext().isNetworkAvailable().not()) {
                return@setOnClickListener
            }

            imageAnimator = GestureTransitions.from<Any>(poster).into(fullImage)
            imageAnimator?.addPositionUpdateListener { position, isLeaving ->
                fullBackground.visibility = if (position == 0F) GONE else VISIBLE
                fullBackground.alpha = position

                fullToolbar.visibility = if (position == 0F) GONE else VISIBLE
                fullToolbar.alpha = position

                fullImage.visibility = if (position == 0F && isLeaving) GONE else VISIBLE

                Glide.with(requireContext())
                        .load(String.format(Locale.US, TMDB_IMAGE, "original", posterPath))
                        .thumbnail(0.1F)
                        .into(fullImage)

                if (position == 0F && isLeaving) {
                    (requireActivity() as MovieActivity).showSystemStatusBar(true)
                }
            }
            fullImage.controller.settings
                    .setGravity(Gravity.CENTER)
                    .setZoomEnabled(true)
                    .setAnimationsDuration(300L)
                    .setDoubleTapEnabled(true)
                    .setRotationEnabled(false)
                    .setFitMethod(Settings.Fit.INSIDE)
                    .setPanEnabled(true)
                    .setRestrictRotation(false)
                    .setOverscrollDistance(requireContext(), 32F, 32F)
                    .setOverzoomFactor(Settings.OVERZOOM_FACTOR).isFillViewport = true
            imageAnimator?.enterSingle(true)
        }

        trailersText.setOnClickListener {
            requireActivity().startActivity<ContainerActivity> {
                putExtra(FRAGMENT_NAME, TRAILERS)
                putExtra(EXTRA_MOVIE, movie)
            }
        }
        reviewsText.setOnClickListener {
            requireActivity().startActivity<ContainerActivity> {
                putExtra(FRAGMENT_NAME, REVIEWS)
                putExtra(EXTRA_MOVIE, movie)
            }
        }
        keywordsText.setOnClickListener {
            requireActivity().startActivity<ContainerActivity> {
                putExtra(FRAGMENT_NAME, KEYWORDS)
                putExtra(EXTRA_MOVIE, movie)
            }
        }
        similarText.setOnClickListener {
            requireActivity().startActivity<ContainerActivity> {
                putExtra(FRAGMENT_NAME, SIMILAR)
                putExtra(EXTRA_MOVIE, movie)
            }
        }
        recommendationsText.setOnClickListener {
            requireActivity().startActivity<ContainerActivity> {
                putExtra(FRAGMENT_NAME, RECOMMENDATIONS)
                putExtra(EXTRA_MOVIE, movie)
            }
        }

        movie = arguments?.getSerializable(EXTRA_MOVIE) as Movie
        movieExtra(movie)
        viewModel.movie(sessionId, movie.id)

        viewModel.movie.observe(viewLifecycleOwner, Observer {
            if (it.runtime == 0) {
                //parent.removeView(runtimeIcon)
                //parent.removeView(runtimeText)
                infoLayout.removeView(runtimeLayout) // old layout
            } else {
                runtimeText.text = getString(R.string.runtime, DateUtil.formatRuntime(it.runtime), it.runtime)
            }

            if (it.countries.isEmpty()) {
                //parent.removeView(langIcon)
                //parent.removeView(langText)
                infoLayout.removeView(langLayout) // old layout
            } else {
                langText.text = formatCountries(it.countries)
            }

            if (TextUtils.isEmpty(it.tagline)) {
                //parent.removeView(taglineText)
                titleLayout.removeView(taglineText) // old layout
            } else {
                taglineText.text = it.tagline
            }
        })
        viewModel.imdb.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty().not()) {
                imdbId = it
                menuImdb = actionMenu?.add(R.string.view_on_imdb)?.setShowAsActionFlags(SHOW_AS_ACTION_NEVER)
            }
        })
        viewModel.homepage.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty().not()) {
                homepage = it
                menuHomepage = actionMenu?.add(R.string.view_homepage)?.setShowAsActionFlags(SHOW_AS_ACTION_NEVER)
            }
        })
        viewModel.connectionError.observe(viewLifecycleOwner, Observer {
            Snackbar.make(parent, R.string.error_no_connection, Snackbar.LENGTH_SHORT).show()
        })
        viewModel.favoriteChange.observe(viewLifecycleOwner, Observer {
            when (it.statusCode) {
                Mark.ADDED -> {
                    favoritesIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_heart, R.color.accent_blue))
                    favoritesText.setTextColor(ContextCompat.getColor(requireContext(), R.color.accent_blue))
                    favorite = true
                }
                Mark.DELETED -> {
                    favoritesIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_heart_outline, R.color.textColorPrimary))
                    favoritesText.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorPrimary))
                    favorite = false
                }
            }
        })
        viewModel.watchlistChange.observe(viewLifecycleOwner, Observer {
            when (it.statusCode) {
                Mark.ADDED -> {
                    watchlistIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_bookmark, R.color.accent_blue))
                    watchlistText.setTextColor(ContextCompat.getColor(requireContext(), R.color.accent_blue))
                    watchlist = true
                }
                Mark.DELETED -> {
                    watchlistIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_bookmark_outline, R.color.textColorPrimary))
                    watchlistText.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorPrimary))
                    watchlist = false
                }
            }
        })
        viewModel.accountStates.observe(viewLifecycleOwner, Observer {
            favorite = it.favorite
            favoritesIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), if (favorite) R.drawable.ic_heart else R.drawable.ic_heart_outline, if (favorite) R.color.accent_blue else R.color.textColorPrimary))
            favoritesText.setTextColor(ContextCompat.getColor(requireContext(), if (favorite) R.color.accent_blue else R.color.textColorPrimary))

            watchlist = it.watchlist
            watchlistIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), if (watchlist) R.drawable.ic_bookmark else R.drawable.ic_bookmark_outline, if (watchlist) R.color.accent_blue else R.color.textColorPrimary))
            watchlistText.setTextColor(ContextCompat.getColor(requireContext(), if (watchlist) R.color.accent_blue else R.color.textColorPrimary))
        })
        viewModel.credit.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty().not()) {
                val crewList = ArrayList<Credits>()
                crewList.add(Credits(getString(R.string.starring), getString(R.string.starring, it["actors"])))
                crewList.add(Credits(getString(R.string.directed), getString(R.string.directed, it["directors"])))
                crewList.add(Credits(getString(R.string.written), getString(R.string.written, it["writers"])))
                crewList.add(Credits(getString(R.string.produced), getString(R.string.produced, it["producers"])))
                crewAdapter.setCrew(crewList)
            }
        })
    }

    private fun movieExtra(movie: Movie) {
        posterPath = movie.posterPath
        if (posterPath.isNullOrEmpty().not()) {
            poster.visibility = VISIBLE
            Glide.with(requireContext())
                    .load(String.format(Locale.US, TMDB_IMAGE, "w342", posterPath))
                    .thumbnail(0.1F)
                    .into(poster)
        }

        titleText.text = movie.title
        titleText.customSelectionActionModeCallback = SelectableActionMode(titleText)

        if (movie.overview.isNullOrEmpty()) {
            overviewText.text = getString(R.string.no_overview)
        } else {
            overviewText.text = movie.overview
            overviewText.customSelectionActionModeCallback = SelectableActionMode(overviewText)
        }

        ratingView.setRating(movie.voteAverage)
        ratingText.text = movie.voteAverage.toString()

        voteCountText.text = movie.voteCount.toString()

        if (movie.releaseDate.isNullOrEmpty()) {
            //parent.removeView(dateIcon)
            //parent.removeView(dateText)
            infoLayout.removeView(dateLayout) // old layout
        } else {
            movie.releaseDate?.let {
                dateIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_calendar, R.color.iconActiveColor))
                dateText.text = DateUtil.formatReleaseDate(it)
            }
        }

        val list = ArrayList<Genre>()
        movie.genreIds.forEach {
            list.add(Genre.getGenreById(it))
        }
        genresAdapter.setGenres(list)
    }

    private fun formatCountries(countries: List<Country>): String {
        if (countries.isEmpty()) {
            return ""
        }

        val text = StringBuilder()
        for (country in countries) {
            text.append(country.name).append(", ")
        }

        //text.delete(text.toString().length - 2, text.toString().length)
        text.delete(text.indexOf(","), text.length)
        return text.toString()
    }

    data class Credits(val category: String, val list: String)

    private inner class SelectableActionMode(private val view: TextView): ActionMode.Callback {
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when (item?.itemId) {
                R.id.action_search -> {
                    var min = 0
                    var max = view.text.length

                    if (view.isFocused) {
                        val start: Int = view.selectionStart
                        val end: Int = view.selectionEnd

                        min = Math.max(0, Math.min(start, end))
                        max = Math.max(0, Math.max(start, end))
                    }

                    val selectedText = view.text.subSequence(min, max).toString()

                    requireActivity().startActivity<SearchActivity> {
                        putExtra(EXTRA_QUERY, selectedText)
                    }

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

    private inner class CrewAdapter: RecyclerView.Adapter<CrewAdapter.CrewViewHolder>() {

        private val crew = ArrayList<Credits>()

        fun setCrew(result: List<Credits>) {
            crew.addAll(result)
            notifyItemRangeInserted(crew.size + 1, result.size)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem_crew, parent, false)
            return CrewViewHolder(view)
        }

        override fun onBindViewHolder(holder: CrewViewHolder, position: Int) {
            holder.bind(crew[position])
        }

        override fun getItemCount() = crew.size

        inner class CrewViewHolder(override val containerView: View):
                RecyclerView.ViewHolder(containerView), LayoutContainer {

            fun bind(crew: Credits) {
                crewText.text = SpannableUtil.boldAndColoredText(crew.category, crew.list)
            }
        }
    }

    private inner class GenresAdapter: RecyclerView.Adapter<GenresAdapter.GenresViewHolder>() {

        private val genres = ArrayList<Genre>()

        fun setGenres(results: List<Genre>) {
            genres.addAll(results)
            notifyItemRangeInserted(genres.size + 1, results.size)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem_genre, parent, false)
            return GenresViewHolder(view)
        }

        override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
            holder.bind(genres[position])
        }

        override fun getItemCount() = genres.size

        inner class GenresViewHolder(override val containerView: View):
                RecyclerView.ViewHolder(containerView), LayoutContainer {

            fun bind(genre: Genre) {
                chipName.text = genre.name
            }
        }
    }
}