package org.michaelbel.moviemade.presentation.features.keywords.fragment

import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_frame.*
import kotlinx.android.synthetic.main.fragment_movies.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.utils.DeviceUtil
import org.michaelbel.moviemade.core.utils.KEYWORD_ID
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.common.network.NetworkChangeListener
import org.michaelbel.moviemade.presentation.common.network.NetworkChangeReceiver
import org.michaelbel.moviemade.presentation.features.keywords.KeywordContract
import org.michaelbel.moviemade.presentation.features.keywords.activity.KeywordActivity
import org.michaelbel.moviemade.presentation.features.main.MoviesAdapter
import javax.inject.Inject

class KeywordFragment: BaseFragment(), KeywordContract.View, NetworkChangeListener, MoviesAdapter.Listener {

    companion object {
        fun newInstance(keywordId: Int): KeywordFragment {
            val args = Bundle()
            args.putInt(KEYWORD_ID, keywordId)

            val fragment = KeywordFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var keywordId: Int = 0
    private var adapter: MoviesAdapter? = null

    private var networkChangeReceiver: NetworkChangeReceiver? = null
    private var connectionFailure = false

    @Inject
    lateinit var presenter: KeywordContract.Presenter

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
        (requireActivity() as KeywordActivity).toolbar.setOnClickListener { recycler_view.smoothScrollToPosition(0) }

        val spanCount = resources.getInteger(R.integer.movies_span_layout_count)

        adapter = MoviesAdapter(this)

        recycler_view.adapter = adapter
        recycler_view.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recycler_view.addItemDecoration(GridSpacingItemDecoration(spanCount, DeviceUtil.dp(requireContext(), 3F)))
        recycler_view.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && adapter?.itemCount != 0) {
                    presenter.getMoviesNext(keywordId)
                }
            }
        })

        empty_view.setOnClickListener { presenter.getMovies(keywordId) }

        keywordId = if (arguments != null) arguments!!.getInt(KEYWORD_ID) else 0
        presenter.getMovies(keywordId)
    }

    override fun onDestroy() {
        super.onDestroy()
        requireContext().unregisterReceiver(networkChangeReceiver)
        presenter.destroy()
    }

    override fun setMovies(movies: List<Movie>) {
        connectionFailure = false
        progress_bar.visibility = GONE
        adapter?.addMovies(movies)
    }

    override fun setError(mode: Int) {
        connectionFailure = false
        progress_bar.visibility = GONE
        empty_view.setMode(mode)
    }

    override fun onNetworkChanged() {
        if (connectionFailure && adapter?.itemCount == 0) {
            presenter.getMovies(keywordId)
        }
    }

    override fun onMovieClick(movie: Movie, view: View) {
        (requireActivity() as KeywordActivity).startMovie(movie)
    }
}