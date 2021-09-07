package org.michaelbel.moviemade.presentation.features.main

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
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
import org.michaelbel.moviemade.databinding.FragmentMainBinding
import org.michaelbel.moviemade.ktx.*
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment: Fragment(R.layout.fragment_main) {

    @Inject lateinit var factory: MainViewModel.Factory
    @Inject lateinit var preferences: SharedPreferences

    private val list: String = Movie.NOW_PLAYING
    private val movie: Movie? by argumentDelegate()
    private val accountId: Long? by argumentDelegate()
    private val keyword: Keyword? by argumentDelegate()

    private val binding: FragmentMainBinding by viewBinding()
    private val viewModel: MainViewModel by assistedViewModels {
        factory.create(list, keyword, movie, accountId)
    }

    private val listAdapter = ListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(binding.appBarLayout) { v, insets ->
            val statusBars = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = requireContext().statusBarHeight
            }
            WindowInsetsCompat.CONSUMED
        }

        val spans: Int = resources.getInteger(R.integer.movies_span_layout_count)

        binding.toolbar.run {
            setOnMenuItemClickListener {
                if (it.itemId == R.id.item_view) {
                    toast("View changed")
                }
                true
            }
            setOnClickListener { binding.recyclerView.smoothScrollToPosition(0) }
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
            launch { viewModel.click.collect {} }
            launch { viewModel.longClick.collect {} }
        }
    }
}