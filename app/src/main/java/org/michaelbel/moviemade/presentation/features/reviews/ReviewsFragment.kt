package org.michaelbel.moviemade.presentation.features.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.transaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_parent.*
import kotlinx.android.synthetic.main.fragment_lce.*
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.data.Movie
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.DeviceUtil
import org.michaelbel.moviemade.core.ViewUtil
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.ContainerActivity
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.EXTRA_MOVIE
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.review.ReviewFragment
import javax.inject.Inject

class ReviewsFragment: BaseFragment() {

    companion object {
        private const val ARG_MOVIE = "movie"

        internal fun newInstance(movie: Movie): ReviewsFragment {
            val args = Bundle()
            args.putSerializable(EXTRA_MOVIE, movie)

            val fragment = ReviewsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var movie: Movie
    private lateinit var adapter: ListAdapter
    private lateinit var viewModel: ReviewsModel

    @Inject
    lateinit var factory: ReviewsFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application].createFragmentComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(requireActivity(), factory).get(ReviewsModel::class.java)
        return inflater.inflate(R.layout.fragment_lce, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie = arguments?.getSerializable(ARG_MOVIE) as Movie

        toolbar.title = getString(R.string.reviews)
        toolbar.subtitle = movie.title
        toolbar.navigationIcon = ViewUtil.getIcon(requireContext(), R.drawable.ic_arrow_back, R.color.iconActiveColor)
        toolbar.setOnClickListener { onScrollToTop() }
        toolbar.setNavigationOnClickListener { requireActivity().finish() }

        val spans = resources.getInteger(R.integer.trailers_span_layout_count)

        adapter = ListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spans)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spans, DeviceUtil.dp(requireContext(), 5F)))
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && adapter.itemCount != 0) {
                    viewModel.reviews(movie.id)
                }
            }
        })

        emptyView.setOnClickListener {
            emptyView.visibility = GONE
            viewModel.reviews(movie.id)
        }

        viewModel.reviews(movie.id)
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = if (it) VISIBLE else GONE
        })
        viewModel.content.observe(viewLifecycleOwner, Observer {
            adapter.setItems(it)
        })
        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            error.getContentIfNotHandled()?.let {
                emptyView.visibility = VISIBLE
                emptyView.setMode(it)
            }
        })
        viewModel.click.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                requireFragmentManager().transaction {
                    add((requireActivity() as ContainerActivity).container.id, ReviewFragment.newInstance(it, movie))
                    addToBackStack(tag)
                }
            }
        })
        viewModel.longClick.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                requireFragmentManager().transaction {
                    add((requireActivity() as ContainerActivity).container.id, ReviewFragment.newInstance(it, movie))
                    addToBackStack(tag)
                }
            }
        })
    }

    override fun onScrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }
}