package org.michaelbel.moviemade.presentation.features.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_movies.*
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.data.remote.model.Movie.Companion.NOW_PLAYING
import org.michaelbel.domain.MoviesRepository
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.getViewModel
import org.michaelbel.moviemade.core.local.BuildUtil
import org.michaelbel.moviemade.core.reObserve
import org.michaelbel.moviemade.core.startActivity
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.EXTRA_MOVIE
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.movie.MovieActivity
import javax.inject.Inject

class MoviesFragment: BaseFragment() {

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
    private lateinit var adapter: ListAdapter

    @Inject lateinit var repository: MoviesRepository

    private val viewModel: MoviesModel by lazy { getViewModel { MoviesModel(repository) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application as App].createFragmentComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = arguments?.getString(EXTRA_LIST) ?: NOW_PLAYING

        val spans = resources.getInteger(R.integer.movies_span_layout_count)

        adapter = ListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spans)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spans, resources.getDimension(R.dimen.movies_list_spacing).toInt()))
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && adapter.itemCount != 0) {
                    viewModel.movies(list)
                }
            }
        })

        emptyView.setOnClickListener {
            emptyView.visibility = GONE
            viewModel.movies(list)
        }

        viewModel.movies(list)
        viewModel.loading.reObserve(viewLifecycleOwner, Observer {
            progressBar.visibility = if (it) VISIBLE else GONE
        })
        viewModel.content.reObserve(viewLifecycleOwner, Observer {
            adapter.setItems(it)
        })
        viewModel.error.reObserve(viewLifecycleOwner, Observer { error ->
            error.getContentIfNotHandled()?.let {
                emptyView.visibility = VISIBLE
                emptyView.setMode(it)

                if (BuildUtil.isApiKeyEmpty()) {
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