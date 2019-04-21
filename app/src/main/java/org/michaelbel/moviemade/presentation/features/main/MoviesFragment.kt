package org.michaelbel.moviemade.presentation.features.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_movies.*
import org.michaelbel.data.Movie
import org.michaelbel.data.remote.model.MoviesResponse.Companion.NOW_PLAYING
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.local.BuildUtil
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.EXTRA_MOVIE
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.movie.MovieActivity
import javax.inject.Inject

/**
 * Список фильмов без тулбара для главной.
 */
class MoviesFragment: BaseFragment(), MoviesAdapter.Listener {

    companion object {
        const val EXTRA_LIST = "list"

        internal fun newInstance(list: String): MoviesFragment {
            val args = Bundle()
            args.putString(EXTRA_LIST, list)

            val fragment = MoviesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var list: String
    private lateinit var adapter: MoviesAdapter
    private lateinit var viewModel: MoviesModel

    @Inject
    lateinit var factory: MoviesFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application as App].createFragmentComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(requireActivity(), factory).get(MoviesModel::class.java)
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = arguments?.getString(EXTRA_LIST) ?: NOW_PLAYING

        val spans = resources.getInteger(R.integer.movies_span_layout_count)

        adapter = MoviesAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spans)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spans, resources.getDimension(R.dimen.movies_list_spacing).toInt()))
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && adapter.itemCount != 0) {
                    viewModel.moviesNext(list = list)
                }
            }
        })

        emptyView.setOnClickListener {
            emptyView.visibility = GONE
            viewModel.movies(list = list)
        }

        viewModel.movies(list = list)
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = if (it) VISIBLE else GONE
        })
        viewModel.content.observe(viewLifecycleOwner, Observer {
            adapter.addMovies(it)
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            emptyView.visibility = VISIBLE
            emptyView.setMode(it)

            if (BuildUtil.isApiKeyEmpty()) {
                emptyView.setValue(R.string.error_empty_api_key)
            }
        })
    }

    override fun onScrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }

    override fun onMovieClick(movie: Movie) {
        val intent = Intent(requireContext(), MovieActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)
        startActivity(intent)
    }
}