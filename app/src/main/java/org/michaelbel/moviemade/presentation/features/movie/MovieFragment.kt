package org.michaelbel.moviemade.presentation.features.movie

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
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
import kotlinx.android.synthetic.main.item_crew.*
import kotlinx.android.synthetic.main.item_genre.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.TmdbConfig.IMDB_MOVIE
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_IMAGE
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_MOVIE
import org.michaelbel.moviemade.core.ViewUtil
import org.michaelbel.moviemade.core.customtabs.Browser
import org.michaelbel.moviemade.core.entity.Country
import org.michaelbel.moviemade.core.entity.Genre
import org.michaelbel.moviemade.core.entity.Mark
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.MoviesResponse.Companion.RECOMMENDATIONS
import org.michaelbel.moviemade.core.entity.MoviesResponse.Companion.SIMILAR
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_ID
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_SESSION_ID
import org.michaelbel.moviemade.core.text.SpannableUtil
import org.michaelbel.moviemade.core.time.DateUtil
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.ContainerActivity
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.FRAGMENT_NAME
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.KEYWORDS
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.REVIEWS
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.TRAILERS
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.search.SearchActivity
import org.michaelbel.moviemade.presentation.features.search.SearchActivity.Companion.EXTRA_QUERY
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MovieFragment: BaseFragment(), MovieContract.View {

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

    private var posterPath: String = ""

    private lateinit var movie: Movie
    private lateinit var crewAdapter: CrewAdapter
    private lateinit var genresAdapter: GenresAdapter

    //AdView adView;

    var imageAnimator: ViewsTransitionAnimator<*>? = null
    private lateinit var fullBackground: View
    private lateinit var fullToolbar: Toolbar
    private lateinit var fullImage: GestureImageView

    @Inject
    lateinit var preferences: SharedPreferences

    @Inject
    lateinit var presenter: MovieContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application as App].createFragmentComponent().inject(this)
        presenter.attach(this)
        sessionId = preferences.getString(KEY_SESSION_ID, "") ?: ""
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        actionMenu = menu
        menuShare = menu.add(R.string.share).setIcon(R.drawable.ic_anim_share).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        menuTmdb = menu.add(R.string.view_on_tmdb).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when {
            item === menuShare -> {
                val icon = actionMenu!!.getItem(0).icon
                if (icon is Animatable) {
                    (icon as Animatable).start()
                }

                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, String.format(Locale.US, TMDB_MOVIE, movie.id))
                startActivity(Intent.createChooser(intent, getString(R.string.share_via)))
            }
            item === menuTmdb ->
                Browser.openUrl(requireContext(), String.format(Locale.US, TMDB_MOVIE, movie.id))
            item === menuImdb ->
                Browser.openUrl(requireContext(), String.format(Locale.US, IMDB_MOVIE, imdbId))
            item === menuHomepage -> Browser.openUrl(requireContext(), homepage!!)
        }

        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fullBackground = (requireActivity() as MovieActivity).fullBackground
        fullToolbar = (requireActivity() as MovieActivity).fullToolbar
        fullImage = (requireActivity() as MovieActivity).fullImage
        setHasOptionsMenu(true)
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

        favoritesBtn.visibility = GONE
        favoritesBtn.visibility = GONE

        genresAdapter = GenresAdapter()
        genresList.adapter = genresAdapter
        genresList.layoutManager = ChipsLayoutManager.newBuilder(requireContext()).setOrientation(ChipsLayoutManager.HORIZONTAL).build()

        crewAdapter = CrewAdapter()
        crewList.adapter = crewAdapter
        crewList.layoutManager = LinearLayoutManager(requireContext())

        /*AdRequest adRequestBuilder = new AdRequest.Builder()
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .addTestDevice(getString(R.string.device_test_id))
            .build();

        adView.loadAd(adRequestBuilder);
        adView.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                switch (errorCode){
                    case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                        Timber.d("onAdFailedToLoad banner ERROR_CODE_INTERNAL_ERROR");
                        break;
                    case AdRequest.ERROR_CODE_INVALID_REQUEST:
                        Timber.d("onAdFailedToLoad banner ERROR_CODE_INVALID_REQUEST");
                        break;
                    case AdRequest.ERROR_CODE_NETWORK_ERROR:
                        Timber.d("onAdFailedToLoad banner ERROR_CODE_NETWORK_ERROR");
                        break;
                    case AdRequest.ERROR_CODE_NO_FILL:
                        Timber.d("onAdFailedToLoad banner ERROR_CODE_NO_FILL");
                        break;
                }
            }
        });*/

        favoritesBtn.setOnClickListener {
            presenter.markFavorite(sessionId, preferences.getInt(KEY_ACCOUNT_ID, 0), movie.id, !favorite)
        }

        watchlistBtn.setOnClickListener {
            presenter.addWatchlist(sessionId, preferences.getInt(KEY_ACCOUNT_ID, 0), movie.id, !watchlist)
        }

        poster.setOnClickListener {
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
            val intent = Intent(requireContext(), ContainerActivity::class.java)
            intent.putExtra(FRAGMENT_NAME, TRAILERS)
            intent.putExtra(EXTRA_MOVIE, movie)
            startActivity(intent)
        }

        reviewsText.setOnClickListener {
            val intent = Intent(requireContext(), ContainerActivity::class.java)
            intent.putExtra(FRAGMENT_NAME, REVIEWS)
            intent.putExtra(EXTRA_MOVIE, movie)
            startActivity(intent)
        }

        keywordsText.setOnClickListener {
            val intent = Intent(requireContext(), ContainerActivity::class.java)
            intent.putExtra(FRAGMENT_NAME, KEYWORDS)
            intent.putExtra(EXTRA_MOVIE, movie)
            startActivity(intent)
        }

        similarText.setOnClickListener {
            val intent = Intent(requireContext(), ContainerActivity::class.java)
            intent.putExtra(FRAGMENT_NAME, SIMILAR)
            intent.putExtra(EXTRA_MOVIE, movie)
            startActivity(intent)
        }

        recommendationsText.setOnClickListener {
            val intent = Intent(requireContext(), ContainerActivity::class.java)
            intent.putExtra(FRAGMENT_NAME, RECOMMENDATIONS)
            intent.putExtra(EXTRA_MOVIE, movie)
            startActivity(intent)
        }

        movie = arguments?.getSerializable(EXTRA_MOVIE) as Movie
        if (movie != null) {
            //presenter.setDetailExtra(movie)
            movieExtra(movie)
            presenter.getDetails(sessionId, movie.id)
        }
    }

    private fun movieExtra(movie: Movie) {
        posterPath = movie.posterPath
        poster.visibility = VISIBLE
        Glide.with(requireContext())
                .load(String.format(Locale.US, TMDB_IMAGE, "w342", posterPath))
                .thumbnail(0.1F)
                .into(poster)

        titleText.text = movie.title
        titleText.customSelectionActionModeCallback = SelectableActionMode(titleText)

        overviewText.text = if (TextUtils.isEmpty(movie.overview)) getString(R.string.no_overview) else movie.overview
        overviewText.customSelectionActionModeCallback = SelectableActionMode(overviewText)

        ratingView.setRating(movie.voteAverage)
        ratingText.text = movie.voteAverage.toString()

        voteCountText.text = movie.voteCount.toString()

        if (movie.releaseDate == null) {
            //parent.removeView(dateIcon)
            //parent.removeView(dateText)
            infoLayout.removeView(dateLayout) // old layout
        } else {
            dateIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_calendar, R.color.iconActiveColor))
            dateText.text = DateUtil.formatReleaseDate(movie.releaseDate)
        }

        val list = ArrayList<Genre>()
        for (id in movie.genreIds) {
            list.add(Genre.getGenreById(id))
        }
        genresAdapter.setGenres(list)
    }

    override fun movie(movie: Movie) {
        if (movie.runtime == 0) {
            //parent.removeView(runtimeIcon)
            //parent.removeView(runtimeText)
            infoLayout.removeView(runtimeLayout) // old layout
        } else {
            runtimeText.text = getString(R.string.runtime, DateUtil.formatRuntime(movie.runtime), movie.runtime)
        }

        if (movie.countries.isEmpty()) {
            //parent.removeView(langIcon)
            //parent.removeView(langText)
            infoLayout.removeView(langLayout) // old layout
        } else {
            langText.text = formatCountries(movie.countries)
        }

        if (TextUtils.isEmpty(movie.tagline)) {
            //parent.removeView(taglineText)
            titleLayout.removeView(taglineText) // old layout
        } else {
            taglineText.text = movie.tagline
        }
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

    /*@Override
    public void onPause() {
        super.onPause();
        if (adView != null) {
            adView.pause();
        }
    }*/

    /*@Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }*/

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
        /*if (adView != null) {
            adView.destroy();
        }*/
    }

    override fun setURLs(imdbId: String, homepage: String) {
        this.imdbId = imdbId
        this.homepage = homepage

        if (!TextUtils.isEmpty(imdbId)) {
            menuImdb = actionMenu?.add(R.string.view_on_imdb)?.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER)
        }

        if (!TextUtils.isEmpty(homepage)) {
            menuHomepage = actionMenu?.add(R.string.view_homepage)?.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER)
        }
    }

    override fun setStates(fave: Boolean, watch: Boolean) {
        favorite = fave
        favoritesBtn.visibility = VISIBLE

        if (fave) {
            favoritesIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_heart, R.color.accent_blue))
            favoritesText.setTextColor(ContextCompat.getColor(requireContext(), R.color.accent_blue))
        } else {
            favoritesIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_heart_outline, R.color.textColorPrimary))
            favoritesText.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorPrimary))
        }

        watchlist = watch
        watchlistBtn.visibility = VISIBLE

        if (watch) {
            watchlistIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_bookmark, R.color.accent_blue))
            watchlistText.setTextColor(ContextCompat.getColor(requireContext(), R.color.accent_blue))
        } else {
            watchlistIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_bookmark_outline, R.color.textColorPrimary))
            watchlistText.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorPrimary))
        }
    }

    override fun onFavoriteChanged(mark: Mark) {
        when (mark.statusCode) {
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
    }

    override fun onWatchListChanged(mark: Mark) {
        when (mark.statusCode) {
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
    }

    override fun setCredits(casts: String, directors: String, writers: String, producers: String) {
        val crewList = ArrayList<Credits>()
        crewList.add(Credits(getString(R.string.starring), getString(R.string.starring, casts)))
        crewList.add(Credits(getString(R.string.directed), getString(R.string.directed, directors)))
        crewList.add(Credits(getString(R.string.written), getString(R.string.written, writers)))
        crewList.add(Credits(getString(R.string.produced), getString(R.string.produced, producers)))
        crewAdapter.setCrew(crewList)
    }

    override fun setConnectionError() {
        Snackbar.make(parent, R.string.error_no_connection, Snackbar.LENGTH_SHORT).show()
    }

    override fun showComplete(movie: Movie) {}

    data class Credits(val category: String, val list: String)

    // Interface to select text and start in-app search.
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

                    val intent = Intent(view.context, SearchActivity::class.java)
                    intent.putExtra(EXTRA_QUERY, selectedText)
                    view.context.startActivity(intent)

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
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_crew, parent, false)
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
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
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