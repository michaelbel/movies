package org.michaelbel.moviemade.presentation.features.movie

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.alexvasilkov.gestures.Settings
import com.alexvasilkov.gestures.transition.GestureTransitions
import com.alexvasilkov.gestures.transition.ViewsTransitionAnimator
import com.alexvasilkov.gestures.views.GestureImageView
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.fragment_movie.*
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
import org.michaelbel.moviemade.core.local.SharedPrefs
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_ID
import org.michaelbel.moviemade.core.remote.AccountService
import org.michaelbel.moviemade.core.remote.MoviesService
import org.michaelbel.moviemade.core.text.SpannableUtil
import org.michaelbel.moviemade.core.time.DateUtil
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.movie.adapter.GenresAdapter
import java.util.*
import javax.inject.Inject

class MovieFragment: BaseFragment(), MovieContract.View {

    companion object {
        const val EXTRA_MOVIE = "movie"

        internal fun newInstance(movie: Movie): MovieFragment {
            val args = Bundle()
            args.putSerializable(EXTRA_MOVIE, movie)

            val fragment = MovieFragment()
            fragment.arguments = args
            return fragment
        }
    }

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

    private lateinit var adapter: GenresAdapter
    private lateinit var movie: Movie

    //AdView adView;

    var imageAnimator: ViewsTransitionAnimator<*>? = null
    private lateinit var fullBackground: View
    private lateinit var fullToolbar: Toolbar
    private lateinit var fullImage: GestureImageView

    @Inject
    lateinit var presenter: MovieContract.Presenter

    @Inject
    lateinit var moviesService: MoviesService

    @Inject
    lateinit var accountService: AccountService

