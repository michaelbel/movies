package org.michaelbel.moviemade.presentation.features.main

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_lce.*
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.data.remote.model.Keyword
import org.michaelbel.data.remote.model.Movie
import org.michaelbel.data.remote.model.Movie.Companion.FAVORITE
import org.michaelbel.data.remote.model.Movie.Companion.NOW_PLAYING
import org.michaelbel.data.remote.model.Movie.Companion.RECOMMENDATIONS
import org.michaelbel.data.remote.model.Movie.Companion.SIMILAR
import org.michaelbel.data.remote.model.Movie.Companion.WATCHLIST
import org.michaelbel.domain.MoviesRepository
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.local.BuildUtil
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_SESSION_ID
import org.michaelbel.moviemade.ktx.getIcon
import org.michaelbel.moviemade.ktx.getViewModel
import org.michaelbel.moviemade.ktx.reObserve
import org.michaelbel.moviemade.ktx.startActivity
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.EXTRA_MOVIE
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
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
class MoviesFragment2: BaseFragment(R.layout.fragment_lce) {

    companion object {
        private const val ARG_LIST = "list"
        private const val ARG_MOVIE = "movie"
        private const val ARG_KEYWORD = "keyword"
        private const val ARG_ACCOUNT_ID = "account_id"

        fun newInstance(list: String): MoviesFragment2 {
            return MoviesFragment2().apply {
                arguments = bundleOf("list" to list)
            }
        }

        fun newInstance(list: String, movie: Movie): MoviesFragment2 {
            return MoviesFragment2().apply {
                arguments = bundleOf("list" to list, "movie" to movie)
            }
        }

        fun newInstance(keyword: Keyword): MoviesFragment2 {
            return MoviesFragment2().apply {
                arguments = bundleOf("keyword" to keyword)
            }
        }

        fun newInstance(list: String, accountId: Int): MoviesFragment2 {
            return MoviesFragment2().apply {
                arguments = bundleOf("list" to list, ARG_ACCOUNT_ID to accountId)
            }
        }
    }

    private var movie: Movie? = null
    private var movieId: Long = 0L
    private var keyword: Keyword = Keyword(id = 0, name = "")

    private var accountId: Long = 0L
    private var sessionId: String = ""

    private lateinit var list: String
    private lateinit var adapter: ListAdapter

    @Inject lateinit var repository: MoviesRepository
    @Inject lateinit var preferences: SharedPreferences

    private val viewModel: MoviesModel by lazy { getViewModel { MoviesModel(repository) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application as App].createFragmentComponent.inject(this)
        sessionId = preferences.getString(KEY_SESSION_ID, "") ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list = arguments?.getString(ARG_LIST) ?: NOW_PLAYING
        if (arguments?.getSerializable(ARG_MOVIE) != null) {
            movie = arguments?.getSerializable(ARG_MOVIE) as Movie
            movieId = movie?.id?.toLong() ?: 0L
        }
        if (arguments?.getSerializable(ARG_KEYWORD) != null) {
            keyword = arguments?.getSerializable(ARG_KEYWORD) as Keyword
        }

        accountId = arguments?.getLong(ARG_ACCOUNT_ID) ?: 0L

        toolbar.title = when(list) {
            SIMILAR -> getString(R.string.similar_movies)
            RECOMMENDATIONS -> getString(R.string.recommendations)
            FAVORITE -> getString(R.string.favorites)
            WATCHLIST -> getString(R.string.watchlist)
            else -> {
                if (keyword.id != 0L) {
                    keyword.name
                } else {
                    ""
                }
            }
        }
        if (movieId != 0L) {
            toolbar.subtitle = movie?.title
        }
        toolbar.navigationIcon = getIcon(R.drawable.ic_arrow_back, R.color.iconActiveColor)
        toolbar.setOnClickListener { onScrollToTop() }
        toolbar.setNavigationOnClickListener {
            if (keyword.id != 0L) {
                requireFragmentManager().popBackStack()
            } else {
                requireActivity().finish()
            }
        }

        val spans = resources.getInteger(R.integer.movies_span_layout_count)

        adapter = ListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spans)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spans, resources.getDimension(R.dimen.movies_list_spacing).toInt()))
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && adapter.itemCount != 0) {
                    when {
                        keyword.id != 0L -> viewModel.moviesByKeyword(keyword.id)
                        list == WATCHLIST -> viewModel.moviesWatchlist(accountId, sessionId)
                        list == FAVORITE -> viewModel.moviesFavorite(accountId, sessionId)
                        else -> viewModel.moviesById(movieId, list)
                    }
                }
            }
        })

        emptyView.setOnClickListener {
            emptyView.visibility = GONE
            when {
                keyword.id != 0L -> viewModel.moviesByKeyword(keyword.id)
                list == FAVORITE -> viewModel.moviesFavorite(accountId, sessionId)
                list == WATCHLIST -> viewModel.moviesWatchlist(accountId, sessionId)
                else -> viewModel.moviesById(movieId, list)
            }
        }

        when {
            keyword.id != 0L -> viewModel.moviesByKeyword(keyword.id)
            list == FAVORITE -> viewModel.moviesFavorite(accountId, sessionId)
            list == WATCHLIST -> viewModel.moviesWatchlist(accountId, sessionId)
            else -> viewModel.moviesById(movieId, list)
        }
        //viewModel.loading.reObserve(viewLifecycleOwner, { progressBar.visibility = if (it) VISIBLE else GONE })
        viewModel.content.reObserve(viewLifecycleOwner, Observer { adapter.setItems(it) })
        viewModel.error.reObserve(viewLifecycleOwner, Observer { error ->
            error.getContentIfNotHandled()?.let {
                emptyView.visibility = VISIBLE
                emptyView.setMode(it)

                if (BuildUtil.isApiKeyEmpty) {
                    emptyView.setValue(R.string.error_empty_api_key)
                }
            }
        })
        viewModel.click.reObserve(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                requireActivity().startActivity<MovieActivity> {
                    putExtra(EXTRA_MOVIE, it)
                }
            }
        })
        viewModel.longClick.reObserve(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                requireActivity().startActivity<MovieActivity> {
                    putExtra(EXTRA_MOVIE, it)
                }
            }
        })
    }

    override fun onScrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }
}