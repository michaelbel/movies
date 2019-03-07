package org.michaelbel.moviemade.presentation.features.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_movies.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.MoviesResponse.Companion.NOW_PLAYING
import org.michaelbel.moviemade.core.local.BuildUtil
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import javax.inject.Inject

class MoviesFragment: BaseFragment(), MainContract.View, MoviesAdapter.Listener {

    companion object {
        const val EXTRA_LIST = "list"
        const val EXTRA_MOVIE_ID = "movie_id"

        fun newInstance(list: String): MoviesFragment {
            val args = Bundle()
            args.putString(EXTRA_LIST, list)

            val fragment = MoviesFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(list: String, id: Int): MoviesFragment {
            val args = Bundle()
            args.putInt(EXTRA_MOVIE_ID, id)
            args.putString(EXTRA_LIST, list)

            val fragment = MoviesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var list: String
    private var movieId: Int = 0
    private lateinit var adapter: MoviesAdapter

    @Inject
    lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application as App].createFragmentComponent().inject(this)
        presenter.attach(this)
        adapter = MoviesAdapter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spanCount = resources.getInteger(R.integer.movies_span_layout_count)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, resources.getDimension(R.dimen.movies_list_spacing).toInt()))
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && adapter.itemCount != 0) {
                    presenter.moviesNext(movieId, list)
                }
            }
        })

        emptyView.setOnClickListener { presenter.movies(movieId, list) }

        list = arguments?.getString(EXTRA_LIST) ?: NOW_PLAYING
        movieId = arguments?.getInt(EXTRA_MOVIE_ID) ?: 0
        presenter.movies(movieId, list)
    }

    override fun loading(state: Boolean) {
        progressBar.visibility = if (state) VISIBLE else GONE
    }

    override fun content(results: List<Movie>) {
        adapter.addMovies(results)
    }

    override fun error(code: Int) {
        emptyView.visibility = VISIBLE
        emptyView.setMode(code)

        if (BuildUtil.isEmptyApiKey()) {
            emptyView.setValue(R.string.error_empty_api_key)
        }
    }

    override fun onMovieClick(movie: Movie) {
        (requireActivity() as MainActivity).startMovie(movie)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}