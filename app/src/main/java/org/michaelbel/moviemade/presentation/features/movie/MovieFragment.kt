package org.michaelbel.moviemade.presentation.features.movie

import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.View.*
import androidx.core.content.ContextCompat
import com.alexvasilkov.gestures.Settings
import com.alexvasilkov.gestures.transition.GestureTransitions
import com.alexvasilkov.gestures.transition.ViewsTransitionAnimator
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movie.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.consts.Code
import org.michaelbel.moviemade.core.consts.Genres
import org.michaelbel.moviemade.core.entity.Genre
import org.michaelbel.moviemade.core.entity.Mark
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.remote.AccountService
import org.michaelbel.moviemade.core.remote.MoviesService
import org.michaelbel.moviemade.core.utils.*
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.common.network.NetworkChangeReceiver
import org.michaelbel.moviemade.presentation.features.movie.adapter.GenresAdapter
import java.util.*
import javax.inject.Inject

class MovieFragment: BaseFragment(), MovieContract.View, NetworkChangeReceiver.Listener {

    private var favorite: Boolean = false
    private var watchlist: Boolean = false
    private var actionMenu: Menu? = null
    private var menuShare: MenuItem? = null
    private var menuTmdb: MenuItem? = null
    private var menuImdb: MenuItem? = null
    private var menuHomepage: MenuItem? = null

    private lateinit var parentView: View

    private var imdbId: String? = null

    private lateinit var homepage: String

    private var posterPath: String? = null
    private var connectionError: Boolean = false

    lateinit var networkChangeReceiver: NetworkChangeReceiver
    lateinit var adapter: GenresAdapter

    //@BindView(R.id.ad_view) AdView adView;

    var imageAnimator: ViewsTransitionAnimator<*>? = null

    lateinit var presenter: MovieContract.Presenter

    @Inject
    lateinit var moviesService: MoviesService

    @Inject
    lateinit var accountService: AccountService

    @Inject
    lateinit var preferences: SharedPreferences

    // Fixme: add newInstance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        App[requireActivity().application as App].createFragmentComponent().inject(this)

        networkChangeReceiver = NetworkChangeReceiver(this)
        requireContext().registerReceiver(networkChangeReceiver, IntentFilter(NetworkChangeReceiver.INTENT_ACTION))

