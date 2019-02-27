package org.michaelbel.moviemade.presentation.features.reviews.fragment

import android.content.IntentFilter
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
import org.michaelbel.moviemade.core.utils.DeviceUtil
import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.core.utils.MOVIE
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.common.network.NetworkChangeReceiver
import org.michaelbel.moviemade.presentation.features.reviews.ReviewsAdapter
import org.michaelbel.moviemade.presentation.features.reviews.ReviewsContract
import org.michaelbel.moviemade.presentation.features.reviews.activity.ReviewsActivity
import javax.inject.Inject

class ReviewsFragment: BaseFragment(), ReviewsContract.View, NetworkChangeReceiver.Listener, ReviewsAdapter.Listener {

    companion object {
        fun newInstance(movie: Movie): ReviewsFragment {
            val args = Bundle()
            args.putSerializable(MOVIE, movie)

            val fragment = ReviewsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var movie: Movie? = null
    private var adapter: ReviewsAdapter? = null

    private var networkChangeReceiver: NetworkChangeReceiver? = null
    private var connectionFailure = false

    @Inject
    lateinit var presenter: ReviewsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkChangeReceiver = NetworkChangeReceiver(this)
        requireContext().registerReceiver(networkChangeReceiver, IntentFilter(NetworkChangeReceiver.INTENT_ACTION))

        App[requireActivity().application].createFragmentComponent().inject(this)
        presenter.attach(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_reviews, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as ReviewsActivity).toolbar.setOnClickListener { recycler_view.smoothScrollToPosition(0) }

        val spanCount = resources.getInteger(R.integer.trailers_span_layout_count)

        adapter = ReviewsAdapter(this)

        recycler_view.adapter = adapter
        recycler_view.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recycler_view.addItemDecoration(GridSpacingItemDecoration(spanCount, DeviceUtil.dp(requireContext(), 5F)))

        empty_view.setOnClickListener {
            empty_view.visibility = GONE
            progress_bar.visibility = VISIBLE
            presenter.getReviews(movie!!.id)
        }

        movie = arguments?.getSerializable(MOVIE) as Movie
        if (movie != null) {
            presenter.getReviews(movie!!.id)
        }
    }

    override fun onReviewClick(review: Review, view: View) {
        (requireActivity() as ReviewsActivity).startReview(review, movie!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        requireContext().unregisterReceiver(networkChangeReceiver)
        presenter.destroy()
    }

    override fun setReviews(reviews: List<Review>) {
        connectionFailure = false
        adapter?.reviews = reviews
        progress_bar.visibility = GONE
    }

    override fun setError(@EmptyViewMode mode: Int) {
        connectionFailure = true
        empty_view.visibility = VISIBLE
        empty_view.setMode(mode)
        progress_bar.visibility = GONE
    }

    override fun onNetworkChanged() {
        if (connectionFailure && adapter?.itemCount == 0) {
            presenter.getReviews(movie!!.id)
        }
    }
}