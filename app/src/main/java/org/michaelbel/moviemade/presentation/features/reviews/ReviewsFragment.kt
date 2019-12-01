package org.michaelbel.moviemade.presentation.features.reviews

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_parent.*
import kotlinx.android.synthetic.main.fragment_lce.*
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.data.remote.model.Movie
import org.michaelbel.domain.ReviewsRepository
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.ktx.*
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.ContainerActivity
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.EXTRA_MOVIE
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.review.ReviewFragment
import javax.inject.Inject

class ReviewsFragment: BaseFragment(R.layout.fragment_lce) {

    companion object {
        private const val ARG_MOVIE = "movie"

        fun newInstance(movie: Movie): ReviewsFragment {
            return ReviewsFragment().apply {
                arguments = bundleOf(EXTRA_MOVIE to movie)
            }
        }
    }

    private lateinit var movie: Movie
    private lateinit var adapter: ListAdapter

    @Inject lateinit var repository: ReviewsRepository

    private val viewModel: ReviewsModel by lazy { getViewModel { ReviewsModel(repository) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application].createFragmentComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie = arguments?.getSerializable(ARG_MOVIE) as Movie

        toolbar.title = getString(R.string.reviews)
        toolbar.subtitle = movie.title
        toolbar.navigationIcon = getIcon(R.drawable.ic_arrow_back, R.color.iconActiveColor)
        toolbar.setOnClickListener { onScrollToTop() }
        toolbar.setNavigationOnClickListener { requireActivity().finish() }

        val spans = resources.getInteger(R.integer.trailers_span_layout_count)

        adapter = ListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spans)
        recyclerView.layoutAnimation = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.recycler_layout_animation)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spans, 5F.toDp(requireContext())))
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && adapter.itemCount != 0) {
                    viewModel.reviews(movie.id.toLong())
                }
            }
        })

        emptyView.setOnClickListener {
            emptyView.gone()
            viewModel.reviews(movie.id.toLong())
        }

        viewModel.reviews(movie.id.toLong())
        viewModel.loading.reObserve(viewLifecycleOwner, Observer { progressBar.visibility = if (it) VISIBLE else GONE })
        viewModel.content.reObserve(viewLifecycleOwner, Observer {
            adapter.setItems(it)
            recyclerView.scheduleLayoutAnimation()
        })
        viewModel.error.reObserve(viewLifecycleOwner, Observer { error ->
            error.getContentIfNotHandled()?.let {
                emptyView.visible()
                emptyView.setMode(it)
            }
        })
        viewModel.click.reObserve(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                requireFragmentManager().commit {
                    add((requireActivity() as ContainerActivity).container.id, ReviewFragment.newInstance(it, movie), ReviewFragment::class.java.name)
                    addToBackStack(ReviewFragment::class.java.name)
                }
            }
        })
        viewModel.longClick.reObserve(viewLifecycleOwner, Observer { it.getContentIfNotHandled()?.let {} })
    }

    override fun onScrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }
}