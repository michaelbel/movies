package org.michaelbel.moviemade.presentation.features.favorites

import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_frame.*
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
import org.michaelbel.moviemade.presentation.common.network.NetworkChangeListener
import org.michaelbel.moviemade.presentation.common.network.NetworkChangeReceiver
import org.michaelbel.moviemade.presentation.features.main.MoviesAdapter
import javax.inject.Inject

class FavoritesFragment: BaseFragment(),
        FavoritesContract.View, NetworkChangeListener, MoviesAdapter.Listener {

    companion object {
        internal fun newInstance(accountId: Int): FavoritesFragment {
            val args = Bundle()
            args.putInt(EXTRA_ACCOUNT_ID, accountId)

            val fragment = FavoritesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var accountId: Int = 0
    private var adapter: MoviesAdapter? = null

    private var networkChangeReceiver: NetworkChangeReceiver? = null
    private var connectionFailure = false

    @Inject
    lateinit var preferences: SharedPreferences

    @Inject
    lateinit var presenter: FavoritesContract.Presenter

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
        (requireContext() as FavoriteActivity).toolbar.setOnClickListener { recycler_view.smoothScrollToPosition(0) }

        val spanCount = resources.getInteger(R.integer.movies_span_layout_count)

        adapter = MoviesAdapter(this)

        recycler_view.adapter = adapter
        recycler_view.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recycler_view.addItemDecoration(GridSpacingItemDecoration(spanCount, DeviceUtil.dp(requireContext(), 3F)))
        recycler_view.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && adapter?.itemCount != 0) {
                    presenter.getFavoriteMoviesNext(accountId, preferences.getString(KEY_SESSION_ID, "")!!)
                }
            }
        })

        empty_view.setOnClickListener { presenter.getFavoriteMovies(accountId, preferences.getString(KEY_SESSION_ID, "")!!) }

        accountId = if (arguments != null) arguments!!.getInt(EXTRA_ACCOUNT_ID) else 0
        presenter.getFavoriteMovies(accountId, preferences.getString(KEY_SESSION_ID, "")!!)
    }

    override fun onMovieClick(movie: Movie, view: View) {
        (requireContext() as FavoriteActivity).startMovie(movie)
        requireActivity().finish()
    }

    override fun showLoading() {
        progress_bar.visibility = VISIBLE
    }

    override fun hideLoading() {
        progress_bar.visibility = GONE
    }

    override fun setMovies(movies: List<Movie>) {
        connectionFailure = false
        adapter?.addMovies(movies)
        hideLoading()
        empty_view.visibility = GONE
    }

    override fun setError(mode: Int) {
        connectionFailure = true
        empty_view.visibility = VISIBLE
        empty_view.setMode(mode)
        hideLoading()

        if (BuildUtil.isEmptyApiKey()) {
            empty_view.setValue(R.string.error_empty_api_key)
        }
    }

    override fun onNetworkChanged() {
        if (connectionFailure && adapter?.itemCount == 0) {
            presenter.getFavoriteMovies(accountId, preferences.getString(KEY_SESSION_ID, "")!!)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}