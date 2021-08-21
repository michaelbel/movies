package org.michaelbel.moviemade.presentation.features.trailers

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.common.base.BaseFragment

@AndroidEntryPoint
class TrailersFragment: BaseFragment(R.layout.fragment_lce) {

    companion object {
        private const val ARG_MOVIE = "movie"

        fun newInstance(movie: Movie): TrailersFragment {
            return TrailersFragment().apply {
                arguments = bundleOf(ARG_MOVIE to movie)
            }
        }
    }

    private lateinit var movie: Movie

    private val viewModel: TrailersModel by viewModels()
    private val binding: FragmentLceBinding by viewBinding()

    private val listAdapter = ListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movie = arguments?.getSerializable(ARG_MOVIE) as Movie

        val spans: Int = resources.getInteger(R.integer.trailers_span_layout_count)

        binding.toolbar.run {
            title = getString(R.string.trailers)
            subtitle = movie.title
            setOnClickListener { onScrollToTop() }
            navigationIcon = getIcon(R.drawable.ic_arrow_back, R.color.iconActiveColor)
            setNavigationOnClickListener { requireActivity().finish() }
        }
        binding.recyclerView.run {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
            layoutAnimation = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.recycler_layout_animation)
            addItemDecoration(GridSpacingItemDecoration(spans, 5F.toDp(requireContext())))
        }
        binding.emptyView.setOnClickListener {
            binding.emptyView.isGone = true
            viewModel.trailers(movie.id.toLong())
        }

        viewModel.trailers(movie.id.toLong())

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
                    //val dialog = YoutubePlayerDialogFragment.newInstance(it.key.toUri().toString())
                    //dialog.show(requireActivity().supportFragmentManager, DIALOG_TAG)
                }
            }
            launch {
                viewModel.longClick.collect {
                    startActivity(Intent(Intent.ACTION_VIEW, ("vnd.youtube:" + it.key).toUri()))
                }
            }
        }
    }

    override fun onScrollToTop() {
        binding.recyclerView.smoothScrollToPosition(0)
    }
}