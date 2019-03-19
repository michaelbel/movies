package org.michaelbel.moviemade.presentation.features.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_container.*
import kotlinx.android.synthetic.main.fragment_lce.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.DeviceUtil
import org.michaelbel.moviemade.core.ViewUtil
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.Review
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.ContainerActivity
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.EXTRA_MOVIE
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import javax.inject.Inject

class ReviewsFragment: BaseFragment(), ReviewsContract.View, ReviewsAdapter.Listener {

    companion object {
        private const val FRAGMENT_TAG = "fragment_review"

        internal fun newInstance(movie: Movie): ReviewsFragment {
            val args = Bundle()
            args.putSerializable(EXTRA_MOVIE, movie)

            val fragment = ReviewsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var movie: Movie
    private lateinit var adapter: ReviewsAdapter

    @Inject
    lateinit var presenter: ReviewsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application].createFragmentComponent().inject(this)
        presenter.attach(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lce, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie = arguments?.getSerializable(EXTRA_MOVIE) as Movie

        toolbar.title = getString(R.string.reviews)
        toolbar.subtitle = movie.title
        toolbar.navigationIcon = ViewUtil.getIcon(requireContext(), R.drawable.ic_arrow_back, R.color.iconActiveColor)
        toolbar.setOnClickListener { recyclerView.smoothScrollToPosition(0) }
        toolbar.setNavigationOnClickListener { requireActivity().finish() }

        val spanCount = resources.getInteger(R.integer.trailers_span_layout_count)

        adapter = ReviewsAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, DeviceUtil.dp(requireContext(), 5F)))

        emptyView.setOnClickListener {
            emptyView.visibility = GONE
            progressBar.visibility = VISIBLE
            presenter.reviews(movie.id)
        }

        presenter.reviews(movie.id)
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
        requireFragmentManager()
                .beginTransaction()
                .add((requireActivity() as ContainerActivity).container.id, ReviewFragment.newInstance(review, movie))
                .addToBackStack(FRAGMENT_TAG)
                .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}