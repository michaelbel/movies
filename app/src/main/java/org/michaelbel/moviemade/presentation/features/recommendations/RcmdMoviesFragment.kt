package org.michaelbel.moviemade.presentation.features.recommendations

import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_default.*
import kotlinx.android.synthetic.main.fragment_movies.*
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.utils.DeviceUtil
import org.michaelbel.moviemade.core.utils.EXTRA_MOVIE_ID
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.common.network.NetworkChangeReceiver
import org.michaelbel.moviemade.presentation.features.main.MoviesAdapter
import javax.inject.Inject

class RcmdMoviesFragment: BaseFragment(),
        RcmdContract.View,
        NetworkChangeReceiver.Listener,
        MoviesAdapter.Listener {

    companion object {
        fun newInstance(movieId: Int): RcmdMoviesFragment {
            val args = Bundle()
            args.putInt(EXTRA_MOVIE_ID, movieId)

            val fragment = RcmdMoviesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var movieId: Int = 0
    lateinit var adapter: MoviesAdapter
    private lateinit var layoutManager: GridLayoutManager

    private var networkChangeReceiver: NetworkChangeReceiver? = null
    private var connectionFailure = false

    @Inject
    lateinit var presenter: RcmdContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkChangeReceiver = NetworkChangeReceiver(this)
        requireContext().registerReceiver(networkChangeReceiver, IntentFilter(NetworkChangeReceiver.INTENT_ACTION))
        App[requireActivity().application].createFragmentComponent().inject(this)
        presenter.attach(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as RcmdMoviesActivity).toolbar.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
        }

        val spanCount = resources.getInteger(R.integer.movies_span_layout_count)

        adapter = MoviesAdapter(this)
        layoutManager = GridLayoutManager(requireContext(), spanCount)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, DeviceUtil.dp(requireContext(), 3F)))
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && adapter.itemCount != 0) {
                    presenter.getRcmdMoviesNext(movieId)
                }
            }
        })

        emptyView.setOnClickListener { presenter.getRcmdMoviesNext(movieId) }

        movieId = arguments?.getInt(EXTRA_MOVIE_ID) ?: 0
        presenter.getRcmdMovies(movieId)
    }

    override fun onDestroy() {
        super.onDestroy()
        requireContext().unregisterReceiver(networkChangeReceiver)
        presenter.destroy()
    }

    override fun setMovies(movies: List<Movie>) {
        connectionFailure = false
        progressBar.visibility = GONE
        adapter.addMovies(movies)
    }

    override fun setError(mode: Int) {
        connectionFailure = false
        progressBar.visibility = GONE
        emptyView.setMode(mode)

        if (TMDB_API_KEY === "null") {
            emptyView.setValue(R.string.error_empty_api_key)
        }
    }

    override fun onNetworkChanged() {
        if (connectionFailure && adapter.itemCount == 0) {
            presenter.getRcmdMovies(movieId)
        }
    }

    override fun onMovieClick(movie: Movie) {
        (requireActivity() as RcmdMoviesActivity).startMovie(movie)
    }
}