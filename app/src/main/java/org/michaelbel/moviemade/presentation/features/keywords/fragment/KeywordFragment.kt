package org.michaelbel.moviemade.presentation.features.keywords.fragment

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
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.utils.DeviceUtil
import org.michaelbel.moviemade.core.utils.KEYWORD_ID
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.common.network.NetworkChangeReceiver
import org.michaelbel.moviemade.presentation.features.keywords.KeywordContract
import org.michaelbel.moviemade.presentation.features.keywords.activity.KeywordActivity
import org.michaelbel.moviemade.presentation.features.main.MoviesAdapter
import javax.inject.Inject

class KeywordFragment: BaseFragment(),
        KeywordContract.View,
        NetworkChangeReceiver.Listener,
        MoviesAdapter.Listener {

    companion object {
        fun newInstance(keywordId: Int): KeywordFragment {
            val args = Bundle()
            args.putInt(KEYWORD_ID, keywordId)

            val fragment = KeywordFragment()
            fragment.arguments = args
            return fragment
        }
    }

    var keywordId: Int = 0
    lateinit var adapter: MoviesAdapter

    private var networkChangeReceiver: NetworkChangeReceiver? = null
    private var connectionFailure = false

    @Inject
    lateinit var presenter: KeywordContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application].createFragmentComponent().inject(this)
        networkChangeReceiver = NetworkChangeReceiver(this)
        requireContext().registerReceiver(networkChangeReceiver, IntentFilter(NetworkChangeReceiver.INTENT_ACTION))
        presenter.attach(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as KeywordActivity).toolbar.setOnClickListener { recyclerView.smoothScrollToPosition(0) }

        val spanCount = resources.getInteger(R.integer.movies_span_layout_count)

        adapter = MoviesAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, DeviceUtil.dp(requireContext(), 3F)))
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (recyclerView.canScrollVertically(1).not() && adapter.itemCount != 0) {
                    presenter.getMoviesNext(keywordId)
                }
            }
        })

        emptyView.setOnClickListener { presenter.getMovies(keywordId) }

        keywordId = arguments?.getInt(KEYWORD_ID) ?: 0
        presenter.getMovies(keywordId)
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
    }

    override fun onNetworkChanged() {
        if (connectionFailure && adapter.itemCount == 0) {
            presenter.getMovies(keywordId)
        }
    }

    override fun onMovieClick(movie: Movie) {
        (requireActivity() as KeywordActivity).startMovie(movie)
    }
}