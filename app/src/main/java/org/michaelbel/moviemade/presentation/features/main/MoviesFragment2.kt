package org.michaelbel.moviemade.presentation.features.main

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.data.remote.model.Keyword
import org.michaelbel.data.remote.model.Movie
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.local.BuildUtil
import org.michaelbel.moviemade.databinding.FragmentLceBinding
import org.michaelbel.moviemade.ktx.argumentDelegate
import org.michaelbel.moviemade.ktx.assistedViewModels
import org.michaelbel.moviemade.ktx.getIcon
import org.michaelbel.moviemade.ktx.launchAndRepeatWithViewLifecycle
import org.michaelbel.moviemade.ktx.startActivity
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.EXTRA_MOVIE
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.movie.MovieActivity
import javax.inject.Inject

/**
 * Display movies list with Toolbar.
 *
 * NowPlaying, Popular, TopRated, Upcoming
 * Watchlist, Favorite
 * Similar, Recommendations
 * Movies by Keyword
 */
@AndroidEntryPoint
class MoviesFragment2: BaseFragment(R.layout.fragment_lce) {

    @Inject lateinit var factory: MoviesModel.Factory
    @Inject lateinit var preferences: SharedPreferences

    private val list: String? by argumentDelegate()
    private val movie: Movie? by argumentDelegate()
    private val accountId: Long? by argumentDelegate()
    private val keyword: Keyword? by argumentDelegate()
    private val binding: FragmentLceBinding by viewBinding()
    private val viewModel: MoviesModel by assistedViewModels {
        factory.create(list, keyword, movie, accountId)
    }

    private val listAdapter = ListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spans: Int = resources.getInteger(R.integer.movies_span_layout_count)

        binding.toolbar.run {
            navigationIcon = getIcon(R.drawable.ic_arrow_back, R.color.iconActiveColor)
            setNavigationOnClickListener {
                if (viewModel.keyword != null) {
                    parentFragmentManager.popBackStack()
                } else {
                    requireActivity().finish()
                }
            }
            setOnClickListener { onScrollToTop() }
        }
        binding.recyclerView.run {
            adapter = listAdapter
            layoutManager = GridLayoutManager(requireContext(), spans)
            addItemDecoration(GridSpacingItemDecoration(spans, resources.getDimension(R.dimen.movies_list_spacing).toInt()))
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && listAdapter.itemCount != 0) {
                        viewModel.load()
                    }
                }
            })
        }
        binding.emptyView.setOnClickListener {
            binding.emptyView.visibility = GONE
            viewModel.load()
        }

        launchAndRepeatWithViewLifecycle {
            launch { viewModel.loading.collect { binding.progressBar.isVisible = it } }
            launch { viewModel.content.collect { listAdapter.setItems(it) } }
            launch { viewModel.error.collect {
                binding.emptyView.isVisible = true
                binding.emptyView.setMode(it)

                if (BuildUtil.isApiKeyEmpty) {
                    binding.emptyView.setValue(R.string.error_empty_api_key)
                }
            } }
            launch { viewModel.click.collect {
                requireActivity().startActivity<MovieActivity> {
                    putExtra(EXTRA_MOVIE, it)
                }
            } }
            launch { viewModel.longClick.collect {
                requireActivity().startActivity<MovieActivity> {
                    putExtra(EXTRA_MOVIE, it)
                }
            } }
            launch { viewModel.toolbarTitle.collect {
                when (it) {
                    is Int -> binding.toolbar.title = getString(it)
                    is String -> binding.toolbar.title = it
                }
            } }
            launch { viewModel.toolbarSubtitle.collect { binding.toolbar.subtitle = it } }
        }
    }

    override fun onScrollToTop() {
        binding.recyclerView.smoothScrollToPosition(0)
    }

    companion object {
        fun newInstance(list: String): MoviesFragment2 {
            return MoviesFragment2().apply {
                arguments = bundleOf("list" to list)
            }
        }

        fun newInstance(list: String, movie: Movie): MoviesFragment2 {
            return MoviesFragment2().apply {
                arguments = bundleOf("list" to list, "movie" to movie)
            }
        }

        fun newInstance(keyword: Keyword): MoviesFragment2 {
            return MoviesFragment2().apply {
                arguments = bundleOf("keyword" to keyword)
            }
        }

        fun newInstance(list: String, accountId: Int): MoviesFragment2 {
            return MoviesFragment2().apply {
                arguments = bundleOf("list" to list, "accountId" to accountId)
            }
        }
    }
}