        presenter = MoviePresenter(this, moviesService, accountService, preferences)
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
                intent.putExtra(Intent.EXTRA_TEXT, String.format(Locale.US, TMDB_MOVIE, (requireActivity() as MovieActivity).movie.id))
                startActivity(Intent.createChooser(intent, getString(R.string.share_via)))
            }
            item === menuTmdb -> Browser.openUrl(requireContext(), String.format(Locale.US, TMDB_MOVIE, (requireActivity() as MovieActivity).movie.id))
            item === menuImdb -> Browser.openUrl(requireContext(), String.format(Locale.US, IMDB_MOVIE, imdbId))
            item === menuHomepage -> Browser.openUrl(requireContext(), homepage)
        }

        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        parentView = inflater.inflate(R.layout.fragment_movie, container, false)
        return parentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runtime_text.setText(R.string.loading)
        runtime_icon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_clock,
                ContextCompat.getColor(requireContext(), R.color.iconActiveColor)))

        tagline_text.setText(R.string.loading_tagline)

        starring_text.text = SpannableUtil.boldAndColoredText(getString(R.string.starring), getString(R.string.starring, getString(R.string.loading)))
        directed_text.text = SpannableUtil.boldAndColoredText(getString(R.string.directed), getString(R.string.directed, getString(R.string.loading)))
        written_text.text = SpannableUtil.boldAndColoredText(getString(R.string.written), getString(R.string.written, getString(R.string.loading)))
        produced_text.text = SpannableUtil.boldAndColoredText(getString(R.string.produced), getString(R.string.produced, getString(R.string.loading)))

        favorites_btn.visibility = GONE
        favorites_btn.visibility = GONE

        adapter = GenresAdapter()

        genres_recycler_view.adapter = adapter
        genres_recycler_view.layoutManager = ChipsLayoutManager.newBuilder(requireContext())
                .setOrientation(ChipsLayoutManager.HORIZONTAL).build()

        //AppCompatButton trailersBtn = view.findViewById(R.id.trailers_btn);
        //trailersBtn.setOnClickListener(v -> activity.startTrailers(activity.getMovie()));

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

        favorites_btn.setOnClickListener {
            presenter.markFavorite(preferences.getInt(KEY_ACCOUNT_ID, 0), (requireActivity() as MovieActivity).movie.id, !favorite)
        }

        watchlist_btn.setOnClickListener {
            presenter.addWatchlist(preferences.getInt(KEY_ACCOUNT_ID, 0), (requireActivity() as MovieActivity).movie.id, !watchlist)
        }

        poster.setOnClickListener {
            imageAnimator = GestureTransitions.from<Any>(poster).into((requireActivity() as MovieActivity).getFullImage())
            imageAnimator!!.addPositionUpdateListener { position, isLeaving ->
                (requireActivity() as MovieActivity).getFullBackground().visibility = if (position == 0F) INVISIBLE else VISIBLE
                (requireActivity() as MovieActivity).getFullBackground().alpha = position

                (requireActivity() as MovieActivity).getFullBackground().visibility = if (position == 0F) INVISIBLE else VISIBLE
                (requireActivity() as MovieActivity).getFullBackground().alpha = position

                (requireActivity() as MovieActivity).getFullImage().visibility = if (position == 0F && isLeaving) INVISIBLE else VISIBLE

                Glide.with(requireContext())
                        .load(String.format(Locale.US, TMDB_IMAGE, "original", posterPath))
                        .thumbnail(0.1F)
                        .into((requireActivity() as MovieActivity).getFullImage())

                if (position == 0F && isLeaving) {
                    (requireActivity() as MovieActivity).showSystemStatusBar(true)
                }
            }
            (requireActivity() as MovieActivity).getFullImage().controller.settings
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
            imageAnimator!!.enterSingle(true)
        }

        trailers_btn.setOnClickListener {
            (requireActivity() as MovieActivity).startTrailers((requireActivity() as MovieActivity).movie)
        }

        reviews_text.setOnClickListener {
            (requireActivity() as MovieActivity).startReviews((requireActivity() as MovieActivity).movie)
        }

        keywords_text.setOnClickListener {
            (requireActivity() as MovieActivity).startKeywords((requireActivity() as MovieActivity).movie)
        }

        similar_text.setOnClickListener {
            (requireActivity() as MovieActivity).startSimilarMovies((requireActivity() as MovieActivity).movie)
        }

        recommendations_text.setOnClickListener {
            (requireActivity() as MovieActivity).startRcmdsMovies((requireActivity() as MovieActivity).movie)
        }
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
        requireContext().unregisterReceiver(networkChangeReceiver)
        presenter.destroy()
        /*if (adView != null) {
            adView.destroy();
        }*/
    }

    override fun setPoster(posterPath: String) {
        this.posterPath = posterPath
        poster.visibility = VISIBLE
        Glide.with(requireContext())
                .load(String.format(Locale.US, TMDB_IMAGE, "w342", posterPath))
                .thumbnail(0.1F).into(poster)
    }

    override fun setMovieTitle(title: String) {
        title_text.text = title
    }

    override fun setOverview(overview: String) {
        if (TextUtils.isEmpty(overview)) {
            overview_text.setText(R.string.no_overview)
            return
        }

        overview_text.text = overview
    }

    override fun setVoteAverage(voteAverage: Float) {
        rating_view.setRating(voteAverage)
        rating_text.text = voteAverage.toString()
    }

    override fun setVoteCount(voteCount: Int) {
        vote_count_text.text = voteCount.toString()
    }

    override fun setReleaseDate(releaseDate: String) {
        if (TextUtils.isEmpty(releaseDate)) {
            info_layout.removeView(date_layout)
            return
        }

        release_date_icon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_calendar,
                ContextCompat.getColor(activity!!, R.color.iconActiveColor)))
        release_date_text.text = releaseDate
    }

    override fun setOriginalLanguage(originalLanguage: String) {
        if (TextUtils.isEmpty(originalLanguage)) {
            info_layout.removeView(lang_layout)
            return
        }

        lang_icon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_earth,
                ContextCompat.getColor(requireContext(), R.color.iconActiveColor)))
        lang_text.text = originalLanguage
    }

    override fun setRuntime(runtime: String) {
        runtime_text.text = runtime
    }

    override fun setTagline(tagline: String) {
        if (TextUtils.isEmpty(tagline)) {
            title_layout.removeView(tagline_text)
            return
        }

        tagline_text.text = tagline
    }

    override fun setURLs(imdbId: String, homepage: String) {
        this.imdbId = imdbId
        this.homepage = homepage

        if (!TextUtils.isEmpty(imdbId)) {
            menuImdb = actionMenu!!.add(R.string.view_on_imdb).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER)
        }

        if (!TextUtils.isEmpty(homepage)) {
            menuHomepage = actionMenu!!.add(R.string.view_homepage).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER)
        }
    }

    override fun setStates(fave: Boolean, watch: Boolean) {
        favorite = fave
        watchlist = watch

        favorites_btn.visibility = VISIBLE
        watchlist_btn.visibility = VISIBLE

        if (fave) {
            favorites_icon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_heart,
                    ContextCompat.getColor(requireContext(), R.color.accent_blue)))
            favorites_text.setTextColor(ContextCompat.getColor(requireContext(), R.color.accent_blue))
        } else {
            favorites_icon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_heart_outline,
                    ContextCompat.getColor(requireContext(), R.color.textColorPrimary)))
            favorites_text.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorPrimary))
        }

        if (watch) {
            watchlist_icon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_bookmark,
                    ContextCompat.getColor(requireContext(), R.color.accent_blue)))
            watchlist_text.setTextColor(ContextCompat.getColor(requireContext(), R.color.accent_blue))
        } else {
            watchlist_icon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_bookmark_outline,
                    ContextCompat.getColor(requireContext(), R.color.textColorPrimary)))
            watchlist_text.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorPrimary))
        }
    }

    override fun onFavoriteChanged(mark: Mark) {
        when (mark.statusCode) {
            Code.ADDED -> {
                favorites_icon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_heart,
                        ContextCompat.getColor(requireContext(), R.color.accent_blue)))
                favorites_text.setTextColor(ContextCompat.getColor(requireContext(), R.color.accent_blue))
                favorite = true
            }
            Code.DELETED -> {
                favorites_icon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_heart_outline,
                        ContextCompat.getColor(requireContext(), R.color.textColorPrimary)))
                favorites_text.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorPrimary))
                favorite = false
            }
        }
    }

    override fun onWatchListChanged(mark: Mark) {
        when (mark.statusCode) {
            Code.ADDED -> {
                watchlist_icon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_bookmark,
                        ContextCompat.getColor(requireContext(), R.color.accent_blue)))
                watchlist_text.setTextColor(ContextCompat.getColor(requireContext(), R.color.accent_blue))
                watchlist = true
            }
            Code.DELETED -> {
                watchlist_icon.setImageDrawable(ViewUtil.getIcon(requireContext(), R.drawable.ic_bookmark_outline,
                        ContextCompat.getColor(requireContext(), R.color.textColorPrimary)))
                watchlist_text.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorPrimary))
                watchlist = false
            }
        }
    }

    override fun setCredits(casts: String, directors: String, writers: String, producers: String) {
        starring_text.text = SpannableUtil.boldAndColoredText(getString(R.string.starring), getString(R.string.starring, casts))
        directed_text.text = SpannableUtil.boldAndColoredText(getString(R.string.directed), getString(R.string.directed, directors))
        written_text.text = SpannableUtil.boldAndColoredText(getString(R.string.written), getString(R.string.written, writers))
        produced_text.text = SpannableUtil.boldAndColoredText(getString(R.string.produced), getString(R.string.produced, producers))
    }

    override fun setGenres(genreIds: List<Int>) {
        val list = ArrayList<Genre>()
        for (id in genreIds) {
            list.add(Genres.getGenreById(id)!!)
        }

        adapter.setGenres(list)
    }

    override fun setConnectionError() {
        Snackbar.make(parentView, R.string.error_no_connection, Snackbar.LENGTH_SHORT).show()
        connectionError = true
    }

    override fun showComplete(movie: Movie) {
        connectionError = false
    }

    override fun onNetworkChanged() {
        if (connectionError) {
            presenter.getDetails((requireActivity() as MovieActivity).movie.id)
        }
    }

    /*private void sendEvent() {
        ((Moviemade) activity.getApplication()).rxBus2.send(new Events.DeleteMovieFromFavorite(activity.getMovie().getId()));
    }*/
}