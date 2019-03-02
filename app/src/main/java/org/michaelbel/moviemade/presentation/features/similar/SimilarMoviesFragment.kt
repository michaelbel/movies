package org.michaelbel.moviemade.presentation.features.similar

import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_default.*
import kotlinx.android.synthetic.main.fragment_lce.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.utils.DeviceUtil
import org.michaelbel.moviemade.core.utils.EXTRA_MOVIE_ID
import org.michaelbel.moviemade.core.utils.ViewUtil
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.common.ErrorView
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.common.network.NetworkChangeReceiver
import org.michaelbel.moviemade.presentation.features.main.MoviesAdapter
import timber.log.Timber
import javax.inject.Inject

class SimilarMoviesFragment: BaseFragment(),
        SimilarContract.View,
        NetworkChangeReceiver.Listener,
        MoviesAdapter.Listener,
        ErrorView.Listener,
        SwipeRefreshLayout.OnRefreshListener {

    companion object {
        fun newInstance(movieId: Int): SimilarMoviesFragment {
            val args = Bundle()
            args.putInt(EXTRA_MOVIE_ID, movieId)

            val fragment = SimilarMoviesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var movieId: Int = 0

    lateinit var adapter: MoviesAdapter

    private var networkChangeReceiver: NetworkChangeReceiver? = null
    private var connectionFailure = false

    @Inject
    lateinit var presenter: SimilarContract.Presenter

    @Inject
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application as App].createFragmentComponent().inject(this)
        networkChangeReceiver = NetworkChangeReceiver(this)
        requireContext().registerReceiver(networkChangeReceiver, IntentFilter(NetworkChangeReceiver.INTENT_ACTION))
        presenter.attach(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_lce, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as SimilarMoviesActivity).toolbar.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
        }

        val spanCount = resources.getInteger(R.integer.movies_span_layout_count)

        adapter = MoviesAdapter(this)

        swipeRefreshLayout.setColorSchemeColors(ViewUtil.getAttrColor(requireContext(), android.R.attr.colorAccent))
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ViewUtil.getAttrColor(requireContext(), android.R.attr.colorPrimary))
        swipeRefreshLayout.setOnRefreshListener(this)

        progressBar.visibility = VISIBLE

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, DeviceUtil.dp(requireContext(), 3F)))
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && adapter.itemCount != 0 && progressBar.isGone) {
                    presenter.getSimilarMoviesNext(movieId)
                }
            }
        })

        errorView.setErrorListener(this)

        movieId = arguments?.getInt(EXTRA_MOVIE_ID) ?: 0
        presenter.getSimilarMovies(movieId)
    }

    override fun setMovies(movies: List<Movie>) {
        connectionFailure = false
        progressBar.visibility = GONE
        swipeRefreshLayout.isRefreshing = false
        errorView.visibility = GONE
        adapter.addMovies(movies)
    }

    override fun setError(error: Throwable, code: Int) {
        connectionFailure = true
        progressBar.visibility = GONE
        swipeRefreshLayout.isRefreshing = false
        errorView.visibility = VISIBLE
        errorView.setError(error, code)

        Timber.d(error, javaClass.simpleName)
    }

    override fun onReloadData() {
        errorView.visibility = GONE
        progressBar.visibility = VISIBLE
        presenter.getSimilarMovies(movieId)
    }

    override fun onRefresh() {
        if (progressBar.isGone) {
            if (adapter.itemCount == 0) {
                errorView.visibility = GONE
                presenter.getSimilarMovies(movieId)
            } else {
                adapter.movies.clear()
                adapter.notifyDataSetChanged()
                presenter.getSimilarMovies(movieId)
            }
        }
    }

    override fun onNetworkChanged() {
        if (connectionFailure && adapter.itemCount == 0) {
            presenter.getSimilarMovies(movieId)
        }
    }

    override fun onMovieClick(movie: Movie) {
        (requireActivity() as SimilarMoviesActivity).startMovie(movie)
    }

    override fun onDestroy() {
        super.onDestroy()
        requireContext().unregisterReceiver(networkChangeReceiver)
        presenter.destroy()
    }
}