package org.michaelbel.moviemade.presentation.features.reviews

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.data.remote.model.Movie
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.databinding.FragmentLceBinding
import org.michaelbel.moviemade.ktx.getIcon
import org.michaelbel.moviemade.ktx.launchAndRepeatWithViewLifecycle
import org.michaelbel.moviemade.ktx.toDp
import org.michaelbel.moviemade.presentation.ContainerActivity
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.EXTRA_MOVIE
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.review.ReviewFragment

@AndroidEntryPoint
class ReviewsFragment: BaseFragment(R.layout.fragment_lce) {

    private lateinit var movie: Movie

    private val viewModel: ReviewsModel by viewModels()
    private val binding: FragmentLceBinding by viewBinding()

    private val listAdapter = ListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie = arguments?.getSerializable(ARG_MOVIE) as Movie

        val spans: Int = resources.getInteger(R.integer.trailers_span_layout_count)

        binding.toolbar.run {
            title = getString(R.string.reviews)
            subtitle = movie.title
            navigationIcon = getIcon(R.drawable.ic_arrow_back, R.color.iconActiveColor)
            setOnClickListener { onScrollToTop() }
            setNavigationOnClickListener { requireActivity().finish() }
        }
        binding.recyclerView.run {
            adapter = listAdapter
            layoutManager = GridLayoutManager(requireContext(), spans)
            layoutAnimation = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.recycler_layout_animation)
            addItemDecoration(GridSpacingItemDecoration(spans, 5F.toDp(requireContext())))
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && listAdapter.itemCount != 0) {
                        viewModel.reviews(movie.id.toLong())
                    }
                }
            })
        }
        binding.emptyView.setOnClickListener {
            binding.emptyView.isGone = true
            viewModel.reviews(movie.id.toLong())
        }

        viewModel.reviews(movie.id.toLong())

        launchAndRepeatWithViewLifecycle {
            launch {
                viewModel.loading.collect { binding.progressBar.isVisible = it }
            }
            launch {
                viewModel.content.collect {
                    listAdapter.setItems(it)
                    binding.recyclerView.scheduleLayoutAnimation()
                }
            }
            launch {
                viewModel.error.collect {
                    binding.emptyView.isVisible = true
                    binding.emptyView.setMode(it)
                }
            }
            launch {
                viewModel.click.collect {
                    parentFragmentManager.commit {
                        add((requireActivity() as ContainerActivity).containerId, ReviewFragment.newInstance(it, movie), ReviewFragment::class.java.name)
                        addToBackStack(ReviewFragment::class.java.name)
                    }
                }
            }
            launch {
                viewModel.longClick.collect {

                }
            }
        }
    }

    override fun onScrollToTop() {
        binding.recyclerView.smoothScrollToPosition(0)
    }

    companion object {
        private const val ARG_MOVIE = "movie"

        fun newInstance(movie: Movie): ReviewsFragment {
            return ReviewsFragment().apply {
                arguments = bundleOf(EXTRA_MOVIE to movie)
            }
        }
    }
}