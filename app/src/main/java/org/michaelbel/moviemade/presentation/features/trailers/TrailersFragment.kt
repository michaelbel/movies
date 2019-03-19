package org.michaelbel.moviemade.presentation.features.trailers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_lce.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.DeviceUtil
import org.michaelbel.moviemade.core.ViewUtil
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.Video
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.features.trailers.dialog.YoutubePlayerDialogFragment
import javax.inject.Inject

class TrailersFragment: BaseFragment(), TrailersContract.View, TrailersAdapter.Listener {

    companion object {
        private const val ARG_MOVIE = "movie"
        private const val DIALOG_TAG = "dialog_youtube"

        internal fun newInstance(movie: Movie): TrailersFragment {
            val args = Bundle()
            args.putSerializable(ARG_MOVIE, movie)

            val fragment = TrailersFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var movie: Movie
    private lateinit var adapter: TrailersAdapter

    @Inject
    lateinit var presenter: TrailersContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application].createFragmentComponent().inject(this)
        presenter.attach(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_lce, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie = arguments?.getSerializable(ARG_MOVIE) as Movie

        toolbar.title = getString(R.string.trailers)
        toolbar.subtitle = movie.title
        toolbar.setOnClickListener { recyclerView.smoothScrollToPosition(0) }
        toolbar.navigationIcon = ViewUtil.getIcon(requireContext(), R.drawable.ic_arrow_back, R.color.iconActiveColor)
        toolbar.setNavigationOnClickListener { requireActivity().finish() }

        val spanCount = resources.getInteger(R.integer.trailers_span_layout_count)

        adapter = TrailersAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, DeviceUtil.dp(requireContext(), 5F)))

        emptyView.setOnClickListener {
            emptyView.visibility = GONE
            progressBar.visibility = VISIBLE
            presenter.trailers(movie.id)
        }

        presenter.trailers(movie.id)
    }

    override fun onTrailerClick(video: Video) {
        val dialog = YoutubePlayerDialogFragment.newInstance(video.key.toUri().toString())
        dialog.show(requireActivity().supportFragmentManager, DIALOG_TAG)
    }

    override fun onTrailerLongClick(video: Video): Boolean {
        startActivity(Intent(Intent.ACTION_VIEW, ("vnd.youtube:" + video.key).toUri()))
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun loading(state: Boolean) {
        progressBar.visibility = if (state) VISIBLE else GONE
    }

    override fun content(results: List<Video>) {
        adapter.addTrailers(results)
    }

    override fun error(code: Int) {
        emptyView.visibility = VISIBLE
        emptyView.setMode(code)
    }
}