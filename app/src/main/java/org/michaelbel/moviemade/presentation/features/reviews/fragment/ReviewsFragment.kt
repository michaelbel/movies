package org.michaelbel.moviemade.presentation.features.reviews.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_default.*
import kotlinx.android.synthetic.main.fragment_reviews.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.Review
import org.michaelbel.moviemade.core.DeviceUtil
import org.michaelbel.moviemade.core.local.Intents.EXTRA_MOVIE
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.features.reviews.ReviewsAdapter
import org.michaelbel.moviemade.presentation.features.reviews.ReviewsContract
import org.michaelbel.moviemade.presentation.features.reviews.activity.ReviewsActivity
import javax.inject.Inject

class ReviewsFragment: BaseFragment(), ReviewsContract.View, ReviewsAdapter.Listener {

    companion object {
        fun newInstance(movie: Movie): ReviewsFragment {
            val args = Bundle()
            args.putSerializable(EXTRA_MOVIE, movie)

            val fragment = ReviewsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var movie: Movie
    lateinit var adapter: ReviewsAdapter

    @Inject
    lateinit var presenter: ReviewsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application].createFragmentComponent().inject(this)
        presenter.attach(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_reviews, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as ReviewsActivity).toolbar.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
        }

        val spanCount = resources.getInteger(R.integer.trailers_span_layout_count)

        adapter = ReviewsAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, DeviceUtil.dp(requireContext(), 5F)))

        emptyView.setOnClickListener {
            emptyView.visibility = GONE
            progressBar.visibility = VISIBLE
            presenter.getReviews(movie.id)
        }

        movie = arguments?.getSerializable(EXTRA_MOVIE) as Movie
        presenter.getReviews(movie.id)
    }

    override fun loading(state: Boolean) {
        progressBar.visibility = if (state) VISIBLE else GONE
    }

    override fun content(results: List<Review>) {
        adapter.setReviews(results)
    }

    override fun error(code: Int) {
        emptyView.visibility = VISIBLE
        emptyView.setMode(code)
    }

    override fun onReviewClick(review: Review, view: View) {
        (requireActivity() as ReviewsActivity).startReview(review, movie)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}