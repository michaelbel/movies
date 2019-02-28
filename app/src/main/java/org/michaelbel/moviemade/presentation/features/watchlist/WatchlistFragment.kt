package org.michaelbel.moviemade.presentation.features.watchlist

import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_default.*
import kotlinx.android.synthetic.main.fragment_movies.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.utils.BuildUtil
import org.michaelbel.moviemade.core.utils.DeviceUtil
import org.michaelbel.moviemade.core.utils.EXTRA_ACCOUNT_ID
import org.michaelbel.moviemade.core.utils.KEY_SESSION_ID
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.common.network.NetworkChangeReceiver
import org.michaelbel.moviemade.presentation.features.main.MoviesAdapter
import javax.inject.Inject

class WatchlistFragment: BaseFragment(),
        WatchlistContract.View,
        NetworkChangeReceiver.Listener,
        MoviesAdapter.Listener {

    companion object {
        fun newInstance(accountId: Int): WatchlistFragment {
            val args = Bundle()
            args.putInt(EXTRA_ACCOUNT_ID, accountId)

            val fragment = WatchlistFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var accountId: Int = 0

    lateinit var adapter: MoviesAdapter

    private var networkChangeReceiver: NetworkChangeReceiver? = null
    private var connectionFailure = false

    @Inject
    lateinit var preferences: SharedPreferences

    @Inject
    lateinit var presenter: WatchlistContract.Presenter

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
        (requireActivity() as WatchlistActivity).toolbar.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
        }

        val spanCount = resources.getInteger(R.integer.movies_span_layout_count)

        adapter = MoviesAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, DeviceUtil.dp(requireContext(), 3F)))
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && adapter.itemCount != 0) {
                    val sessionId = preferences.getString(KEY_SESSION_ID, "") ?: ""
                    presenter.getWatchlistMoviesNext(accountId, sessionId)
                }
            }
        })

        emptyView.setOnClickListener {
            val sessionId = preferences.getString(KEY_SESSION_ID, "") ?: ""
            presenter.getWatchlistMovies(accountId, sessionId)
        }

        accountId = arguments?.getInt(EXTRA_ACCOUNT_ID) ?: 0
        val sessionId = preferences.getString(KEY_SESSION_ID, "") ?: ""
        presenter.getWatchlistMovies(accountId, sessionId)
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

        if (BuildUtil.isEmptyApiKey()) {
            emptyView.setValue(R.string.error_empty_api_key)
        }
    }

    override fun onNetworkChanged() {
        if (connectionFailure && adapter.itemCount == 0) {
            val sessionId = preferences.getString(KEY_SESSION_ID, "") ?: ""
            presenter.getWatchlistMovies(accountId, sessionId)
        }
    }

    override fun onMovieClick(movie: Movie) {
        (requireActivity() as WatchlistActivity).startMovie(movie)
        requireActivity().finish()
    }
}