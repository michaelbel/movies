package org.michaelbel.moviemade.presentation.features.main

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_movies.*
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.data.remote.model.Movie.Companion.NOW_PLAYING
import org.michaelbel.domain.MoviesRepository
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.local.BuildUtil
import org.michaelbel.moviemade.ktx.*
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.EXTRA_MOVIE
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.movie.MovieActivity
import javax.inject.Inject

class MoviesFragment: BaseFragment(R.layout.fragment_movies) {

    companion object {
        fun newInstance(list: String): MoviesFragment {
            return MoviesFragment().apply {
                arguments = bundleOf("list" to list)
            }
        }
    }

    private val list: String? by argumentDelegate()

    private lateinit var adapter: ListAdapter

    @Inject lateinit var repository: MoviesRepository

    private val viewModel: MoviesModel by lazy { getViewModel { MoviesModel(repository) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application as App].createFragmentComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spans = resources.getInteger(R.integer.movies_span_layout_count)

        adapter = ListAdapter()

        recyclerView.apply {
            this.adapter = adapter
            this.layoutManager = GridLayoutManager(requireContext(), spans)
            this.addItemDecoration(GridSpacingItemDecoration(spans, resources.getDimension(R.dimen.movies_list_spacing).toInt()))
            this.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && adapter?.itemCount != 0) {
                        viewModel.movies(list ?: NOW_PLAYING)
                    }
                }
            })
        }

        emptyView.setOnClickListener {
            emptyView.gone()
            viewModel.movies(list ?: NOW_PLAYING)
        }

        viewModel.movies(list ?: NOW_PLAYING)
        viewModel.loading.reObserve(viewLifecycleOwner, Observer { progressBar.visibility = if (it) VISIBLE else GONE })
        viewModel.content.reObserve(viewLifecycleOwner, Observer { adapter.setItems(it) })
        viewModel.error.reObserve(viewLifecycleOwner, Observer { error ->
            error.getContentIfNotHandled()?.let {
                emptyView.visible()
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