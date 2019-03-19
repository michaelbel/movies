package org.michaelbel.moviemade.presentation

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_lce.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.ViewUtil
import org.michaelbel.moviemade.core.entity.Keyword
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.MoviesResponse.Companion.FAVORITE
import org.michaelbel.moviemade.core.entity.MoviesResponse.Companion.NOW_PLAYING
import org.michaelbel.moviemade.core.entity.MoviesResponse.Companion.RECOMMENDATIONS
import org.michaelbel.moviemade.core.entity.MoviesResponse.Companion.SIMILAR
import org.michaelbel.moviemade.core.entity.MoviesResponse.Companion.WATCHLIST
import org.michaelbel.moviemade.core.local.BuildUtil
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_SESSION_ID
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.EXTRA_MOVIE
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.features.main.MainContract
import org.michaelbel.moviemade.presentation.features.main.MoviesAdapter
import org.michaelbel.moviemade.presentation.features.movie.MovieActivity
import javax.inject.Inject

/**
 * Display movies list with Toolbar.
 *
 * NowPlaying, Popular, TopRated, Upcoming
 * Watchlist, Favorite
 * Similar, Recommendations
 * Movies by Keyword
 */
class MoviesFragment2: BaseFragment(), MainContract.View, MoviesAdapter.Listener {

    companion object {
        private const val ARG_LIST = "list"
        private const val ARG_MOVIE = "movie"
        private const val ARG_KEYWORD = "keyword"
        private const val ARG_ACCOUNT_ID = "account_id"

        internal fun newInstance(list: String): MoviesFragment2 {
            val args = Bundle()
            args.putString(ARG_LIST, list)

            val fragment = MoviesFragment2()
            fragment.arguments = args
            return fragment
        }

        internal fun newInstance(list: String, movie: Movie): MoviesFragment2 {
            val args = Bundle()
            args.putString(ARG_LIST, list)
            args.putSerializable(ARG_MOVIE, movie)

            val fragment = MoviesFragment2()
            fragment.arguments = args
            return fragment
        }

        internal fun newInstance(keyword: Keyword): MoviesFragment2 {
            val args = Bundle()
            args.putSerializable(ARG_KEYWORD, keyword)

            val fragment = MoviesFragment2()
            fragment.arguments = args
            return fragment
        }

        internal fun newInstance(list: String, accountId: Int): MoviesFragment2 {
            val args = Bundle()
            args.putString(ARG_LIST, list)
            args.putInt(ARG_ACCOUNT_ID, accountId)

            val fragment = MoviesFragment2()
            fragment.arguments = args
            return fragment
        }
    }

    private var movie: Movie = Movie(id = 0, title = "")
    private var keyword: Keyword = Keyword(id = 0, name = "")

    private var accountId: Int = 0
    private var sessionId: String = ""

    private lateinit var list: String
    private lateinit var adapter: MoviesAdapter

    @Inject
    lateinit var preferences: SharedPreferences

    @Inject
    lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application as App].createFragmentComponent().inject(this)
        presenter.attach(this)
        adapter = MoviesAdapter(this)
        sessionId = preferences.getString(KEY_SESSION_ID, "") ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lce, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = arguments?.getString(ARG_LIST) ?: NOW_PLAYING
        if (arguments?.getSerializable(ARG_MOVIE) != null) {
            movie = arguments?.getSerializable(ARG_MOVIE) as Movie
        }
        if (arguments?.getSerializable(ARG_KEYWORD) != null) {
            keyword = arguments?.getSerializable(ARG_KEYWORD) as Keyword
        }

        accountId = arguments?.getInt(ARG_ACCOUNT_ID) ?: 0

        toolbar.title = when(list) {
            SIMILAR -> getString(R.string.similar_movies)
            RECOMMENDATIONS -> getString(R.string.recommendations)
            FAVORITE -> getString(R.string.favorites)
            WATCHLIST -> getString(R.string.watchlist)
            else -> {
                if (keyword.id != 0) {
                    keyword.name
                } else {
                    ""
                }
            }
        }

        if (movie.id != 0) {
            toolbar.subtitle = movie.title
        }
        toolbar.navigationIcon = ViewUtil.getIcon(requireContext(), R.drawable.ic_arrow_back, R.color.iconActiveColor)
        toolbar.setOnClickListener { recyclerView.smoothScrollToPosition(0) }
        toolbar.setNavigationOnClickListener { requireActivity().finish() }

        val spanCount = resources.getInteger(R.integer.movies_span_layout_count)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, resources.getDimension(R.dimen.movies_list_spacing).toInt()))
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && adapter.itemCount != 0) {
                    when {
                        keyword.id != 0 -> presenter.moviesNext(keyword.id)
                        list == WATCHLIST -> presenter.moviesWatchlistNext(accountId, sessionId)
                        list == FAVORITE -> presenter.moviesFavoriteNext(accountId, sessionId)
                        else -> presenter.moviesNext(movie.id, list)
                    }
                }
            }
        })

        emptyView.setOnClickListener { presenter.movies(movie.id, list) }

        when {
            keyword.id != 0 -> presenter.movies(keyword.id)
            list == FAVORITE -> presenter.moviesFavorite(accountId, sessionId)
            list == WATCHLIST -> presenter.moviesWatchlist(accountId, sessionId)
            else -> presenter.movies(movie.id, list)
        }
    }

    override fun loading(state: Boolean) {
        progressBar.visibility = if (state) VISIBLE else GONE
    }

    override fun content(results: List<Movie>) {
        emptyView.visibility = GONE
        adapter.addMovies(results)
    }

    override fun error(code: Int) {
        emptyView.visibility = VISIBLE
        emptyView.setMode(code)

        /*if (code == 1) {
            emptyView.setMode(EmptyViewMode.MODE_NO_MOVIES)
        } else {
            emptyView.setMode(EmptyViewMode.MODE_NO_CONNECTION)
        }*/

        if (BuildUtil.isApiKeyEmpty()) {
            emptyView.setValue(R.string.error_empty_api_key)
        }
    }

    override fun onMovieClick(movie: Movie) {
        val intent = Intent(requireContext(), MovieActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}