    @Inject
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        App[requireActivity().application as App].createFragmentComponent().inject(this)
        presenter.attach(this)
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
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runtimeText.setText(R.string.loading)
        runtimeIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_clock,
                ContextCompat.getColor(requireContext(), R.color.iconActiveColor)))

        taglineText.setText(R.string.loading_tagline)

        starringText.text = SpannableUtil.boldAndColoredText(getString(R.string.starring), getString(R.string.starring, getString(R.string.loading)))
        directedText.text = SpannableUtil.boldAndColoredText(getString(R.string.directed), getString(R.string.directed, getString(R.string.loading)))
        writtenText.text = SpannableUtil.boldAndColoredText(getString(R.string.written), getString(R.string.written, getString(R.string.loading)))
        producedText.text = SpannableUtil.boldAndColoredText(getString(R.string.produced), getString(R.string.produced, getString(R.string.loading)))

        favoritesBtn.visibility = GONE
        favoritesBtn.visibility = GONE

        adapter = GenresAdapter()

        genresList.adapter = adapter
        genresList.layoutManager = ChipsLayoutManager.newBuilder(requireContext())
                .setOrientation(ChipsLayoutManager.HORIZONTAL).build()

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

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });*/

        favoritesBtn.setOnClickListener {
            val sessionId = preferences.getString(SharedPrefs.KEY_SESSION_ID, "") ?: ""
            presenter.markFavorite(sessionId, preferences.getInt(KEY_ACCOUNT_ID, 0), (requireActivity() as MovieActivity).movie.id, !favorite)
        }

        watchlistBtn.setOnClickListener {
            val sessionId = preferences.getString(SharedPrefs.KEY_SESSION_ID, "") ?: ""
            presenter.addWatchlist(sessionId, preferences.getInt(KEY_ACCOUNT_ID, 0), (requireActivity() as MovieActivity).movie.id, !watchlist)
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
            (requireActivity() as MovieActivity).startTrailers(movie)
        }

        reviewsText.setOnClickListener {
            (requireActivity() as MovieActivity).startReviews(movie)
        }

        keywordsText.setOnClickListener {
            (requireActivity() as MovieActivity).startKeywords(movie)
        }

        similarText.setOnClickListener {
            (requireActivity() as MovieActivity).startMovies(SIMILAR, movie)
        }

        recommendationsText.setOnClickListener {
            (requireActivity() as MovieActivity).startMovies(RECOMMENDATIONS, movie)
        }

        movie = arguments?.getSerializable(EXTRA_MOVIE) as Movie
        if (movie != null) {
            val sessionId = preferences.getString(SharedPrefs.KEY_SESSION_ID, "") ?: ""
            presenter.setDetailExtra(movie)
            presenter.getDetails(sessionId, movie.id)
        }
    }

    override fun movieExtra(movie: Movie) {
        posterPath = movie.posterPath
        poster.visibility = VISIBLE
        Glide.with(requireContext())
                .load(String.format(Locale.US, TMDB_IMAGE, "w342", posterPath))
                .thumbnail(0.1F)
                .into(poster)

        titleText.text = movie.title

        overviewText.text = if (TextUtils.isEmpty(movie.overview)) getString(R.string.no_overview) else movie.overview

        ratingView.setRating(movie.voteAverage)
        ratingText.text = movie.voteAverage.toString()

        voteCountText.text = movie.voteCount.toString()

        dateIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_calendar,
                ContextCompat.getColor(requireContext(), R.color.iconActiveColor)))
        dateText.text = DateUtil.formatReleaseDate(movie.releaseDate)

        val list = ArrayList<Genre>()
        for (id in movie.genreIds) {
            list.add(Genre.getGenreById(id))
        }
        adapter.setGenres(list)
    }

    override fun movie(movie: Movie) {
        runtimeText.text = getString(R.string.runtime, DateUtil.formatRuntime(movie.runtime), movie.runtime)

        langIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_earth,
                ContextCompat.getColor(requireContext(), R.color.iconActiveColor)))
        langText.text = formatCountries(movie.countries)

        if (TextUtils.isEmpty(movie.tagline)) {
            parent.removeView(taglineText)
            return
        }
        taglineText.text = movie.tagline
    }

    private fun formatCountries(countries: List<Country>): String {
        if (countries.isEmpty()) {
            return ""
        }

        val text = StringBuilder()
        for (country in countries) {
            text.append(country.name).append(", ")
        }

        text.delete(text.toString().length - 2, text.toString().length)
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
            favoritesIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_heart,
                    ContextCompat.getColor(requireContext(), R.color.accent_blue)))
            favoritesText.setTextColor(ContextCompat.getColor(requireContext(), R.color.accent_blue))
        } else {
            favoritesIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_heart_outline,
                    ContextCompat.getColor(requireContext(), R.color.textColorPrimary)))
            favoritesText.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorPrimary))
        }

        watchlist = watch
        watchlistBtn.visibility = VISIBLE

        if (watch) {
            watchlistIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_bookmark,
                    ContextCompat.getColor(requireContext(), R.color.accent_blue)))
            watchlistText.setTextColor(ContextCompat.getColor(requireContext(), R.color.accent_blue))
        } else {
            watchlistIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_bookmark_outline,
                    ContextCompat.getColor(requireContext(), R.color.textColorPrimary)))
            watchlistText.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorPrimary))
        }
    }

    override fun onFavoriteChanged(mark: Mark) {
        when (mark.statusCode) {
            Mark.ADDED -> {
                favoritesIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_heart,
                        ContextCompat.getColor(requireContext(), R.color.accent_blue)))
                favoritesText.setTextColor(ContextCompat.getColor(requireContext(), R.color.accent_blue))
                favorite = true
            }
            Mark.DELETED -> {
                favoritesIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_heart_outline,
                        ContextCompat.getColor(requireContext(), R.color.textColorPrimary)))
                favoritesText.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorPrimary))
                favorite = false
            }
        }
    }

    override fun onWatchListChanged(mark: Mark) {
        when (mark.statusCode) {
            Mark.ADDED -> {
                watchlistIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_bookmark,
                        ContextCompat.getColor(requireContext(), R.color.accent_blue)))
                watchlistText.setTextColor(ContextCompat.getColor(requireContext(), R.color.accent_blue))
                watchlist = true
            }
            Mark.DELETED -> {
                watchlistIcon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_bookmark_outline,
                        ContextCompat.getColor(requireContext(), R.color.textColorPrimary)))
                watchlistText.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorPrimary))
                watchlist = false
            }
        }
    }

    override fun setCredits(casts: String, directors: String, writers: String, producers: String) {
        starringText.text = SpannableUtil.boldAndColoredText(getString(R.string.starring), getString(R.string.starring, casts))
        directedText.text = SpannableUtil.boldAndColoredText(getString(R.string.directed), getString(R.string.directed, directors))
        writtenText.text = SpannableUtil.boldAndColoredText(getString(R.string.written), getString(R.string.written, writers))
        producedText.text = SpannableUtil.boldAndColoredText(getString(R.string.produced), getString(R.string.produced, producers))
    }

    override fun setConnectionError() {
        Snackbar.make(parent, R.string.error_no_connection, Snackbar.LENGTH_SHORT).show()
    }

    override fun showComplete(movie: Movie) {}
}