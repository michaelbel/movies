package org.michaelbel.moviemade.presentation.features.keywords

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.setMargins
import androidx.fragment.app.commitNow
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.data.remote.model.Movie
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.databinding.FragmentLceBinding
import org.michaelbel.moviemade.ktx.argumentDelegate
import org.michaelbel.moviemade.ktx.getIcon
import org.michaelbel.moviemade.ktx.launchAndRepeatWithViewLifecycle
import org.michaelbel.moviemade.presentation.ContainerActivity
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.main.MoviesFragment2

@AndroidEntryPoint
class KeywordsFragment: BaseFragment(R.layout.fragment_lce) {

    private val movie: Movie? by argumentDelegate()
    private val viewModel: KeywordsModel by viewModels()
    private val binding: FragmentLceBinding by viewBinding()

    private val listAdapter = ListAdapter()

    private val recyclerViewLayoutParams: ConstraintLayout.LayoutParams
        get() {
            val params = binding.recyclerView.layoutParams as ConstraintLayout.LayoutParams
            params.setMargins(resources.getDimension(R.dimen.fragment_keywords_margin).toInt())
            return params
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.run {
            title = getString(R.string.keywords)
            subtitle = movie?.title
            navigationIcon = getIcon(R.drawable.ic_arrow_back, R.color.iconActiveColor)
            setOnClickListener { onScrollToTop() }
            setNavigationOnClickListener { requireActivity().finish() }
        }
        binding.recyclerView.run {
            adapter = listAdapter
            layoutParams = recyclerViewLayoutParams
            layoutManager = ChipsLayoutManager.newBuilder(requireContext()).setOrientation(ChipsLayoutManager.HORIZONTAL).build()
        }
        binding.emptyView.setOnClickListener {
            binding.emptyView.isGone = true
            viewModel.keywords(movie?.id?.toLong() ?: 0L)
        }

        viewModel.keywords(movie?.id?.toLong() ?: 0L)

        launchAndRepeatWithViewLifecycle {
            launch {
                viewModel.loading.collect { binding.progressBar.isVisible = it }
            }
            launch {
                viewModel.content.collect { listAdapter.setItems(it) }
            }
            launch {
                viewModel.error.collect {
                    binding.emptyView.isVisible = true
                    binding.emptyView.setMode(it)
                }
            }
            launch {
                viewModel.click.collect {
                    parentFragmentManager.commitNow {
                        add((requireActivity() as ContainerActivity).containerId, MoviesFragment2.newInstance(it))
                        addToBackStack(tag)
                    }
                }
            }
            launch {
                viewModel.longClick.collect {
                    parentFragmentManager.commitNow {
                        add((requireActivity() as ContainerActivity).containerId, MoviesFragment2.newInstance(it))
                        addToBackStack(tag)
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance(movie: Movie): KeywordsFragment {
            return KeywordsFragment().apply {
                arguments = bundleOf("movie" to movie)
            }
        }
    }
}